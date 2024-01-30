package az.azercellfintech.payment.service;

import az.azercellfintech.payment.configuration.QueueProperties;
import az.azercellfintech.payment.dao.repository.PurchaseTransactionRepository;
import az.azercellfintech.payment.model.dto.RejectTransactionDto;
import az.azercellfintech.payment.model.request.PurchaseRequest;
import az.azercellfintech.payment.queue.producer.RabbitMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static az.azercellfintech.payment.mapper.PurchaseEntityBuilder.TOP_UP_REQUEST_BUILDER;
import static az.azercellfintech.payment.mapper.PurchaseTransactionMapper.TOP_UP_TRANSACTION_MAPPER;
import static az.azercellfintech.payment.model.enums.Status.REJECT;
import static az.azercellfintech.payment.model.enums.Status.SUCCESS;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PurchaseService {

    PurchaseTransactionRepository purchaseTransactionRepository;
    QueueProperties queueProperties;
    RabbitMessageProducer rabbitMessageProducer;

    public void purchaseTransaction(PurchaseRequest purchaseRequest, String number) {
        var purchaseEntity = TOP_UP_REQUEST_BUILDER.buildPurchaseTransactionEntity(purchaseRequest, number);
        purchaseEntity.setIsDelete(false);
        purchaseTransactionRepository.save(purchaseEntity);
        rabbitMessageProducer.sendMessage(TOP_UP_TRANSACTION_MAPPER.toPurchaseDto(purchaseEntity), queueProperties.getPurchase());
    }

    public void rejectionUpdate(RejectTransactionDto rejectTransactionDto) {
        purchaseTransactionRepository.findByTransactionId(rejectTransactionDto.transactionId())
                .map(purchaseTransactionsEntity -> {
                    purchaseTransactionsEntity.setStatus(REJECT);
                    purchaseTransactionsEntity.setRejectReason(rejectTransactionDto.reason());
                    return purchaseTransactionsEntity;
                })
                .ifPresent(purchaseTransactionRepository::save);
    }

    public void successUpdate(RejectTransactionDto rejectTransactionDto) {
        purchaseTransactionRepository.findByTransactionId(rejectTransactionDto.transactionId())
                .map(purchaseTransactionsEntity -> {
                    purchaseTransactionsEntity.setStatus(SUCCESS);
                    purchaseTransactionsEntity.setRejectReason(rejectTransactionDto.reason());
                    return purchaseTransactionsEntity;
                })
                .ifPresent(purchaseTransactionRepository::save);
    }
}
