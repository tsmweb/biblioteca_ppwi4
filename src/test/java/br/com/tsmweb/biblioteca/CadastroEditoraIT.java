package br.com.tsmweb.biblioteca;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.service.EditoraService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroEditoraIT {

	@Autowired
	private EditoraService editoraService;
	
	@Test
	public void testarCadastroDeEditora() {
		Editora editora = new Editora();
		editora.setName("Novatec");
		
		editora = editoraService.save(editora);
		
		assertTrue(editora.getId() > 0);
		
		if (editora.getId() > 0) {
			System.out.println(editora.toString());
		}
	}
	
}
