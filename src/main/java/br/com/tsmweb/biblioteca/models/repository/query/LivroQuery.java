package br.com.tsmweb.biblioteca.models.repository.query;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.tsmweb.biblioteca.models.model.Livro;
import br.com.tsmweb.biblioteca.models.repository.filtros.LivroFiltro;

public interface LivroQuery {

	Page<Livro> listLivroByPage(LivroFiltro livroFiltro, Pageable pageable);
	Optional<Livro> findLivroById(Long id);
	
}
