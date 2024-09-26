package desafio.urban_potato.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String err;
	private final HttpStatus status;

	public ApiException(String err, HttpStatus status) {
		this.err = err;
		this.status = status;
	}

	public String getErr() {
		return err;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
