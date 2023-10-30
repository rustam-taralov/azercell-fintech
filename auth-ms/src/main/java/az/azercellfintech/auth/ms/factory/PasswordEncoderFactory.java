package az.azercellfintech.auth.ms.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
public enum PasswordEncoderFactory {
    B_CRYPT_PASSWORD_ENCODER(new BCryptPasswordEncoder(10));

    final PasswordEncoder passwordEncoder;
}