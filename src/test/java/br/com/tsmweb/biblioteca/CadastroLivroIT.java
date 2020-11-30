package br.com.tsmweb.biblioteca;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.model.Livro;
import br.com.tsmweb.biblioteca.models.service.LivroService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroLivroIT {

	@Autowired
	private LivroService livroService;
	
	@Test
	public void testarCadastroDeLivro() {
		Editora editora = new Editora(1L, "Novatec");
		Livro livro = new Livro();
		livro.setTitle("A Linguagem de Programação Go");
		livro.setPublisher(editora);
		livro.setAuthor("Alan A. A. Donovan");
		livro.setNumberPages(478);
		livro.setYearPublication(2017);
		livro.setTotalAmount(5);
		
		livro = livroService.save(livro);
		
		assertTrue(livro.getId() > 0);
		
		if (livro.getId() > 0) {
			System.out.println(livro.toString());
		}
		
	}
	
}
