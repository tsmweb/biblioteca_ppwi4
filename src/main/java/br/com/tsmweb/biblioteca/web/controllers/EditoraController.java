package br.com.tsmweb.biblioteca.web.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.tsmweb.biblioteca.models.config.ConfigProjeto;
import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.repository.filtros.EditoraFiltro;
import br.com.tsmweb.biblioteca.models.service.EditoraService;

@Controller
@RequestMapping(value = "/editora")
public class EditoraController {

	@Autowired
	private EditoraService editoraService;
	
	@GetMapping(value = "/listar")
	public ModelAndView listarEditora(EditoraFiltro editoraFiltro) {
		ModelAndView mv = new ModelAndView("/editora/listar");
		mv.addObject("editoraFiltro", editoraFiltro);
		mv.addObject("pageSizes", ConfigProjeto.PAGE_SIZES);
		mv.addObject("size", ConfigProjeto.SIZE);
		mv.addObject("editoras", editoraService.findAll());
		
		return mv;		
	}
	
	@GetMapping(value = "/cadastrar")
	public String cadastroEditora(Editora editora) {
		return "/editora/cadastrar";
	}
	
	@PostMapping(value = "/incluir")
	public String inserirEditora(@Valid Editora editora, BindingResult result) {
		if (result.hasErrors()) {
			return "/editora/cadastrar";
		}
		
		editoraService.save(editora);
		
		return "redirect:/editora/listar";
	}
	
	@GetMapping(value = "/alterar/{id}")
	public String buscarEditoraParaAlteracao(@PathVariable Long id, Model model) {
		Editora editora = editoraService.findById(id);
		model.addAttribute("editora", editora);
		
		return "/editora/cadastrar";
	}
	
	@PostMapping(value = "/alterar")
	public String alterarEditora(@Valid Editora editora, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("editora", editora);
			return "/editora/cadastrar";	
		}
		
		editora = editoraService.update(editora);
		
		return "redirect:/editora/listar";
	}

	@GetMapping(value = "/excluir/{id}")
	public String excluirEditora(@PathVariable Long id) {		
		editoraService.deleteById(id);
		
		return "redirect:/editora/listar";
	}
	
	@GetMapping(value = "/consultar/{id}")
	public ModelAndView consultarEditora(@PathVariable Long id) {		
		ModelAndView mv = new ModelAndView("/editora/consultar");
		mv.addObject("editora", editoraService.findById(id));
		
		return mv;	
	}
}
