package az.azercellfintech.payment.model.request;

import java.math.BigDecimal;

public record RefundRequest(String transactionId, BigDecimal amount) {}
