package br.com.tsmweb.biblioteca.models.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.tsmweb.biblioteca.models.service.UsuarioService;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		var usuario = usuarioService.findUsuarioByEmail(email);
		
		if (!usuario.isPresent()) {
			throw new UsernameNotFoundException("Usuário não cadastrado! " + email);
		}
		
		return new UsuarioSistema(usuario.get());
	}

}
