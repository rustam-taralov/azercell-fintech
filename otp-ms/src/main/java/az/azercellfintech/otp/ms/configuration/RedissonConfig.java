package az.azercellfintech.otp.ms.configuration;

import lombok.experimental.FieldDefaults;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static lombok.AccessLevel.PRIVATE;

@Configuration
@FieldDefaults(level = PRIVATE)
public class RedissonConfig {

    @Value("${redis.url}")
    String redisUrl;

    @Bean
    public RedissonClient redissonClient() {
        var config = new Config();
        config.setCodec(new SerializationCodec())
                .useSingleServer()
                .setAddress(redisUrl);
        return Redisson.create(config);
    }

}
