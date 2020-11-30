package br.com.tsmweb.biblioteca.models.repository.query;

import java.util.Optional;

import br.com.tsmweb.biblioteca.models.model.Usuario;

public interface UsuarioQuery {

	Optional<Usuario> findUsuarioByEmail(String email);
	
}
