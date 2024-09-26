package desafio.urban_potato.exceptions;

import org.springframework.http.HttpStatus;

public class PautaPossuiSessaoException extends ApiException {

	private static final long serialVersionUID = 1L;

	public PautaPossuiSessaoException() {
		super("ERR_PAUTA_POSSUI_SESSAO", 
				HttpStatus.NOT_ACCEPTABLE);
	}

}
