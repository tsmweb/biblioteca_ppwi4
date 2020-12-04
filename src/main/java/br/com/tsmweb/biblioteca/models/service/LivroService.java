package br.com.tsmweb.biblioteca.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tsmweb.biblioteca.models.model.Livro;
import br.com.tsmweb.biblioteca.models.repository.LivroRepository;

@Service
@Transactional
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;
	
	public Livro save(Livro livro) {
		return livroRepository.save(livro);
	}
	
	public Livro update(Livro livro) {
		return save(livro);
	}
	
	public void deleteById(Long id) {
		livroRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Livro findById(Long id) {
		return livroRepository.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<Livro> findAll() {
		return livroRepository.findAll();
	}
}
