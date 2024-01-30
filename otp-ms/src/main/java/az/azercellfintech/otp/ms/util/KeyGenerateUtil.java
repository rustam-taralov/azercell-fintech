package az.azercellfintech.otp.ms.util;

import lombok.experimental.UtilityClass;

import static java.util.UUID.randomUUID;

@UtilityClass
public class KeyGenerateUtil {

    public static String generateKey(String serviceName) {
        return String.format("%s-%s", serviceName, randomUUID());
    }

}
