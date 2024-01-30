package az.azercellfintech.otp.ms.exception;

import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static az.azercellfintech.otp.ms.model.enums.ResponseMessages.ARGUMENT_NOT_VALID;
import static az.azercellfintech.otp.ms.model.enums.ResponseMessages.ERROR;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ErrorHandler {

    @ExceptionHandler(ClientException.class)
    @ResponseStatus(SERVICE_UNAVAILABLE)
    public ErrorResponse handle(ClientException ex) {
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(OtpException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(OtpException ex) {
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(MethodArgumentNotValidException ex) {
        return new ErrorResponse(ARGUMENT_NOT_VALID.name(), ARGUMENT_NOT_VALID.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception ex) {
        return new ErrorResponse(ERROR.name(), ERROR.getMessage());
    }
}
