package az.azercellfintech.auth.ms.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CreateCustomerRequest {
    String name;
    String surname;
    String password;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;
    String number;
    String email;
}
