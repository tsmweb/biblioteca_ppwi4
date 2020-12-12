package br.com.tsmweb.biblioteca.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tsmweb.biblioteca.models.model.Livro;
import br.com.tsmweb.biblioteca.models.repository.query.LivroQuery;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>, LivroQuery {

}
