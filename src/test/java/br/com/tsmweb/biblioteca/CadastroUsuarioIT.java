package br.com.tsmweb.biblioteca;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.service.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroUsuarioIT {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	public void testarCadastroDeUsuario() {
		Usuario usuario = new Usuario();
		usuario.setUsername("José");
		usuario.setActive(true);
		usuario.setPassword("123456");
		usuario.setConfirmPassword("123456");
		usuario.setEmail("jose@servidor.com.br");

		// assertTrue(!usuario.getConfirmPassword().equals(""));
		
		usuario = usuarioService.save(usuario);
		
		assertTrue(usuario.getId() > 0);

		if (usuario.getId() > 0) {
			System.out.print(usuario.toString());
		}
	}

}
