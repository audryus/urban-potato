package desafio.urban_potato.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import desafio.urban_potato.controller.req.ReqPautaDTO;
import desafio.urban_potato.controller.res.ResPautaDTO;
import desafio.urban_potato.usecase.CreatePautaUC;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("pautas")
@RequiredArgsConstructor
public class PautaController {
	
	private final CreatePautaUC createPauta;
	
	@PostMapping
	public ResPautaDTO create(@RequestBody ReqPautaDTO pauta) {
		return createPauta.create(pauta);
	}
	
}
