package az.azercellfintech.otp.ms.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum ObjectMapperFactory {
    MAPPER_FACTORY(new ObjectMapper());

    ObjectMapper objectMapper;
}
