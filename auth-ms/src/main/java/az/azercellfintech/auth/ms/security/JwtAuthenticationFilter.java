package az.azercellfintech.auth.ms.security;

import az.azercellfintech.auth.ms.client.CustomerClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static az.azercellfintech.auth.ms.security.JwtTokenUtil.getNumber;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.StringUtils.hasLength;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    JwtTokenUtil jwtTokenUtil;
    CustomerClient customerClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final var header = request.getHeader(AUTHORIZATION);
        if (!hasLength(header)) {
            filterChain.doFilter(request, response);
            return;
        }

        final var token = header.split(" ")[0].trim();
        if (!jwtTokenUtil.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        request.setAttribute("number", getNumber(token));

        filterChain.doFilter(request, response);
    }
}