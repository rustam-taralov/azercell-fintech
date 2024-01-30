package az.azercellfintech.payment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum ExceptionMessages {

    UNEXPECTED_ERROR("Unexpected error occurred"),
    USER_ALREADY_EXIST_ERROR("User already exist"),
    OTP_LIMIT_EXCEED_ERROR("Otp limit exceed"),
    INSUFFICIENT_BALANCE("Insufficient Balance"),
    SERVICE_FOUND_ERROR("Service couldn't found"),
    USER_NOT_FOUND_ERROR("User couldn't found");

    String message;
}
