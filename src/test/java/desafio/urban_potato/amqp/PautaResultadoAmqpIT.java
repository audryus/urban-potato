package desafio.urban_potato.amqp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration")
class PautaResultadoAmqpIT {
	
	@Autowired PautaResultadoAmqp queue;
	
	@Test void send() {
		queue.send("teste");
	}

}
