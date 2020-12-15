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

@Service
@Transactional
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario save(Usuario usuario) {
		Optional<Usuario> usuarioCadastrado = findUsuarioByEmail(usuario.getEmail());
		
		if (usuarioCadastrado.isPresent() && !usuarioCadastrado.get().equals(usuario)) {
			throw new EmailCadastradoException("O e-mail já está cadastrado");
		}
		
		if (usuario.getConfirmPassword().equals("")) {
			throw new ConfirmPasswordNaoInformadoException("O campo Confirme Senha deve ser preenchido");
		}
		
		return usuarioRepository.save(usuario);
	}
	
	public Usuario update(Usuario usuario) {
		return save(usuario);
	}
	
	public void deleteById(Long id) {
		usuarioRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		return usuarioRepository.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
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
		return usuarioRepository.findUsuarioById(id);
	}
	
}
