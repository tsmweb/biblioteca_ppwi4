package br.com.tsmweb.biblioteca.models.model.dto;

import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Foto {
	
	private String nomeArquivo;
	
	@JsonIgnore
	private InputStream inputStream;
	
	private String contentType;

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
