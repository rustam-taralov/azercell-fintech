package az.azercellfintech.otp.ms.model.request;

import az.azercellfintech.otp.ms.model.enums.MessageTemplate;
import az.azercellfintech.otp.ms.model.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.util.List;

import static az.azercellfintech.otp.ms.model.enums.MessageTemplate.DEFAULT_MAIL_TEMPLATE;
import static az.azercellfintech.otp.ms.model.enums.NotificationType.MAIL;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.USE_DEFAULTS;
import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@JsonInclude(NON_NULL)
public class SendOtpRequest {
    @NotBlank
    String serviceName;
    @NotBlank
    String destination;
    @JsonInclude(USE_DEFAULTS)
    Integer attempt = 3;
    @JsonInclude(USE_DEFAULTS)
    Integer expirationSecond = 180;
    @JsonInclude(USE_DEFAULTS)
    List<NotificationType> notificationType = List.of(MAIL);
    @JsonInclude(USE_DEFAULTS)
    Integer otpLength = 6;
    @JsonInclude(USE_DEFAULTS)
    MessageTemplate messageTemplate = DEFAULT_MAIL_TEMPLATE;
}
