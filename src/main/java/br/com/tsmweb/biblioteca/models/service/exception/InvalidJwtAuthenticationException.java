package br.com.tsmweb.biblioteca.models.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidJwtAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = -6185800400909912331L;

	public InvalidJwtAuthenticationException(String msg) {
		super(msg);
	}

}
