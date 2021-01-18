package br.com.tsmweb.biblioteca.models.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tsmweb.biblioteca.models.model.Livro;

@Service
public class LivroFotosStorageService extends LocalFotosStorageService {

	@Autowired
	private LivroService livroService;
	
	@Override
	protected Optional<String> buscarNomeArquivoPorId(Long id) {
		Livro livro = null;
		
		if ( id != 0L) {
			livro = livroService.findLivroById(id);
		}
		
		return Optional.of(livro.getPhoto());
	}
	
	@Override
	protected String getNomeArquivoDefault() {
		return "default-image.png";
	}
	
}
