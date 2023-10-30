//package az.azercellfintech.auth.ms.security;
//
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import static lombok.AccessLevel.PRIVATE;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//@FieldDefaults(level = PRIVATE, makeFinal = true)
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    JwtAuthenticationFilter jwtAuthenticationFilter;
//    JwtAuthenticationEntryPoint authenticationEntryPoint;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().and().cors().disable();
//
//        http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and();
//
//        http
//                .exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint)
//                .and();
//
//        http.authorizeRequests()
//                .antMatchers("/auth-ms/v1/auth/**").permitAll()
//                .anyRequest().authenticated();
//
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder(10);
//    }
//}
