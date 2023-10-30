package az.azercellfintech.otp.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OtpMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtpMsApplication.class, args);
	}

}
