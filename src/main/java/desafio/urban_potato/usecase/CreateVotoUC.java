package desafio.urban_potato.usecase;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import desafio.urban_potato.domain.associado.Associado;
import desafio.urban_potato.domain.associado.AssociadoService;
import desafio.urban_potato.domain.sessao.SessaoService;
import desafio.urban_potato.domain.voto.Escolha;
import desafio.urban_potato.domain.voto.Voto;
import desafio.urban_potato.domain.voto.VotoService;
import desafio.urban_potato.exceptions.SessaoEncerradaException;
import desafio.urban_potato.exceptions.SessaoNaoExisteException;
import desafio.urban_potato.exceptions.VotoJaCadastradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateVotoUC {

	private final SessaoService sessaoService;
	private final AssociadoService associadoService;
	private final VotoService votoService;
	
	@Transactional
	public void create(
			String sessaoID, 
			String cpf, 
			String escolha) {
		var sessao = sessaoService.get(sessaoID)
				.orElseThrow(SessaoNaoExisteException::new);
		
		if (LocalDateTime.now().isAfter(sessao.getTsFim())) {
			throw new SessaoEncerradaException();
		}
		
		var numerosCPF = removerMascara(cpf);
		
		var associado = associadoService.get(numerosCPF)
				.orElseGet(() -> 
					associadoService.create(Associado.builder()
							.cpf(numerosCPF)
							.build()));
		
		var voto = votoService.getBySessaoAndAssociado(
				sessaoID, associado.getCpf())
			.orElse(new Voto());
		
		check(voto);
		
		var escolhaValida = Escolha.from(escolha);
		
		voto.setAssociado(associado.getCpf());
		voto.setSessao(sessaoID);
		voto.setEscolha(escolhaValida);
		
		votoService.create(voto);
		
		log.info("[VOTO] {} criado", voto);
	}
	
	private String removerMascara(String str){ 
		return str.replaceAll("\\D", ""); 
	}
	
	private void check(Voto voto) {
		if (StringUtils.isNotEmpty(voto.getAssociado())
				&& StringUtils.isNotEmpty(voto.getSessao())) {
			throw new VotoJaCadastradoException();
		}
	}

}
