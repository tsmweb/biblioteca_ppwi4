package br.com.tsmweb.biblioteca.models.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.repository.query.EditoraQuery;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long>, EditoraQuery {

	@Query("SELECT e FROM Editora e WHERE e.name LIKE (CONCAT ('%', :name, '%'))")
	Page<Editora> findPublisherByName(@Param("name") String name, Pageable pageable);

	@Query("SELECT e FROM Editora e WHERE e.id = :id")
	Optional<Editora> findEditoraById(@Param("id") Long id);
	
}
