package desafio.urban_potato.amqp;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import desafio.urban_potato.controller.res.ResSessaoVO;
import desafio.urban_potato.controller.res.ResVotoVO;

@SpringBootTest
@ActiveProfiles("integration")
class PautaResultadoAmqpIT {
	
	@Autowired PautaResultadoAmqp queue;
	
	@Test void send() {
		queue.send(new ResSessaoVO(
				"id", 
				"pauta", 
				LocalDateTime.now().minusMinutes(10), 
				LocalDateTime.now().plusMinutes(10), 
				Arrays.asList(
						new ResVotoVO("Sim", 20),
						new ResVotoVO("Não", 22))));
	}

}
