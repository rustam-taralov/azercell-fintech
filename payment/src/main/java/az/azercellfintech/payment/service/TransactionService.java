package az.azercellfintech.payment.service;

import az.azercellfintech.payment.configuration.QueueProperties;
import az.azercellfintech.payment.dao.repository.TopUpTransactionsRepository;
import az.azercellfintech.payment.model.dto.RejectTransactionDto;
import az.azercellfintech.payment.model.request.RefundRequest;
import az.azercellfintech.payment.model.request.TopUpRequest;
import az.azercellfintech.payment.queue.producer.RabbitMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static az.azercellfintech.payment.mapper.TransactionEntityBuilder.TOP_UP_REQUEST_BUILDER;
import static az.azercellfintech.payment.mapper.TransactionMapper.TOP_UP_TRANSACTION_MAPPER;
import static az.azercellfintech.payment.model.enums.Status.REJECT;
import static az.azercellfintech.payment.model.enums.Status.SUCCESS;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TransactionService {

    QueueProperties queueProperties;
    RabbitMessageProducer rabbitMessageProducer;
    TopUpTransactionsRepository topUpTransactionsRepository;

    @Transactional
    public void topUpTransaction(String number, TopUpRequest topUpRequest) {
        var topUpTransaction = TOP_UP_REQUEST_BUILDER.buildTopUpTransactionEntity(topUpRequest, number);
        topUpTransaction.setIsDelete(false);
        topUpTransactionsRepository.save(topUpTransaction);
        rabbitMessageProducer.sendMessage(TOP_UP_TRANSACTION_MAPPER.toTopUpDto(topUpTransaction), queueProperties.getTopUp());
    }

    @Transactional
    public void refundTransaction(String number, RefundRequest topUpRequest) {
        var refundTransaction = TOP_UP_REQUEST_BUILDER.buildRefundTransactionEntity(topUpRequest, number);
        refundTransaction.setIsDelete(false);
        topUpTransactionsRepository.save(refundTransaction);
        rabbitMessageProducer.sendMessage(TOP_UP_TRANSACTION_MAPPER.toRefundDto(refundTransaction), queueProperties.getRefund());
    }

    public void rejectionUpdate(RejectTransactionDto rejectTransactionDto) {
        topUpTransactionsRepository.findByTransactionId(rejectTransactionDto.transactionId())
                .map(transactionsEntity -> {
                    transactionsEntity.setStatus(REJECT);
                    transactionsEntity.setRejectReason(rejectTransactionDto.reason());
                    return transactionsEntity;
                })
                .ifPresent(topUpTransactionsRepository::save);
    }
    public void successUpdate(RejectTransactionDto rejectTransactionDto) {
        topUpTransactionsRepository.findByTransactionId(rejectTransactionDto.transactionId())
                .map(transactionsEntity -> {
                    transactionsEntity.setStatus(SUCCESS);
                    transactionsEntity.setRejectReason(rejectTransactionDto.reason());
                    return transactionsEntity;
                })
                .ifPresent(topUpTransactionsRepository::save);
    }
}
