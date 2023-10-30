package az.azercellfintech.payment.mapper;

import az.azercellfintech.payment.dao.entity.TransactionsEntity;
import az.azercellfintech.payment.model.request.RefundRequest;
import az.azercellfintech.payment.model.request.TopUpRequest;

import java.util.UUID;

import static az.azercellfintech.payment.model.enums.Status.PENDING;
import static az.azercellfintech.payment.model.enums.TransactionType.REFUND;
import static az.azercellfintech.payment.model.enums.TransactionType.TOP_UP;

public enum TransactionEntityBuilder {

    TOP_UP_REQUEST_BUILDER;

    public TransactionsEntity buildTopUpTransactionEntity(TopUpRequest topUpRequest, String number) {
        return new TransactionsEntity(
                number,
                topUpRequest.amount(),
                UUID.randomUUID().toString(),
                PENDING,
                TOP_UP,
                null
        );
    }
    public TransactionsEntity buildRefundTransactionEntity(RefundRequest topUpRequest, String number) {
        return new TransactionsEntity(
                number,
                topUpRequest.amount(),
                UUID.randomUUID().toString(),
                PENDING,
                REFUND,
                null
        );
    }
}
