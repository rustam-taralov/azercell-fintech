package az.azercellfintech.auth.ms.controller;

import az.azercellfintech.auth.ms.model.request.CreateCustomerRequest;
import az.azercellfintech.auth.ms.model.request.LoginRequest;
import az.azercellfintech.auth.ms.model.response.LoginResponse;
import az.azercellfintech.auth.ms.model.response.RefreshCustomerTokenResponse;
import az.azercellfintech.auth.ms.model.response.VerifyCustomerTokenResponse;
import az.azercellfintech.auth.ms.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    public void createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        authService.createCustomer(createCustomerRequest);
    }

    @PostMapping("/login")
    public LoginResponse loginAsCustomer(@RequestBody LoginRequest loginRequest) {
        return authService.loginCustomer(loginRequest);
    }

    @PostMapping("/verify")
    public VerifyCustomerTokenResponse verifyCustomerToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return authService.verifyCustomerToken(token);
    }

    @PostMapping("/refresh")
    public RefreshCustomerTokenResponse refreshCustomerToken(@RequestHeader String refreshToken) {
        return authService.refreshCustomerToken(refreshToken);
    }
}
