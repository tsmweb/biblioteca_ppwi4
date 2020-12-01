package br.com.tsmweb.biblioteca.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tsmweb.biblioteca.models.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
