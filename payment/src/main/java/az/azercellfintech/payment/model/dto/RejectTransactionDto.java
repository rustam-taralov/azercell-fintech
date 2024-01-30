package az.azercellfintech.payment.model.dto;

public record RejectTransactionDto(String transactionId, String reason) {}
