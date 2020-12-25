package br.com.tsmweb.biblioteca.models.service.exception;

public class EntidadeNaoCadastradaException extends NegocioException {

	private static final long serialVersionUID = -400100735933097488L;

	public EntidadeNaoCadastradaException(String message) {
		super(message);
	}

}
