package br.com.tsmweb.biblioteca.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.tsmweb.biblioteca.models.config.ConfigProjeto;
import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.model.Livro;
import br.com.tsmweb.biblioteca.models.repository.filtros.LivroFiltro;
import br.com.tsmweb.biblioteca.models.service.EditoraService;
import br.com.tsmweb.biblioteca.models.service.LivroService;

@Controller
@RequestMapping(value = "/livro")
public class LivroController {

	@Autowired
	private LivroService livroService;
	
	@Autowired
	private EditoraService editoraService;
	
	@GetMapping(value = "/listar")
	public ModelAndView listarLivro(LivroFiltro livroFiltro) {
		ModelAndView mv = new ModelAndView("/livro/listar");
		mv.addObject("livroFiltro", livroFiltro);
		mv.addObject("pageSizes", ConfigProjeto.PAGE_SIZES);
		mv.addObject("size", ConfigProjeto.SIZE);
		mv.addObject("livros", livroService.findAll());
		
		return mv;		
	}
	
	@GetMapping(value = "/cadastrar")
	public String cadastroLivro(Livro livro) {
		return "/livro/cadastrar";
	}
	
	@PostMapping(value = "/incluir")
	public String inserirLivro(@Valid Livro livro, BindingResult result) {
		if (result.hasErrors()) {
			return "/livro/cadastrar";
		}
		
		livroService.save(livro);
		
		return "redirect:/livro/listar";
	}
	
	@GetMapping(value = "/alterar/{id}")
	public String buscarLivroParaAlteracao(@PathVariable Long id, Model model) {
		Livro livro = livroService.findById(id);
		model.addAttribute("livro", livro);
		
		return "/livro/cadastrar";
	}
	
	@PostMapping(value = "/alterar")
	public String alterarLivro(@Valid Livro livro, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("livro", livro);
			return "/livro/cadastrar";	
		}
		
		livro = livroService.update(livro);
		
		return "redirect:/livro/listar";
	}

	@GetMapping(value = "/excluir/{id}")
	public String excluirLivro(@PathVariable Long id) {		
		livroService.deleteById(id);
		
		return "redirect:/livro/listar";
	}
	
	@GetMapping(value = "/consultar/{id}")
	public ModelAndView consultarLivro(@PathVariable Long id) {		
		ModelAndView mv = new ModelAndView("/livro/consultar");
		mv.addObject("livro", livroService.findById(id));
		
		return mv;	
	}
	
	@ModelAttribute("editoras")
	public List<Editora> listarEditoras() {
		return editoraService.findAll();
	}
	
}
