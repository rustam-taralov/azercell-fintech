package az.azercellfintech.auth.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@ConfigurationPropertiesScan
public class AuthMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthMsApplication.class, args);
	}

}
