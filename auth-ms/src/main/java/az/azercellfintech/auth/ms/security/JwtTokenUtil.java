package az.azercellfintech.auth.ms.security;

import az.azercellfintech.auth.ms.exception.BusinessException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Instant;

import static az.azercellfintech.auth.ms.exception.ExceptionMessages.TOKEN_EXPIRED_ERROR;
import static az.azercellfintech.auth.ms.exception.ExceptionMessages.TOKEN_WAS_NOT_VERIFIED_ERROR;
import static com.auth0.jwt.JWT.require;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.lang.String.format;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static lombok.AccessLevel.PRIVATE;

@ConstructorBinding
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenUtil {

    String secret;
    Integer accessTokenTime;
    Integer refreshTokenTime;

    public String generateAccessToken(String number) {
        return JWT.create()
                .withSubject(format(number))
                .withExpiresAt(now().plus(accessTokenTime, MINUTES))
                .sign(HMAC512(secret));
    }

    public String generateRefreshToken(String number) {
        return JWT.create()
                .withSubject(format(number))
                .withExpiresAt(now().plus(refreshTokenTime, DAYS))
                .sign(HMAC512(secret));
    }

    public boolean validate(String token) {
        try {
            JWTVerifier jwtVerifier = require(HMAC512(secret)).build();
            Instant expires = jwtVerifier.verify(token).getExpiresAtAsInstant();
            if (now().isBefore(expires)) {
                return true;
            }
        } catch (TokenExpiredException e) {
            throw new BusinessException(TOKEN_EXPIRED_ERROR);
        }catch (JWTVerificationException ex) {
            throw new BusinessException(TOKEN_WAS_NOT_VERIFIED_ERROR);
        }
        return false;
    }
    public static String getNumber(String token) {
        return JWT.decode(token).getSubject();
    }

}
