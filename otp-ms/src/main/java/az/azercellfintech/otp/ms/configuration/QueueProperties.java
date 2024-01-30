package az.azercellfintech.otp.ms.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class QueueProperties {

    @Value("${rabbit.producer}")
    String mailPQueue;
}
