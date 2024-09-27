package desafio.urban_potato.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import desafio.urban_potato.controller.req.ReqPautaVO;
import desafio.urban_potato.controller.res.ResPautaVO;
import desafio.urban_potato.usecase.CreatePautaUC;
import desafio.urban_potato.usecase.GetPautaUC;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("pautas")
@RequiredArgsConstructor
public class PautaController {
	
	private final CreatePautaUC createPauta;
	private final GetPautaUC getPauta;
	
	@GetMapping
	public List<ResPautaVO> get() {
		return getPauta.getAll();
	}
	
	@PostMapping
	public ResPautaVO create(@RequestBody ReqPautaVO pauta) {
		return createPauta.create(pauta);
	}
	
}
