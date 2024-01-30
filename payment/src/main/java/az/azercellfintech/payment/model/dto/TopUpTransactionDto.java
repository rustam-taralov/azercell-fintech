package az.azercellfintech.payment.model.dto;

import java.math.BigDecimal;

public record TopUpTransactionDto(String number, String transactionId, BigDecimal amount) {}

