package az.azercellfintech.gateway.client;

import az.azercellfintech.gateway.exception.ClientException;
import az.azercellfintech.gateway.model.VerifyCustomerTokenResponse;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static az.azercellfintech.gateway.exception.ExceptionMessages.AUTHENTICATION_ERROR;

@Service
public class AuthClientService {

    private final WebClient webClient;

    public AuthClientService() {
        this.webClient = WebClient.builder()
                .build();
    }

    public Mono<VerifyCustomerTokenResponse> verifyCustomerToken(String accessToken) {
        return webClient.post()
                .uri("localhost:8084/auth-ms/v1/auth/verify")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new ClientException(AUTHENTICATION_ERROR.name(), AUTHENTICATION_ERROR.getMessage())))
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new ClientException(String.valueOf(clientResponse.statusCode().value()), errorBody)))
                )
                .bodyToMono(VerifyCustomerTokenResponse.class);
    }
}