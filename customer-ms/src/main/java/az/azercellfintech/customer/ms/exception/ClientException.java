package az.azercellfintech.customer.ms.exception;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@FieldDefaults(level = PRIVATE)
public class ClientException extends RuntimeException{

    String code;

    public ClientException(String code, String message) {
        super(message);
        this.code = code;
    }
}
