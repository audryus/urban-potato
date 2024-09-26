package desafio.urban_potato.exceptions;

import org.springframework.http.HttpStatus;

public class PautaNaoExisteException extends ApiException {

	private static final long serialVersionUID = 1L;

	public PautaNaoExisteException() {
		super("ERR_PAUTA_NAO_EXISTE", 
				HttpStatus.NOT_FOUND);
	}

}
