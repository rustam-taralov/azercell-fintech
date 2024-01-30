package az.azercellfintech.otp.ms.util;

import az.azercellfintech.otp.ms.exception.OtpException;
import az.azercellfintech.otp.ms.model.redis.RedisOtpDto;
import lombok.experimental.UtilityClass;

import static az.azercellfintech.otp.ms.model.enums.ResponseMessages.*;
import static java.time.LocalDateTime.now;

@UtilityClass
public class OtpValidator {

    public static void validateOtp(String otp, RedisOtpDto redisOtpDto) {
        if (!otp.equals(redisOtpDto.getOtp()))
            throw new OtpException(OTP_NOT_EQUAL.getMessage(), OTP_NOT_EQUAL.name());
        if (redisOtpDto.getExpirationTime().isBefore(now()))
            throw new OtpException(OTP_EXPIRED.getMessage(), OTP_EXPIRED.name());
        if (redisOtpDto.getAttemptCount() > redisOtpDto.getAttemptLimit())
            throw new OtpException(OTP_LIMIT_EXCESS.getMessage(), OTP_LIMIT_EXCESS.name());
    }
}
