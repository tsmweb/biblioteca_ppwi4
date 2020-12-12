package br.com.tsmweb.biblioteca.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.repository.EditoraRepository;
import br.com.tsmweb.biblioteca.models.repository.filtros.EditoraFiltro;

@Service
@Transactional
public class EditoraService {

	@Autowired
	private EditoraRepository editoraRepository;
	
	public Editora save(Editora editora) {
		return editoraRepository.save(editora);
	}
	
	public Editora update(Editora editora) {
		return save(editora);
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
		return editoraRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@Transactional(readOnly = true)
	public Page<Editora> listEditoraByPage(EditoraFiltro editoraFiltro, Pageable pageable) {
		return editoraRepository.listEditoraByPage(editoraFiltro, pageable);
	}
	
}
