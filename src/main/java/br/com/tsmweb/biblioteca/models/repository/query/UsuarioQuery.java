package br.com.tsmweb.biblioteca.models.repository.query;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.repository.filtros.UsuarioFiltro;

public interface UsuarioQuery {

	Optional<Usuario> findUsuarioByEmail(String email);
	Page<Usuario> listUsuarioByPage(UsuarioFiltro usuarioFiltro, Pageable pageable);
//	Optional<Usuario> findUsuarioById(Long id);
	
}
