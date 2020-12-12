package br.com.tsmweb.biblioteca.models.repository.queryimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

import br.com.tsmweb.biblioteca.models.model.Livro;
import br.com.tsmweb.biblioteca.models.repository.filtros.LivroFiltro;
import br.com.tsmweb.biblioteca.models.repository.query.LivroQuery;

public class LivroQueryImpl implements LivroQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Page<Livro> listLivroByPage(LivroFiltro livroFiltro, Pageable pageable) {
		List<Livro> lista = new ArrayList<>();
		TypedQuery<Livro> query = null;
		
		int totalRegistrosPorPagina = pageable.getPageSize();
		int paginaAtual = pageable.getPageNumber();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Livro> cq = cb.createQuery(Livro.class);
		Root<Livro> root = cq.from(Livro.class);
		
		Predicate predicate = predicados(cb, root, livroFiltro);
		
		sortQuery(pageable, cb, cq, root);
		
		if (!Objects.isNull(predicate)) {
			cq.where(predicate);
		}
		
		query = entityManager.createQuery(cq);
		query.setFirstResult(primeiroRegistro);
		query.setMaxResults(totalRegistrosPorPagina);
		
		lista = query.getResultList();
		
		return new PageImpl<Livro>(lista, pageable, totalRegistros(predicate));
	}

	private Predicate predicados(CriteriaBuilder cb, Root<Livro> root, LivroFiltro livroFiltro) {
		Predicate predicates = null;
		
		if (!StringUtils.isEmpty(livroFiltro.getTitle())) {
			predicates = cb.like(cb.lower(root.get("title")), "%"+livroFiltro.getTitle()+"%");
		}
		
		return predicates;
	}
	
	private void sortQuery(Pageable pageable, CriteriaBuilder cb, CriteriaQuery<Livro> cq, Root<Livro> root) {
		Sort sort = pageable.getSort();
		Sort.Order order = sort.iterator().next();
		String propriedade = order.getProperty();
		cq.orderBy(order.isAscending() ? cb.asc(root.get(propriedade)) : cb.desc(root.get(propriedade)));
	}
	
	private Long totalRegistros(Predicate predicate) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Livro> root = cq.from(Livro.class);
		
		cq.select(cb.count(root));
		
		if (!Objects.isNull(predicate)) {
			cq.where(predicate);
		}
		
		TypedQuery<Long> query = entityManager.createQuery(cq);
		
		return query.getSingleResult();
	}
	
}
