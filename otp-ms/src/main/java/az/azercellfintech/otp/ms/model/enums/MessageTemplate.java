package az.azercellfintech.otp.ms.model.enums;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.i18n.LocaleContextHolder;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum MessageTemplate {

    DEFAULT_MAIL_TEMPLATE("OTP code is: %s. Do not share it with anyone.", "OTP kod: %s. Bu kodu digər şəxslərlə paylaşmayın.", "Код OTP: %s. Не делитесь им ни с кем.");
    String messageEn;
    String messageAz;
    String messageRu;

    public String getMessage(String otp) {
        var language = LocaleContextHolder.getLocale().getLanguage();
        return switch (language) {
            case "en" -> format(messageEn, otp);
            case "ru" -> format(messageRu, otp);
            default -> format(messageAz, otp);
        };
    }
    public String getMessage() {
        var language = LocaleContextHolder.getLocale().getLanguage();
        return switch (language) {
            case "en" -> messageEn;
            case "ru" -> messageRu;
            default ->messageAz;
        };
    }
}
