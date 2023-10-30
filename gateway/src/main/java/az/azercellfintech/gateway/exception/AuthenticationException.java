package az.azercellfintech.gateway.exception;

import lombok.Getter;

import static az.azercellfintech.gateway.exception.ExceptionMessages.AUTHENTICATION_ERROR;

@Getter
public class AuthenticationException extends RuntimeException {
    String code;
    public AuthenticationException() {
        super(AUTHENTICATION_ERROR.getMessage());
        this.code = AUTHENTICATION_ERROR.name();
    }
}
