package az.azercellfintech.customer.ms.exception;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static az.azercellfintech.customer.ms.exception.ExceptionMessages.UNEXPECTED_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Log
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception ex) {
        log.throwing("Exception","Exception ", ex);
        return new ErrorResponse(UNEXPECTED_ERROR.name(),UNEXPECTED_ERROR.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(BusinessException ex) {
        log.throwing("Exception","Exception ", ex);
        return new ErrorResponse(ex.code, ex.getMessage());
    }

    @ExceptionHandler(ClientException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(ClientException ex) {
        log.throwing("Exception","Exception ", ex);
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }
}
