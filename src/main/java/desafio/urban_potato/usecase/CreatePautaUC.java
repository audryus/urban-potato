package desafio.urban_potato.usecase;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import desafio.urban_potato.controller.req.ReqPautaVO;
import desafio.urban_potato.controller.res.ResPautaVO;
import desafio.urban_potato.domain.pauta.Pauta;
import desafio.urban_potato.domain.pauta.PautaService;
import desafio.urban_potato.exceptions.PautaSemNomeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePautaUC {
	
	private final PautaService pautaService;
	
	public ResPautaVO create(ReqPautaVO request) {
		if (StringUtils.isEmpty(request.nome())) {
			throw new PautaSemNomeException();
		}
		
		var pauta = pautaService.create(
				Pauta.builder()
				.id(UUID.randomUUID().toString())
				.nome(request.nome())
				.build());
		
		log.info("[PAUTA] {} criada", pauta);
		
		return new ResPautaVO(
				pauta.getId(), 
				pauta.getNome(),
				pauta.getTsCriacao(),
				null);
	}

}
