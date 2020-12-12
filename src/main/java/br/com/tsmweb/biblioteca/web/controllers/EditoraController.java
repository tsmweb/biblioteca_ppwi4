package br.com.tsmweb.biblioteca.web.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.tsmweb.biblioteca.models.config.ConfigProjeto;
import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.repository.filtros.EditoraFiltro;
import br.com.tsmweb.biblioteca.models.repository.pagination.Pagina;
import br.com.tsmweb.biblioteca.models.service.EditoraService;

@Controller
@RequestMapping(value = "/editora")
public class EditoraController {

	@Autowired
	private EditoraService editoraService;
	
	@GetMapping(value = "/listar")
	public ModelAndView listarEditora(EditoraFiltro editoraFiltro,
			HttpServletRequest request,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@RequestParam(value = "page", required = false) Optional<Integer> page,
			@RequestParam(value = "sort", required = false) Optional<String> sort,
			@RequestParam(value = "dir", required = false) Optional<String> dir) {
		
		Pageable pageable = PageRequest.of(page.orElse(ConfigProjeto.INITIAL_PAGE), 
				size.orElse(ConfigProjeto.SIZE),
				getDirection(dir), 
				getAttribute(sort));

		Page<Editora> listaEditora = editoraService.listEditoraByPage(editoraFiltro, pageable);
		
		Pagina<Editora> pagina = new Pagina<>(listaEditora, size.orElse(ConfigProjeto.SIZE), request);
		
		ModelAndView mv = new ModelAndView("/editora/listar");
		mv.addObject("editoraFiltro", editoraFiltro);
		mv.addObject("pageSizes", ConfigProjeto.PAGE_SIZES);
		mv.addObject("size", size.orElse(ConfigProjeto.SIZE));
		mv.addObject("dir", dir.orElse("asc"));
		mv.addObject("sort", sort.orElse("id"));
		mv.addObject("pagina", pagina);
		
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
	
	private String getAttribute(Optional<String> sort) {
		return sort.orElse("id");
	}

	private Direction getDirection(Optional<String> dir) {
		String direcao = dir.orElse("asc");
		return direcao.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
	}
	
}
