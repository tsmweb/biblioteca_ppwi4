package br.com.tsmweb.biblioteca.models.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.repository.filtros.EditoraFiltro;

public interface EditoraQuery {

	Page<Editora> listEditoraByPage(EditoraFiltro editoraFiltro, Pageable pageable);
	
}
