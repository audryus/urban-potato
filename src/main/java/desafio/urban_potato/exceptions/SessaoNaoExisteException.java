package desafio.urban_potato.exceptions;

import org.springframework.http.HttpStatus;

public class SessaoNaoExisteException extends ApiException {
	
	private static final long serialVersionUID = 1L;

	public SessaoNaoExisteException() {
		super("ERR_SESSAO_NAO_EXISTE", 
				HttpStatus.NOT_FOUND);
	}

}
