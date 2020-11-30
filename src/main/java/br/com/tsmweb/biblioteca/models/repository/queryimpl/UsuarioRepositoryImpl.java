package br.com.tsmweb.biblioteca.models.repository.queryimpl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.repository.query.UsuarioQuery;

public class UsuarioRepositoryImpl implements UsuarioQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Optional<Usuario> findUsuarioByEmail(String email) {
		TypedQuery<Usuario> consultaUsuarioEmail = entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
		
		consultaUsuarioEmail.setParameter("email", email);
		
		return consultaUsuarioEmail
					.setMaxResults(1)
					.getResultList()
					.stream()
					.findFirst();
	}

}
