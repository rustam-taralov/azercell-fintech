package az.azercellfintech.otp.ms.model.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class RedisOtpDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;

    String otp;
    Integer attemptCount;
    Integer attemptLimit;
    LocalDateTime expirationTime;

}
