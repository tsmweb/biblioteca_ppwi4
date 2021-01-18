package br.com.tsmweb.biblioteca.models.model.dto;

import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Foto {
	
	private Long id;
	private String nomeArquivo;
	
	@JsonIgnore
	private InputStream inputStream;
	
	private String contentType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
