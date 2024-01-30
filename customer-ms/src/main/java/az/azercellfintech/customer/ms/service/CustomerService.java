package az.azercellfintech.customer.ms.service;

import az.azercellfintech.customer.ms.client.OtpClient;
import az.azercellfintech.customer.ms.configuration.QueueProperties;
import az.azercellfintech.customer.ms.dao.entity.CustomerEntity;
import az.azercellfintech.customer.ms.dao.repository.CustomerRepository;
import az.azercellfintech.customer.ms.dao.repository.service.ServiceRepository;
import az.azercellfintech.customer.ms.exception.BusinessException;
import az.azercellfintech.customer.ms.model.dto.PurchaseTransactionDto;
import az.azercellfintech.customer.ms.model.dto.RefundTransactionDto;
import az.azercellfintech.customer.ms.model.dto.RejectTransactionDto;
import az.azercellfintech.customer.ms.model.dto.TopUpTransactionDto;
import az.azercellfintech.customer.ms.model.request.CheckOtpRequest;
import az.azercellfintech.customer.ms.model.request.CreateCustomerRequest;
import az.azercellfintech.customer.ms.model.request.OtpVerifyRequest;
import az.azercellfintech.customer.ms.model.response.CustomerCredentialsResponse;
import az.azercellfintech.customer.ms.queue.producer.RabbitMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static az.azercellfintech.customer.ms.exception.ExceptionMessages.*;
import static az.azercellfintech.customer.ms.mapper.CustomerMapper.CUSTOMER_MAPPER;
import static az.azercellfintech.customer.ms.mapper.SendOtpDtoBuilder.SEND_OTP_DTO_BUILDER;
import static az.azercellfintech.customer.ms.model.enums.Status.ACTIVE;
import static az.azercellfintech.customer.ms.model.enums.Status.PENDING;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CustomerService {

    CustomerRepository customerRepository;
    ServiceRepository serviceRepository;
    RabbitMessageProducer rabbitMessageProducer;
    QueueProperties queueProperties;
    OtpClient otpClient;

    @Transactional
    public void createCustomer(CreateCustomerRequest request) {
        var customer = customerRepository.findByNumberAndCreatedAtAfter(request.number(), now().minusHours(1));
        if (customer.size() > 3) throw new BusinessException(OTP_LIMIT_EXCEED_ERROR);

        var activeCustomer = customer.stream().filter(c -> c.getStatus() == ACTIVE).findAny();
        if (activeCustomer.isPresent()) throw new BusinessException(USER_ALREADY_EXIST_ERROR);

        var customerEntity = CUSTOMER_MAPPER.toEntity(request);
        customerEntity.setBalance(valueOf(100L));
        customerEntity.setStatus(PENDING);
        customerEntity.setIsDelete(false);

        var otpResponse = otpClient.sendOtp(SEND_OTP_DTO_BUILDER.buildSendOtpDto(customerEntity.getEmail()));
        customerEntity.setOtpIdentifier(otpResponse.identifier());
        customerRepository.save(customerEntity);
    }

    @Transactional
    public void verifyCustomer(OtpVerifyRequest otpVerifyRequest) {
        var customer = customerRepository.findFirstByNumberAndCreatedAtAfter(otpVerifyRequest.number(), now().minusHours(1))
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND_ERROR));
        otpClient.checkOtp(new CheckOtpRequest(customer.getOtpIdentifier(), otpVerifyRequest.otp()));
        customer.setStatus(ACTIVE);
        customerRepository.save(customer);
    }

    public CustomerCredentialsResponse getCustomerCredentialsResponse(String number) {
        return customerRepository.findByNumberAndStatusAndIsDeleteFalse(number, ACTIVE)
                .map(CUSTOMER_MAPPER::toResponse)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND_ERROR));
    }

    @Transactional
    public void topUpTransaction(TopUpTransactionDto topUpTransactionDto) {
        var customer = customerRepository.findByNumberAndStatusAndIsDeleteFalse(topUpTransactionDto.number(), ACTIVE);
        try {
            if (customer.isEmpty()) {
                sendMessage(
                    topUpTransactionDto.transactionId(),
                    USER_NOT_FOUND_ERROR.getMessage(),
                    queueProperties.getTopUpRejection()
                );
                return;
            }
            customerRepository.save(increaseBalance(customer.get(), topUpTransactionDto.amount()));
            sendMessage(
                topUpTransactionDto.transactionId(),
                null,
                queueProperties.getTopUpSuccess()
            );
        } catch (Exception ex) {
            sendMessage(
                topUpTransactionDto.transactionId(),
                ex.getMessage(),
                queueProperties.getTopUpRejection()
            );
        }
    }

    @Transactional
    public void refundTransaction(RefundTransactionDto refundTransactionDto) {
        var customer = customerRepository.findByNumberAndStatusAndIsDeleteFalse(refundTransactionDto.number(), ACTIVE);
        try {
            if (customer.isEmpty()) {
              sendMessage(
                  refundTransactionDto.transactionId(),
                  USER_NOT_FOUND_ERROR.getMessage(),
                  queueProperties.getRefundRejection()
              );
              return;
            }
            customerRepository.save(subtractBalance(customer.get(), refundTransactionDto.amount()));
            sendMessage(
                refundTransactionDto.transactionId(),
                null,
                queueProperties.getRefundSuccess()
            );
        } catch (Exception ex) {
            sendMessage(
                refundTransactionDto.transactionId(),
                ex.getMessage(),
                queueProperties.getRefundRejection()
            );
        }
    }

    @Transactional
    public void purchaseTransaction(PurchaseTransactionDto purchaseTransactionDto) {
        var customer = customerRepository.findByNumberAndStatusAndIsDeleteFalse(purchaseTransactionDto.number(), ACTIVE);
        var service = serviceRepository.findByServiceIdAndIsDeleteFalse(purchaseTransactionDto.serviceId());
        try {
            if (customer.isEmpty()) {
              sendMessage(
                  purchaseTransactionDto.transactionId(),
                  USER_NOT_FOUND_ERROR.getMessage(),
                  queueProperties.getPurchaseRejection()
              );
              return;
            }

            if (service.isEmpty()) {
              sendMessage(
                  purchaseTransactionDto.transactionId(),
                  SERVICE_FOUND_ERROR.getMessage(),
                  queueProperties.getPurchaseRejection()
              );
              return;
            }

            checkBalance(customer.get().getBalance(), service.get().getPrice());
            customerRepository.save(subtractBalance(customer.get(), service.get().getPrice()));
          sendMessage(
              purchaseTransactionDto.transactionId(),
              null,
              queueProperties.getPurchaseSuccess()
          );
        } catch (Exception ex) {
            sendMessage(
                purchaseTransactionDto.transactionId(),
                ex.getMessage(),
                queueProperties.getPurchaseRejection()
            );
        }
    }

    private void sendMessage(String transactionId, String message, String queueName) {
        rabbitMessageProducer.sendMessage(
                new RejectTransactionDto(transactionId, message),
                queueName
        );
    }


    private CustomerEntity subtractBalance(CustomerEntity customer, BigDecimal amount) {
        customer.setBalance(customer.getBalance().subtract(amount));
        return customer;
    }

    private CustomerEntity increaseBalance(CustomerEntity customer, BigDecimal amount) {
        customer.setBalance(customer.getBalance().add(amount));
        return customer;
    }

    private void checkBalance(BigDecimal currentBalance, BigDecimal amount) {
        if (currentBalance.doubleValue() < amount.doubleValue())
            throw new BusinessException(INSUFFICIENT_BALANCE);
    }
}
