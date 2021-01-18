package br.com.tsmweb.biblioteca.models.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tsmweb.biblioteca.models.model.Usuario;

@Service
public class UsuarioFotosStorageService extends LocalFotosStorageService<Usuario> {

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	protected Optional<String> buscarNomeArquivoPorId(Long id) {
		Usuario usuario = null;
		
		if ( id != 0L) {
			usuario = usuarioService.findUserById(id);
		}
		
		return Optional.of(usuario.getPhoto());
	}
	
	@Override
	protected String getNomeArquivoDefault() {
		return "default-avatar.png";
	}
	
}
