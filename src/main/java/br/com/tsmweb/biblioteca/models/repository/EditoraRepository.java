package br.com.tsmweb.biblioteca.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.repository.query.EditoraQuery;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long>, EditoraQuery {

}
