package az.azercellfintech.gateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum ExceptionMessages {

    UNEXPECTED_ERROR("Unexpected error occurred"),
    AUTHENTICATION_ERROR("User can't authenticate");

    String message;
}
