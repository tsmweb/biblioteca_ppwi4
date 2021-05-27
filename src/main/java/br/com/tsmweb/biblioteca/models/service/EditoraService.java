package br.com.tsmweb.biblioteca.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.repository.EditoraRepository;
import br.com.tsmweb.biblioteca.models.repository.filtros.EditoraFiltro;
import br.com.tsmweb.biblioteca.models.service.exception.EntidadeNaoCadastradaException;
import br.com.tsmweb.biblioteca.models.service.exception.IdNaoPodeSerZeroNuloException;
import br.com.tsmweb.biblioteca.web.response.ResponseSelect2Data;

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
		if (id <= 0) {
			throw new IdNaoPodeSerZeroNuloException("Identificador do Usuário inválido!");
		}
		
		editoraRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Editora findById(Long id) {
		if (id <= 0) {
			throw new IdNaoPodeSerZeroNuloException("Identificador do Usuário inválido!");
		}
		
		return editoraRepository.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public Page<Editora> findPublisherByName(String name, Pageable pageable) {
		return editoraRepository.findPublisherByName(name, pageable);
	}
	
	@Transactional(readOnly = true)
	public List<Editora> findAll() {
		return editoraRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@Transactional(readOnly = true)
	public Page<Editora> findAll(Pageable pageable) {
		return editoraRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<Editora> listEditoraByPage(EditoraFiltro editoraFiltro, Pageable pageable) {
		return editoraRepository.listEditoraByPage(editoraFiltro, pageable);
	}
	
	@Transactional(readOnly = true)
	public List<ResponseSelect2Data> buscaSemParametro() {
		return findAll()
				.stream()
				.limit(15)
				.map(e -> editoraToResponseSelect2Data(e))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ResponseSelect2Data> buscaPorParametro(String query) {
		return findAll()
				.stream()
				.limit(15)
				.filter(e -> e.getName().toLowerCase().contains(query.toLowerCase()))
				.map(e -> editoraToResponseSelect2Data(e))
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Editora findEditoraById(Long id) {
		return editoraRepository.findEditoraById(id)
				.orElseThrow(() -> new EntidadeNaoCadastradaException("Editora não está cadastrada"));
		
	}
	
	private ResponseSelect2Data editoraToResponseSelect2Data(Editora e) {
		return new ResponseSelect2Data(e.getId().toString(), e.getName());
	}
	
}
