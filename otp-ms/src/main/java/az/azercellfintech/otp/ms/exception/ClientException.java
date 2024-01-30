package az.azercellfintech.otp.ms.exception;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ClientException extends RuntimeException {
    String code;

    public ClientException(String message, String code) {
        super(message);
        this.code = code;
    }
}
