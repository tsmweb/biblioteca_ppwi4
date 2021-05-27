package br.com.tsmweb.biblioteca.models.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tsmweb.biblioteca.models.model.Livro;
import br.com.tsmweb.biblioteca.models.repository.query.LivroQuery;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>, LivroQuery {

	@Query("SELECT l FROM Livro l "
			+ "INNER JOIN l.publisher "
			+ "WHERE l.title LIKE (CONCAT ('%', :title, '%'))")
	Page<Livro> findBookByTitle(@Param("title") String title, Pageable pageable);
	
	@Query("SELECT l FROM Livro l "
			+ "INNER JOIN FETCH l.publisher "
			+ "WHERE l.id = :id")
	Optional<Livro> findLivroById(@Param("id") Long id);
	
}
