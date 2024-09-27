package desafio.urban_potato.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import desafio.urban_potato.controller.req.ReqAberturaSessaoVO;
import desafio.urban_potato.domain.pauta.Pauta;
import desafio.urban_potato.domain.pauta.PautaService;
import desafio.urban_potato.domain.sessao.Sessao;
import desafio.urban_potato.domain.sessao.SessaoService;
import desafio.urban_potato.exceptions.PautaNaoExisteException;

@SpringBootTest
@ActiveProfiles("test")
class CreateSessaoUCTests {
	
	@Autowired CreateSessaoUC uc;
	@MockBean SessaoService sessaoService;
	@MockBean PautaService pautaService;
	
	@Test void createPautaNaoExiste() {
		when(pautaService.get(any()))
			.thenReturn(Optional.empty());
		
		assertThrows(PautaNaoExisteException.class, 
				() -> uc.create("pauta", null));
	}

	@Test void create() {
		when(pautaService.get(any()))
			.thenReturn(Optional.of(new Pauta()));
		
		ReqAberturaSessaoVO request = new ReqAberturaSessaoVO(5);
		
		when(sessaoService.create(any()))
		.thenAnswer(new Answer<Sessao>() {

			@Override
			public Sessao answer(InvocationOnMock invocation) throws Throwable {
				Sessao s = invocation.getArgument(0);
				assertNotNull(s);
				assertNotNull(s.getTsFim());
				assertNotNull(s.getPauta());
				assertEquals(s.getTsFim().getMinute(), 
						LocalDateTime.now().plusMinutes(5).getMinute());
				return s;
			}
		});
		
		uc.create("pauta", request );
	}

}
