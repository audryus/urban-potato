package desafio.urban_potato.exceptions;

import org.springframework.http.HttpStatus;

public class PautaSemNomeException extends ApiException {

	private static final long serialVersionUID = 1L;
	
	public PautaSemNomeException() {
		super("ERR_PAUTA_SEM_NOME", 
				HttpStatus.BAD_REQUEST);
	}

}
