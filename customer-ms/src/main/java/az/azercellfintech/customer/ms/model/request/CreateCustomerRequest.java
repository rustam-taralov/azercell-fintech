package az.azercellfintech.customer.ms.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import javax.validation.constraints.Pattern;

public record CreateCustomerRequest(
        String name,
        String surname,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate birthDate,
        @Pattern(regexp="^[0-9]{3}-[0-9]{3}-[0-9]{2}-[0-9]{2}$")
        String number,
        String password,
        String email
) {}
