package br.com.tsmweb.biblioteca.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tsmweb.biblioteca.models.model.Editora;

public interface EditoraRepository extends JpaRepository<Editora, Long> {

}
