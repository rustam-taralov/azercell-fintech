package az.azercellfintech.otp.ms.util;

import lombok.experimental.UtilityClass;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@UtilityClass
public class GenerateOtpUtil {
    public static String generateOtp(Integer length) {
        return randomNumeric(length);
    }
}
