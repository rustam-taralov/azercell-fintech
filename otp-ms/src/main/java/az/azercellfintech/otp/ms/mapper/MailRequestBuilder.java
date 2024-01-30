package az.azercellfintech.otp.ms.mapper;

import az.azercellfintech.otp.ms.model.request.MailRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MailRequestBuilder {
    public static MailRequest buildEmailRequest(String toEmail, String subject, String message) {
        return new MailRequest(toEmail, subject, message);
    }
}
