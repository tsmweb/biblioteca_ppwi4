package br.com.tsmweb.biblioteca.models.service.exception;

public class NegocioException extends RuntimeException{

	private static final long serialVersionUID = 9141935067594690931L;

	public NegocioException(String message) {
		super(message);
	}
	
}
