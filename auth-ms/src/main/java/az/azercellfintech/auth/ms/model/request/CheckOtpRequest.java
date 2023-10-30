package az.azercellfintech.auth.ms.model.request;

public record CheckOtpRequest(
        String identifier,
        String otp
) {}
