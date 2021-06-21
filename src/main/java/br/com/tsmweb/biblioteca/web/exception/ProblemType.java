package br.com.tsmweb.biblioteca.web.exception;

public enum ProblemType {
	
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "O recurso solicitado não foi localizado"),
	REGISTRO_NAO_ENCONTRADO("/registro-nao-encontrado", "O registro solicitado não foi localizado"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	SERVICO_EMAIL("/erro-servidor-email", "Erro no serviço de e-mail"),
	USUARIO_BLOQUEADO("/usuario-bloqueado", "Usuário bloqueado"),
	ERRO_INTERNO("/erro-interno", "Erro interno do sistema");
	
	private String uri;
	private String title;
	
	private ProblemType(String uri, String title) {
		this.uri = uri;
		this.title = title;
	}

	public String getUri() {
		return uri;
	}

	public String getTitle() {
		return title;
	}

}
