package desafio.urban_potato;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRabbit
@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
public class UrbanPotatoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrbanPotatoApplication.class, args);
	}

}
