package desafio.urban_potato.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import desafio.urban_potato.TestContainerConfiguration;
import desafio.urban_potato.controller.req.ReqPautaVO;
import desafio.urban_potato.domain.pauta.Pauta;
import desafio.urban_potato.domain.pauta.PautaService;
import desafio.urban_potato.exceptions.PautaSemNomeException;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestContainerConfiguration.class)
class CreatePautaUCTests {

	@Autowired CreatePautaUC uc;
	@MockBean PautaService pautaService;
	
	@Test
	void create() {
		final ReqPautaVO request1 = new ReqPautaVO(null);
		
		assertThrows(PautaSemNomeException.class, 
				() -> uc.create(request1));
		
		when(pautaService.create(any()))
			.thenReturn(new Pauta());
		
		try {
			ReqPautaVO request2 = new ReqPautaVO("nome");
			assertNotNull(uc.create(request2));
		} catch (Exception e) {
			fail(e);
		}
	}
}
