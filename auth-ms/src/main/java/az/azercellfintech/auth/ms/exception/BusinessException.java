package az.azercellfintech.auth.ms.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    String code;
    public BusinessException(ExceptionMessages exceptionMessages) {
        super(exceptionMessages.getMessage());
        this.code = exceptionMessages.name();
    }
}
