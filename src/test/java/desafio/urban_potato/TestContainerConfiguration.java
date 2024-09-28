package desafio.urban_potato;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainerConfiguration {
	
	@Bean
    @ServiceConnection
    public RabbitMQContainer lero() {
		return new RabbitMQContainer(DockerImageName.parse("rabbitmq:3-management"));
	}
}
