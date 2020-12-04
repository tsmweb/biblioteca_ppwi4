package br.com.tsmweb.biblioteca.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tsmweb.biblioteca.models.model.Departamento;
import br.com.tsmweb.biblioteca.models.repository.DepartamentoRepository;

@Service
@Transactional
public class DepartamentoService {

	@Autowired
	private DepartamentoRepository departamentoRepository;
	
	public Departamento save(Departamento departamento) {
		return departamentoRepository.save(departamento);
	}
	
	public Departamento update(Departamento departamento) {
		return save(departamento);
	}
	
	public void deleteById(Long id) {
		departamentoRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Departamento findById(Long id) {
		return departamentoRepository.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<Departamento> findAll(){
		return departamentoRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
}
