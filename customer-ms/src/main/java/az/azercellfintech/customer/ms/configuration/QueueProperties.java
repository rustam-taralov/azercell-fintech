package az.azercellfintech.customer.ms.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@ConfigurationProperties(prefix = "rabbit.producer")
public class QueueProperties {
    String topUpRejection;
    String topUpSuccess;
    String purchaseRejection;
    String purchaseSuccess;
    String refundRejection;
    String refundSuccess;
}
