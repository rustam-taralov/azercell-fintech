package az.azercellfintech.otp.ms.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum ResponseMessages {

    ERROR("Unexpected error occurred"),
    CLIENT_ERROR("Error happen in client"),
    OTP_NOT_EQUAL("Error otp not equal"),
    OTP_EXPIRED("Error otp expired"),
    ARGUMENT_NOT_VALID("Error argument not valid"),
    OTP_LIMIT_EXCESS("Error otp limit excess");

    String message;
}
