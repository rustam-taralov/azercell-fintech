package az.azercellfintech.gateway.filter;

import az.azercellfintech.gateway.client.AuthClientService;
import az.azercellfintech.gateway.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter {

    AuthClientService authClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();
        var headers = request.getHeaders().get(HttpHeaders.AUTHORIZATION);

        if (headers != null) {
            String accessToken = headers.stream().findFirst().orElseThrow(AuthenticationException::new);
            return authClient.verifyCustomerToken(accessToken)
                    .flatMap(authResponse -> {
                        String number = authResponse.number();
                        exchange.getRequest().mutate().headers(httpHeaders -> httpHeaders.add("number", number));
                        return chain.filter(exchange);
                    })
                    .switchIfEmpty(Mono.error(new AuthenticationException()));
        } else {
            return chain.filter(exchange);
        }
    }
}
