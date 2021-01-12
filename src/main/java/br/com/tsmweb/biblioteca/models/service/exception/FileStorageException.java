package br.com.tsmweb.biblioteca.models.service.exception;

public class FileStorageException extends NegocioException {

	private static final long serialVersionUID = -1483899490400588171L;

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}

}
