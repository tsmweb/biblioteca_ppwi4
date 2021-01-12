package br.com.tsmweb.biblioteca.web.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.tsmweb.biblioteca.models.model.dto.ExcluirFoto;
import br.com.tsmweb.biblioteca.models.model.dto.Foto;
import br.com.tsmweb.biblioteca.models.service.LocalFotosStorageService;
import br.com.tsmweb.biblioteca.models.service.exception.FileStorageException;

@RestController
@RequestMapping(value = "/foto")
public class FotoRestController {
	
	@Autowired
	private LocalFotosStorageService localFotosStorageService; 
	
	@PostMapping(value = "/gravar", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Foto> uploadFoto(@RequestParam MultipartFile foto) {
		String nomeFoto = localFotosStorageService.gerarNomeArquivo(foto.getOriginalFilename());
		Foto arquivoFoto = new Foto();
		
		try {	
			arquivoFoto.setNomeArquivo(nomeFoto);
			arquivoFoto.setInputStream(foto.getInputStream());
			arquivoFoto.setContentType(foto.getContentType());
			localFotosStorageService.armazenar(arquivoFoto);
		} catch (FileStorageException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileStorageException("Erro na gravação da foto", e.getCause());
		}
		
		return ResponseEntity.ok(arquivoFoto);
	}
	
	@PostMapping(value = "/excluir", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExcluirFoto> excluirFoto(@RequestBody ExcluirFoto foto) {		
		try {
			localFotosStorageService.remover(foto);
			foto.setNomeArquivo("default-avatar.png");
		} catch (FileStorageException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(foto);
	}
	
	@GetMapping("/{nomeFoto}")
	public byte[] recuperarFoto(@PathVariable String nomeFoto) {
		return localFotosStorageService.recuperarFoto(nomeFoto);
	}

}
