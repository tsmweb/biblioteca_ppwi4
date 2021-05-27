package br.com.tsmweb.biblioteca.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tsmweb.biblioteca.models.model.Departamento;
import br.com.tsmweb.biblioteca.models.repository.DepartamentoRepository;
import br.com.tsmweb.biblioteca.models.service.exception.EntidadeNaoCadastradaException;
import br.com.tsmweb.biblioteca.models.service.exception.IdNaoPodeSerZeroNuloException;
import br.com.tsmweb.biblioteca.web.response.ResponseSelect2Data;

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
		if (id <= 0) {
			throw new IdNaoPodeSerZeroNuloException("Identificador do Usuário inválido!");
		}
		
		departamentoRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Departamento findById(Long id) {
		if (id <= 0) {
			throw new IdNaoPodeSerZeroNuloException("Identificador do Usuário inválido!");
		}
		
		return departamentoRepository.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<Departamento> findAll(){
		return departamentoRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@Transactional(readOnly = true)
	public List<ResponseSelect2Data> buscaSemParametro() {
		return findAll()
				.stream()
				.limit(15)
				.map(d -> departamentoToResponseSelect2Data(d))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ResponseSelect2Data> buscaPorParametroDepartamento(String query) {
		return findAll()
				.stream()
				.limit(15)
				.filter(d -> d.getName().toLowerCase().contains(query.toLowerCase()))
				.map(d -> departamentoToResponseSelect2Data(d))
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Departamento findDepartamentoById(Long id) {
		return departamentoRepository.findDepartamentoById(id)
				.orElseThrow(() -> new EntidadeNaoCadastradaException("Departamento não está cadastrado"));
		
	}
	
	private ResponseSelect2Data departamentoToResponseSelect2Data(Departamento d) {
		return new ResponseSelect2Data(d.getId().toString(), d.getName());
	}
	
}
