package br.com.tsmweb.biblioteca.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.repository.query.UsuarioQuery;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioQuery {

//	@Query("SELECT u FROM Usuario u "
//			+ "LEFT JOIN FETCH u.departamento "
//			+ "WHERE u.id = :id")
//	Usuario findUsuarioById(@Param("id") Long id);

}
