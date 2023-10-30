package az.azercellfintech.payment.mapper;

import az.azercellfintech.payment.dao.entity.PurchaseTransactionsEntity;
import az.azercellfintech.payment.model.request.PurchaseRequest;

import java.util.UUID;

import static az.azercellfintech.payment.model.enums.Status.PENDING;

public enum PurchaseEntityBuilder {

    TOP_UP_REQUEST_BUILDER;

    public PurchaseTransactionsEntity buildPurchaseTransactionEntity(PurchaseRequest topUpRequest, String number) {
        return new PurchaseTransactionsEntity(
                number,
                UUID.randomUUID().toString(),
                topUpRequest.serviceId(),
                PENDING,
                null
        );
    }
}
