package br.com.tsmweb.biblioteca.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.repository.EditoraRepository;

@Service
@Transactional
public class EditoraService {

	@Autowired
	private EditoraRepository editoraRepository;
	
	public Editora save(Editora editora) {
		return editoraRepository.save(editora);
	}
	
	public void update(Editora editora) {
		save(editora);
	}
	
	public void deleteById(Long id) {
		editoraRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Editora findById(Long id) {
		return editoraRepository.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<Editora> findAll() {
		return editoraRepository.findAll();
	}
	
}
