package az.azercellfintech.customer.ms.model.dto;

import java.math.BigDecimal;

public record TopUpTransactionDto(String number, String transactionId, BigDecimal amount) {}
