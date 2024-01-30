package az.azercellfintech.otp.ms.exception;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OtpException extends RuntimeException {
    String code;

    public OtpException(String message, String code) {
        super(message);
        this.code = code;
    }
}
