package az.azercellfintech.auth.ms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum ExceptionMessages {

    UNEXPECTED_ERROR("Unexpected error occurred"),
    UN_AUTHORIZED_ERROR("User couldn't authorized"),
    TOKEN_EXPIRED_ERROR("Token expired"),
    TOKEN_WAS_NOT_VERIFIED_ERROR("Token was not verified"),
    USER_NOT_FOUND_ERROR("User couldn't found");

    String message;
}
