package desafio.urban_potato.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import desafio.urban_potato.controller.res.ResPautaVO;
import desafio.urban_potato.controller.res.ResSessaoVO;
import desafio.urban_potato.controller.res.ResVotoVO;
import desafio.urban_potato.domain.pauta.Pauta;
import desafio.urban_potato.domain.pauta.PautaService;
import desafio.urban_potato.domain.sessao.Sessao;
import desafio.urban_potato.domain.sessao.SessaoService;
import desafio.urban_potato.domain.voto.VotoService;

@SpringBootTest
@ActiveProfiles("test")
class GetPautaUCTests {
	
	@Autowired GetPautaUC uc;
	@MockBean PautaService pautaService;
	@MockBean SessaoService sessaoService;
	@MockBean VotoService votoService;
	
	@Test void getAll_SemPauta() {
		when(pautaService.getAll())
			.thenReturn(new ArrayList<>());
		
		try {
			uc.getAll();
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test void getAll_SemSessao() {
		when(pautaService.getAll())
		.thenReturn(Arrays.asList(Pauta.builder()
				.id("pauta")
				.build()));
	
		when(sessaoService.getByPauta("pauta"))
			.thenReturn(Optional.empty());
		
		try {
			uc.getAll();
		} catch (Exception e) {
			fail(e);
		}
		
		verify(votoService, times(0)).count(any(), any());
	}

	@Test void getAll_SemVotos() {
		when(pautaService.getAll())
		.thenReturn(Arrays.asList(Pauta.builder()
				.id("pauta")
				.build()));
		
		when(sessaoService.getByPauta("pauta"))
		.thenReturn(Optional.of(Sessao.builder()
				.id("sessao")
				.build()));
		
		try {
			uc.getAll();
		} catch (Exception e) {
			fail(e);
		}
		
		verify(votoService, times(2)).count(any(), any());
	}

	@Test void getAll() {
		when(pautaService.getAll())
		.thenReturn(Arrays.asList(Pauta.builder()
				.id("pauta")
				.build()));
		
		when(sessaoService.getByPauta("pauta"))
		.thenReturn(Optional.of(Sessao.builder()
				.id("sessao")
				.build()));
		
		when(votoService.count(any(), any()))
		.thenReturn(2L)
		.thenReturn(5L);
		
		try {
			List<ResPautaVO> all = uc.getAll();
			assertNotNull(all);
			ResPautaVO resPautaVO = all.get(0);
			assertNotNull(resPautaVO);
			ResSessaoVO sessao = resPautaVO.sessao();
			assertNotNull(sessao);
			List<ResVotoVO> votos = sessao.votos();
			assertNotNull(votos);
			assertEquals(2, votos.size());
		} catch (Exception e) {
			fail(e);
		}
		
		verify(votoService, times(2)).count(any(), any());
	}
	
}
