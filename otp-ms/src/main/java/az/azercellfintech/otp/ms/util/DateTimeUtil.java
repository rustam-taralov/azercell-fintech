package az.azercellfintech.otp.ms.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.SECONDS;

@UtilityClass
public class DateTimeUtil {

    public static LocalDateTime plusSecondsCurrentTime(Integer seconds) {
        return now().plus(seconds, SECONDS);
    }
}
