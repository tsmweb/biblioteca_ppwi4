package br.com.tsmweb.biblioteca.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tsmweb.biblioteca.models.model.Emprestimo;
import br.com.tsmweb.biblioteca.models.model.Usuario;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

	@Query("SELECT e FROM Emprestimo e WHERE e.user = :user")
	List<Emprestimo> findByUser(@Param("user") Usuario usuario);
	
}
