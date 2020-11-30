package br.com.tsmweb.biblioteca.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.service.EditoraService;

@Controller
public class EditoraController {

	@Autowired
	private EditoraService editoraService;
	
	@GetMapping(value = "/editora/cadastro")
	public String cadastroEditora(Editora editora, Model model) {
		model.addAttribute("editora", editora);
		return "/editora/cadastro";
	}
	
	@PostMapping(value = "/editora/incluir")
	public String inserirEditora(Editora editora) {
		System.out.println(editora.toString());
		return "/editora/cadastro";
	}
}
