package desafio.urban_potato.domain.sessao;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration")
class SessaoRepoIT {
	
	@Autowired SessaoRepo r;
	
	@Test void findAllSemNotificacao() {
		Pageable pageable = PageRequest.of(0, 1);
		Slice<Sessao> allSemNotificacao = r.findAllSemNotificacao(
				LocalDateTime.now(), pageable);
		List<Sessao> content = allSemNotificacao.getContent();
		
		content.forEach(System.out::println);
	}
}
