package br.com.tsmweb.biblioteca.models.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.repository.query.UsuarioQuery;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioQuery {

	@Query("SELECT u FROM Usuario u "
			+ "LEFT JOIN FETCH u.roles "
			+ "LEFT JOIN FETCH u.departamento "
			+ "WHERE u.id = :id")
	Optional<Usuario> findUsuarioById(@Param("id") Long id);
	
	
//	@Query("SELECT u FROM Usuario u "
//			+ "LEFT JOIN FETCH u.departamento "
//			+ "LEFT JOIN FETCH u.roles")
//	Page<Usuario> findAll(Pageable pageable);

	@Query("SELECT u FROM Usuario u "
			+ "WHERE u.username LIKE (CONCAT ('%', :name, '%'))")
	Page<Usuario> findUserByName(@Param("name") String name, Pageable pageable);
	
}
