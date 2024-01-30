package az.azercellfintech.payment.model.dto;

import java.math.BigDecimal;

public record RefundTransactionDto(String number, String transactionId, BigDecimal amount) {}
