package az.azercellfintech.customer.ms.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record CreateCustomerRequest(
        String name,
        String surname,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate birthDate,
        String number,
        String password,
        String email
) {}
