package az.azercellfintech.otp.ms.mapper;

import az.azercellfintech.otp.ms.model.redis.RedisOtpDto;
import az.azercellfintech.otp.ms.model.request.SendOtpRequest;
import lombok.experimental.UtilityClass;

import static az.azercellfintech.otp.ms.util.DateTimeUtil.plusSecondsCurrentTime;


@UtilityClass
public class RedisOtpObjectBuilder {

    public static RedisOtpDto mapToRedisObject(SendOtpRequest sendOtpRequest, String otp) {
        return new RedisOtpDto(
                otp,
                0,
                sendOtpRequest.getAttempt(),
                plusSecondsCurrentTime(sendOtpRequest.getExpirationSecond())
        );
    }

}
