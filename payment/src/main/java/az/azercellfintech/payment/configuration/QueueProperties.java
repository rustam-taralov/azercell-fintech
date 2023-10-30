package az.azercellfintech.payment.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@ConfigurationProperties(prefix = "rabbit.producer")
public class QueueProperties {
    String topUp;
    String purchase;
    String refund;
}
