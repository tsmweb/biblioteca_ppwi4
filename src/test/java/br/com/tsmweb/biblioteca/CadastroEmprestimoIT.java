package br.com.tsmweb.biblioteca;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.tsmweb.biblioteca.models.model.Emprestimo;
import br.com.tsmweb.biblioteca.models.model.Livro;
import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.service.EmprestimoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroEmprestimoIT {

	@Autowired
	private EmprestimoService emprestimoService;
	
	@Test
	public void testarCadastroDeEmprestimo() {
		Livro livro = new Livro();
		livro.setId(1L);
		
		Usuario usuario = new Usuario();
		usuario.setId(2L);
		
		Date dataAtual = new Date();
		Calendar dataRetorno = Calendar.getInstance();
		dataRetorno.setTime(dataAtual);
		dataRetorno.add(Calendar.DAY_OF_MONTH, 7);
		
		Emprestimo emprestimo = new Emprestimo();
		emprestimo.setId(1L);
		emprestimo.setUser(usuario);
		emprestimo.setBook(livro);
		emprestimo.setLoanDate(dataAtual);
		emprestimo.setReturnDate(dataRetorno.getTime());
		
		emprestimo = emprestimoService.save(emprestimo);
		
		assertTrue(emprestimo.getId() > 0);
		
		if (emprestimo.getId() > 0) {
			System.out.println(emprestimo.toString());
		}
	}
	
}
