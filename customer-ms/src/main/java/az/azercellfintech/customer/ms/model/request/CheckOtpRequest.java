package az.azercellfintech.customer.ms.model.request;

public record CheckOtpRequest(
        String identifier,
        String otp
) {}
