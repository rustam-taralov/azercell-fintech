package az.azercellfintech.auth.ms.config;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import static lombok.AccessLevel.PRIVATE;

@ConstructorBinding
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@ConfigurationProperties(prefix = "rabbit.consumer")
public class JwtProperties {

    String secret;
}
