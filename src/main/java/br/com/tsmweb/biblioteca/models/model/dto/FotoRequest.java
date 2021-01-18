package br.com.tsmweb.biblioteca.models.model.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class FotoRequest implements Serializable {

	private static final long serialVersionUID = 6507696235627577196L;
	
	private String id;
	private String nomeArquivo;
	private String contentType;
	private MultipartFile foto;
	
	public FotoRequest(String id, String nomeArquivo, String contentType, MultipartFile foto) {
		this.id = id;
		this.nomeArquivo = nomeArquivo;
		this.contentType = contentType;
		this.foto = foto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public MultipartFile getFoto() {
		return foto;
	}

	public void setFoto(MultipartFile foto) {
		this.foto = foto;
	}
	
}
