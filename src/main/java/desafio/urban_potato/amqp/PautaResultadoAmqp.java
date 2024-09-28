package desafio.urban_potato.amqp;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import desafio.urban_potato.controller.res.ResSessaoVO;
import desafio.urban_potato.utils.JsonUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PautaResultadoAmqp {
	
	@Value("${queue.pauta.resultado}") 
	private String name;
	
	private final RabbitTemplate rabbitTemplate;
	private final AmqpAdmin amqpAdmin;
	private final JsonUtils json;
	
	@PostConstruct
	public void createQueue() {
        amqpAdmin.declareQueue(new Queue(name, true));
    }
	
	public void send(ResSessaoVO resSessaoVO) {
        rabbitTemplate.convertAndSend(
        		this.name, json.toJson(resSessaoVO));
    }
	
}
