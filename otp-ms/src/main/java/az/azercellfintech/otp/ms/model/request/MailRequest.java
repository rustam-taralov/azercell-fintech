package az.azercellfintech.otp.ms.model.request;

public record MailRequest(String toEmail, String subject, String message){}