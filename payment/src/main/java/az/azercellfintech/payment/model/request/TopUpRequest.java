package az.azercellfintech.payment.model.request;

import java.math.BigDecimal;

public record TopUpRequest(BigDecimal amount) {}
