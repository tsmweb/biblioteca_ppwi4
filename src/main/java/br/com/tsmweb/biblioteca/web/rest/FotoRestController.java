package br.com.tsmweb.biblioteca.web.rest;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.tsmweb.biblioteca.models.model.dto.FotoRequest;
import br.com.tsmweb.biblioteca.models.model.dto.Foto;
import br.com.tsmweb.biblioteca.models.service.LocalFotosStorageService;
import br.com.tsmweb.biblioteca.models.service.exception.FileStorageException;

public class FotoRestController {
	
	private LocalFotosStorageService localFotosStorageService; 
	
	public FotoRestController(LocalFotosStorageService localFotosStorageService) {
		this.localFotosStorageService = localFotosStorageService;
	}

	@PostMapping(value = "/gravar", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Foto> uploadFoto(FotoRequest fotoRequest) {
		Long id = convertIdToLong(fotoRequest);
        Foto arquivoFoto = new Foto();
		
        try {
			arquivoFoto.setId(id);
			arquivoFoto.setNomeArquivo(fotoRequest.getFoto().getOriginalFilename());
			arquivoFoto.setInputStream(fotoRequest.getFoto().getInputStream());
			arquivoFoto.setContentType(fotoRequest.getFoto().getContentType());
			arquivoFoto = localFotosStorageService.armazenar(arquivoFoto);
		} catch (IOException e) {
			throw new FileStorageException("Falha na gravação do arquivo de foto", e);
		}
		
        return ResponseEntity.ok().body(arquivoFoto);
	}
	
	@PostMapping(value = "/delete", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FotoRequest> excluirFoto(@RequestBody FotoRequest fotoRequest) {		
		Long id = convertIdToLong(fotoRequest);
		Foto arquivoFoto = new Foto();
		arquivoFoto.setId(id);
		arquivoFoto.setNomeArquivo(fotoRequest.getNomeArquivo());
		arquivoFoto = localFotosStorageService.excluirFoto(arquivoFoto);
		fotoRequest.setNomeArquivo(arquivoFoto.getNomeArquivo());
		
		return ResponseEntity.ok().body(fotoRequest);
	}
	
	@GetMapping("/{nomeFoto}")
	public byte[] recuperarFoto(@PathVariable String nomeFoto) {
		return localFotosStorageService.recuperarFoto(nomeFoto);
	}
	
	private Long convertIdToLong(FotoRequest fotoRequest) {
		return "".equals(fotoRequest.getId()) ?  0L : Long.valueOf(Long.valueOf(fotoRequest.getId()));
	}

}
