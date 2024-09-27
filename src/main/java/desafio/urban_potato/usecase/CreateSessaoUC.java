package desafio.urban_potato.usecase;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import desafio.urban_potato.controller.req.ReqAberturaSessaoVO;
import desafio.urban_potato.controller.res.ResSessaoVO;
import desafio.urban_potato.domain.pauta.PautaService;
import desafio.urban_potato.domain.sessao.Sessao;
import desafio.urban_potato.domain.sessao.SessaoService;
import desafio.urban_potato.exceptions.PautaNaoExisteException;
import desafio.urban_potato.exceptions.PautaPossuiSessaoException;
import desafio.urban_potato.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateSessaoUC {
	
	private final PautaService pautaService;
	private final SessaoService sessaoService;
	private final AppProperties props;
	
	@Transactional
	public ResSessaoVO create(
			String pauta, 
			ReqAberturaSessaoVO abertura) {
		if (pautaService.get(pauta).isEmpty()) {
			throw new PautaNaoExisteException();
		}
		
		if (sessaoService.getByPauta(pauta).isPresent()) {
			throw new PautaPossuiSessaoException();
		}
		
		int duracao = props.getDuracaoSessao();
		if (abertura.duracao() != null 
				&& abertura.duracao() > duracao) {
			duracao = abertura.duracao();
		}
		
		var sessao = sessaoService.create(
				Sessao.builder()
				.pauta(pauta)
				.tsFim(LocalDateTime.now()
						.plusMinutes(duracao))
				.build());
		
		log.info("[SESSAO] {} criada.", sessao);
		
		return new ResSessaoVO(
				sessao.getId(), 
				pauta, 
				sessao.getTsCriacao(), 
				sessao.getTsFim(), 
				null);
	}

}
