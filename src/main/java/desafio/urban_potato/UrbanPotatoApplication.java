package desafio.urban_potato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
public class UrbanPotatoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrbanPotatoApplication.class, args);
	}

}
