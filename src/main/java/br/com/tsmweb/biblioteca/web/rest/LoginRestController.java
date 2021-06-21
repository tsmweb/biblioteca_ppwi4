package br.com.tsmweb.biblioteca.web.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tsmweb.biblioteca.models.model.Login;
import br.com.tsmweb.biblioteca.models.model.dto.UsuarioLogadoDTO;
import br.com.tsmweb.biblioteca.models.service.UsuarioService;
import br.com.tsmweb.biblioteca.models.service.security.jwt.JwtTokenProvider;

@RestController
@RequestMapping(value = "/rest")
public class LoginRestController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping(value = "/login", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UsuarioLogadoDTO login(@RequestBody @Valid Login login) {
		var usuario = usuarioService.findUsuarioByEmail(login.getEmail());
		
		if (!usuario.isPresent()) {
			throw new UsernameNotFoundException("Usuário não está cadastrado");
		}
		
		if (login.getEmail().equals(usuario.get().getEmail()) && !usuario.get().isActive()) {
			throw new LockedException("Usuário está bloqueado no sistema");
		}
		
		if (login.getEmail().equals(usuario.get().getEmail()) && 
			BCrypt.checkpw(login.getPassword(), usuario.get().getPassword())) {
			new UsernamePasswordAuthenticationToken(usuario.get(), usuario.get().getPassword(), usuario.get().getAuthorities());
		} else {
			throw new BadCredentialsException("A senha informada é inválida");
		}
		
		var token = tokenProvider.createToken(usuario.get().getEmail(), usuario.get().getRoles());
		
		var usuarioLogado = new UsuarioLogadoDTO();
		usuarioLogado.setUsername(usuario.get().getUsername());
		usuarioLogado.setEmail(usuario.get().getEmail());
		usuarioLogado.setToken(token);
		
		return usuarioLogado;
	}
	
}
