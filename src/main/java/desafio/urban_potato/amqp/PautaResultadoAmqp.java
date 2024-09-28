package desafio.urban_potato.amqp;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class PautaResultadoAmqp {
	
	private final RabbitTemplate rabbitTemplate;
	private final Queue queue;
	private final AmqpAdmin amqpAdmin;
	
	public PautaResultadoAmqp(
			AmqpAdmin amqpAdmin,
			@Value("${queue.pauta.resultado}") String name,
			RabbitTemplate rabbitTemplate) {
		this.amqpAdmin = amqpAdmin;
		this.queue = new Queue(name, true);
		this.rabbitTemplate = rabbitTemplate;
	}
	
	@PostConstruct
	public void createQueue() {
        amqpAdmin.declareQueue(queue);
    }
	
	public void send(String order) {
        rabbitTemplate.convertAndSend(
        		this.queue.getName(), order);
    }
	
}
