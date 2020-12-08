package br.com.tsmweb.biblioteca.models.repository.queryimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.thymeleaf.util.StringUtils;

import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.repository.filtros.UsuarioFiltro;
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

	@Override
	public Page<Usuario> listUsuarioByPage(UsuarioFiltro usuarioFiltro, Pageable pageable) {
		List<Usuario> lista = new ArrayList<>();
		TypedQuery<Usuario> query = null;
		
		int totalRegistrosPorPagina = pageable.getPageSize();
		int paginaAtual = pageable.getPageNumber();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
		Root<Usuario> root = cq.from(Usuario.class);
		
		Predicate predicate = predicados(cb, root, usuarioFiltro);
		
		sortQuery(pageable, cb, cq, root);
		
		if (!Objects.isNull(predicate)) {
			cq.where(predicate);
		}
		
		query = entityManager.createQuery(cq);
		query.setFirstResult(primeiroRegistro);
		query.setMaxResults(totalRegistrosPorPagina);
		
		lista = query.getResultList();
		
		return new PageImpl<Usuario>(lista, pageable, totalRegistros(predicate));
	}

	private Predicate predicados(CriteriaBuilder cb, Root<Usuario> root, UsuarioFiltro usuarioFiltro) {
		Predicate predicates = null;
		
		if (!StringUtils.isEmpty(usuarioFiltro.getNome())) {
			predicates = cb.like(cb.lower(root.get("username")), "%"+usuarioFiltro.getNome()+"%");
		}
		
		return predicates;
	}
	
	private void sortQuery(Pageable pageable, CriteriaBuilder cb, CriteriaQuery<Usuario> cq, Root<Usuario> root) {
		Sort sort = pageable.getSort();
		
		if (!sort.isSorted()) {
			Sort.Order order = sort.iterator().next();
			String propriedade = order.getProperty();
			cq.orderBy(order.isAscending() ? cb.asc(root.get(propriedade)) : cb.desc(root.get(propriedade)));
		}
	}
	
	private Long totalRegistros(Predicate predicate) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Usuario> root = cq.from(Usuario.class);
		
		cq.select(cb.count(root));
		
		if (!Objects.isNull(predicate)) {
			cq.where(predicate);
		}
		
		TypedQuery<Long> query = entityManager.createQuery(cq);
		
		return query.getSingleResult();
	}

}
