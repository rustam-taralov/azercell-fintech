package az.azercellfintech.auth.ms.service;

import az.azercellfintech.auth.ms.client.CustomerClient;
import az.azercellfintech.auth.ms.exception.BusinessException;
import az.azercellfintech.auth.ms.exception.ExceptionMessages;
import az.azercellfintech.auth.ms.model.request.CreateCustomerRequest;
import az.azercellfintech.auth.ms.model.request.LoginRequest;
import az.azercellfintech.auth.ms.model.response.LoginResponse;
import az.azercellfintech.auth.ms.model.response.RefreshCustomerTokenResponse;
import az.azercellfintech.auth.ms.model.response.VerifyCustomerTokenResponse;
import az.azercellfintech.auth.ms.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static az.azercellfintech.auth.ms.exception.ExceptionMessages.TOKEN_WAS_NOT_VERIFIED_ERROR;
import static az.azercellfintech.auth.ms.factory.PasswordEncoderFactory.B_CRYPT_PASSWORD_ENCODER;
import static az.azercellfintech.auth.ms.security.JwtTokenUtil.getNumber;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AuthService {

    CustomerClient customerClient;
    JwtTokenUtil jwtTokenUtil;

    public void createCustomer(CreateCustomerRequest createCustomerRequest) {
        createCustomerRequest.setPassword(B_CRYPT_PASSWORD_ENCODER.getPasswordEncoder().encode(createCustomerRequest.getPassword()));
        customerClient.createCustomer(createCustomerRequest);
    }

    public LoginResponse loginCustomer(LoginRequest loginRequest) {
        var customer = customerClient.getCustomerCredentials(loginRequest.number());
        var isPasswordSame = B_CRYPT_PASSWORD_ENCODER.getPasswordEncoder().matches(loginRequest.password(), customer.password());
        if (!isPasswordSame) throw new BusinessException(ExceptionMessages.USER_NOT_FOUND_ERROR);
        return new LoginResponse(
                jwtTokenUtil.generateAccessToken(loginRequest.number()),
                jwtTokenUtil.generateRefreshToken(loginRequest.number())
        );
    }

    public VerifyCustomerTokenResponse verifyCustomerToken(String token) {
        if (!jwtTokenUtil.validate(token)) throw new BusinessException(TOKEN_WAS_NOT_VERIFIED_ERROR);
        return new VerifyCustomerTokenResponse(getNumber(token));
    }

    public RefreshCustomerTokenResponse refreshCustomerToken(String refreshToken) {
      if (!jwtTokenUtil.validate(refreshToken)) throw new BusinessException(TOKEN_WAS_NOT_VERIFIED_ERROR);
      return new RefreshCustomerTokenResponse(jwtTokenUtil.generateAccessToken(getNumber(refreshToken)));
    }

}
