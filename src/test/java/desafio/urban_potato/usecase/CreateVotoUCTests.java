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

import desafio.urban_potato.domain.associado.Associado;
import desafio.urban_potato.domain.associado.AssociadoService;
import desafio.urban_potato.domain.sessao.Sessao;
import desafio.urban_potato.domain.sessao.SessaoService;
import desafio.urban_potato.domain.voto.Voto;
import desafio.urban_potato.domain.voto.VotoService;
import desafio.urban_potato.exceptions.SessaoEncerradaException;
import desafio.urban_potato.exceptions.SessaoNaoExisteException;
import desafio.urban_potato.exceptions.VotoJaCadastradoException;

@SpringBootTest
@ActiveProfiles("test")
class CreateVotoUCTests {
	
	@Autowired CreateVotoUC uc;
	@MockBean SessaoService sessaoService;
	@MockBean AssociadoService associadoService;
	@MockBean VotoService votoService;
	
	@Test void sessao_NaoExiste() {
		when(sessaoService.get("sessao"))
		.thenReturn(Optional.empty());
		
		assertThrows(SessaoNaoExisteException.class, 
				() -> uc.create("sessao", "cpf", "sim"));
	}
	
	@Test void sessao_Encerrada() {
		when(sessaoService.get("sessao"))
		.thenReturn(Optional.of(Sessao.builder()
				.tsFim(LocalDateTime.now().minusMinutes(1))
				.build()));
		
		assertThrows(SessaoEncerradaException.class, 
				() -> uc.create("sessao", "cpf", "sim"));
	}

	@Test void voto_Cadastrado() {
		when(sessaoService.get("sessao"))
		.thenReturn(Optional.of(Sessao.builder()
				.tsFim(LocalDateTime.now().plusMinutes(1))
				.build()));
		
		when(associadoService.get("cpf"))
		.thenReturn(Optional.of(Associado.builder()
				.cpf("cpf")
				.build()));
		
		when(votoService.getBySessaoAndAssociado("sessao", "cpf"))
			.thenReturn(Optional.of(Voto.builder()
					.sessao("sessao")
					.associado("cpf")
					.build()));
		
		assertThrows(VotoJaCadastradoException.class, 
				() -> uc.create("sessao", "cpf", "sim"));
	}

	@Test void create() {
		when(sessaoService.get("sessao"))
		.thenReturn(Optional.of(Sessao.builder()
				.tsFim(LocalDateTime.now().plusMinutes(1))
				.build()));
		
		when(associadoService.get("cpf"))
			.thenReturn(Optional.empty());
		
		when(associadoService.create(any()))
		.thenAnswer(new Answer<Associado>() {

			@Override
			public Associado answer(InvocationOnMock invocation) throws Throwable {
				Associado a = invocation.getArgument(0);
				assertNotNull(a);
				assertEquals("cpf", a.getCpf());
				return a;
			}
		});
		
		when(votoService.getBySessaoAndAssociado("sessao", "cpf"))
		.thenReturn(Optional.of(Voto.builder()
				.sessao("sessao")
				.associado("cpf")
				.build()));
		
		assertThrows(VotoJaCadastradoException.class, 
				() -> uc.create("sessao", "cpf", "sim"));
	}

}
