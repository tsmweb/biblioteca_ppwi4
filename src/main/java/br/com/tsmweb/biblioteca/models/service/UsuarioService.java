package br.com.tsmweb.biblioteca.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.repository.UsuarioRepository;
import br.com.tsmweb.biblioteca.models.repository.filtros.UsuarioFiltro;
import br.com.tsmweb.biblioteca.models.service.exception.ConfirmPasswordNaoInformadoException;
import br.com.tsmweb.biblioteca.models.service.exception.EmailCadastradoException;
import br.com.tsmweb.biblioteca.models.service.exception.IdNaoPodeSerZeroNuloException;
import br.com.tsmweb.biblioteca.models.service.exception.EntidadeNaoCadastradaException;

@Service
@Transactional
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DepartamentoService departamentoService;
	
	@Autowired
	private RoleService roleService;
	
	public Usuario save(Usuario usuario) {
		Optional<Usuario> usuarioCadastrado = findUsuarioByEmail(usuario.getEmail());
		
		if (usuarioCadastrado.isPresent() && !usuarioCadastrado.get().equals(usuario)) {
			throw new EmailCadastradoException("O e-mail já está cadastrado");
		}
		
		if (usuario.getConfirmPassword().equals("")) {
			throw new ConfirmPasswordNaoInformadoException("O campo Confirme Senha deve ser preenchido!");
		}
		
		if (usuario.getPhoto().isEmpty()) {
			usuario.setPhoto("default-avatar.png");
		}
		
		departamentoService.findDepartamentoById(usuario.getDepartamento().getId());
		
		usuario.getRoles().forEach(role -> roleService.findRoleById(role.getId()));
		
		return usuarioRepository.save(usuario);
	}
	
	public Usuario update(Usuario usuario) {
		return save(usuario);
	}
	
	public void deleteById(Long id) {
		if (id <= 0) {
			throw new IdNaoPodeSerZeroNuloException("Identificador do Usuário inválido!");
		}
		
		usuarioRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		if (id <= 0) {
			throw new IdNaoPodeSerZeroNuloException("Identificador do Usuário inválido!");
		}
		
		return usuarioRepository.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public Page<Usuario> findUserByName(String name, Pageable pageable) {
		return usuarioRepository.findUserByName(name, pageable);
	}
	
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Optional<Usuario> findUsuarioByEmail(String email){
		return usuarioRepository.findUsuarioByEmail(email);
	}

	@Transactional(readOnly = true)
	public Page<Usuario> listUsuarioByPage(UsuarioFiltro usuarioFiltro, Pageable pageable) {
		return usuarioRepository.listUsuarioByPage(usuarioFiltro, pageable);
	}
	
	@Transactional(readOnly = true)
	public Usuario findUserById(Long id) {
		return usuarioRepository.findUsuarioById(id)
				.orElseThrow(() -> new EntidadeNaoCadastradaException("Usuário não cadastrado!"));
	}
	
}
