package az.azercellfintech.auth.ms.model.request;

public record OtpVerifyRequest(String number, String otp) {}
