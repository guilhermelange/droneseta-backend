package com.udesc.droneseta.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {	
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ErrorDetails> appException(ApplicationException ex) {
		ErrorDetails errorModel = new ErrorDetails(ex.getStatus().value(), ex.getMessage());
		return new ResponseEntity <ErrorDetails> (errorModel, ex.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity <?> globleExcpetionHandler(Exception ex) {
		ErrorDetails errorModel = new ErrorDetails(500, ex.getMessage());
		return new ResponseEntity <> (errorModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
