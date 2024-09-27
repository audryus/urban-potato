package desafio.urban_potato.exceptions;

import org.springframework.http.HttpStatus;

public class SessaoEncerradaException extends ApiException {

	private static final long serialVersionUID = 1L;

	public SessaoEncerradaException() {
		super("ERR_SESSAO_ENCERRADA", 
				HttpStatus.PRECONDITION_FAILED);
	}

}
