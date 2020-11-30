package br.com.tsmweb.biblioteca.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tsmweb.biblioteca.models.model.Emprestimo;
import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.repository.EmprestimoRepository;

@Service
@Transactional
public class EmprestimoService {

	@Autowired
	private EmprestimoRepository emprestimoRepository;
	
	public Emprestimo save(Emprestimo emprestimo) {
		return emprestimoRepository.save(emprestimo);
	}
	
	public void update(Emprestimo emprestimo) {
		save(emprestimo);
	}
	
	public void deleteById(Long id) {
		emprestimoRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Emprestimo findById(Long id) {
		return emprestimoRepository.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<Emprestimo> findAll() {
		return emprestimoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Emprestimo> findByUser(Usuario usuario) {
		return emprestimoRepository.findByUser(usuario);
	}
	
}
