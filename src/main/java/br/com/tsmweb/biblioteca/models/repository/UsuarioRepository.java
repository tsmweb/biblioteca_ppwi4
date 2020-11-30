package br.com.tsmweb.biblioteca.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.repository.query.UsuarioQuery;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioQuery {

}
