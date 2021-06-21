package br.com.tsmweb.biblioteca.models.service.security;

import org.springframework.security.core.userdetails.User;

import br.com.tsmweb.biblioteca.models.model.Usuario;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 5331681494671042333L;

	private Usuario usuario;
	
	public UsuarioSistema(Usuario usuario) {
		super(usuario.getUsername(),
				usuario.getPassword(),
				usuario.isEnabled(),
				usuario.isAccountNonExpired(),
				usuario.isAccountNonLocked(),
				usuario.isCredentialsNonExpired(),
				usuario.getAuthorities());
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}
