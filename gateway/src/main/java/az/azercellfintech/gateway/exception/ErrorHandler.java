package az.azercellfintech.gateway.exception;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static az.azercellfintech.gateway.exception.ExceptionMessages.UNEXPECTED_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Log
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception ex) {
        return new ErrorResponse(UNEXPECTED_ERROR.name(),UNEXPECTED_ERROR.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(AuthenticationException ex) {
        return new ErrorResponse(ex.code, ex.getMessage());
    }
}
