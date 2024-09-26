package desafio.urban_potato.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import desafio.urban_potato.controller.req.ReqAberturaSessaoDTO;
import desafio.urban_potato.domain.sessao.Sessao;
import desafio.urban_potato.usecase.CreateSessaoUC;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("pautas/{pauta}/sessoes")
@RequiredArgsConstructor
public class SessaoController {
	
	private final CreateSessaoUC createSessaoUC;

	@PostMapping
	public Sessao create(
			@PathVariable(name = "pauta") String pauta,
			@RequestBody ReqAberturaSessaoDTO abertura
			) {
		return createSessaoUC.create(pauta, abertura);
	}
	
	

}
