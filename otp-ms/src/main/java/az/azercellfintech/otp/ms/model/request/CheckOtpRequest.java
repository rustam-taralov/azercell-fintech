package az.azercellfintech.otp.ms.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PRIVATE;


@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CheckOtpRequest {
    @NotBlank
    String identifier;
    @NotBlank
    String otp;
}
