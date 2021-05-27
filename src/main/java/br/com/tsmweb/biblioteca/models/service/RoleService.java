package br.com.tsmweb.biblioteca.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tsmweb.biblioteca.models.model.Role;
import br.com.tsmweb.biblioteca.models.repository.RoleRepository;
import br.com.tsmweb.biblioteca.models.service.exception.EntidadeNaoCadastradaException;
import br.com.tsmweb.biblioteca.models.service.exception.IdNaoPodeSerZeroNuloException;

@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	public Role save(Role role) {		
		return roleRepository.save(role);
	}
	
	public Role update(Role role) {
		return save(role);
	}
	
	public void deleteById(Long id) {
		if (id <= 0) {
			throw new IdNaoPodeSerZeroNuloException("Identificador do Usuário inválido!");
		}
		
		roleRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Role findById(Long id) {
		if (id <= 0) {
			throw new IdNaoPodeSerZeroNuloException("Identificador do Usuário inválido!");
		}
		
		return roleRepository.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<Role> findAll() {
		return roleRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Page<Role> findAll(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Role findRoleById(Long id) {
		return roleRepository.findRoleById(id)
				.orElseThrow(() -> new EntidadeNaoCadastradaException("Role não cadastrada"));
		
	}
	
}
