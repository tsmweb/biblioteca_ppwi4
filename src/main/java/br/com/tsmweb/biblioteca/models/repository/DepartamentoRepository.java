package br.com.tsmweb.biblioteca.models.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tsmweb.biblioteca.models.model.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

	@Query("SELECT d FROM Departamento d WHERE d.id = :id")
	Optional<Departamento> findDepartamentoById(@Param("id") Long id);

}
