package desafio.urban_potato.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import desafio.urban_potato.controller.res.ResReplyVO;
import desafio.urban_potato.exceptions.ApiException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ApiException.class)
    public ResponseEntity<ResReplyVO> handle(ApiException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ResReplyVO(ex.getErr()));
    }
	
}
