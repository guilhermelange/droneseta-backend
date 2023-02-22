package com.udesc.droneseta.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private HttpStatus status;

	public ApplicationException(String message) {
		super(message);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	
	public ApplicationException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
}
