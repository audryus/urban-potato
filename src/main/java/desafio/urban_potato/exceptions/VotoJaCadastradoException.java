package desafio.urban_potato.exceptions;

import org.springframework.http.HttpStatus;

public class VotoJaCadastradoException extends ApiException {

	private static final long serialVersionUID = 1L;

	public VotoJaCadastradoException() {
		super("ERR_VOTO_CADASTRADO", 
				HttpStatus.CONFLICT);
	}

}
