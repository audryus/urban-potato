package desafio.urban_potato.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import desafio.urban_potato.controller.req.ReqVotoVO;
import desafio.urban_potato.controller.res.ResReplyVO;
import desafio.urban_potato.usecase.CreateVotoUC;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("sessoes/{sessao}/votos")
@RequiredArgsConstructor
public class VotoController {
	
	private final CreateVotoUC createVotoUC;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResReplyVO create(
			@PathVariable(name = "sessao") String sessao,
			@RequestBody ReqVotoVO voto
			) {
		createVotoUC.create( 
				sessao, 
				voto.associado(), 
				voto.voto());
		
		return new ResReplyVO("RPL_VOTO");
	}
	
}
