package br.com.tsmweb.biblioteca.web.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.tsmweb.biblioteca.models.config.ConfigProjeto;
import br.com.tsmweb.biblioteca.models.config.PageRequestConfig;
import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.repository.filtros.EditoraFiltro;
import br.com.tsmweb.biblioteca.models.repository.pagination.Pagina;
import br.com.tsmweb.biblioteca.models.service.EditoraService;
import br.com.tsmweb.biblioteca.models.service.exception.NegocioException;

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
		
		Pageable pageable = PageRequestConfig.requestPage(size, page, sort, dir);

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
	public String inserirEditora(@Valid Editora editora, BindingResult result, RedirectAttributes flash) {
		if (result.hasErrors()) {
			return "/editora/cadastrar";
		}
		
		editoraService.save(editora);
		flash.addFlashAttribute("success", "Editora cadastrada com sucesso!");
		
		return "redirect:/editora/listar";
	}
	
	@GetMapping(value = "/alterar/{id}")
	public String buscarEditoraParaAlteracao(@PathVariable Long id, Model model) {
		Editora editora = editoraService.findById(id);
		model.addAttribute("editora", editora);
		
		return "/editora/cadastrar";
	}
	
	@PostMapping(value = "/alterar")
	public String alterarEditora(@Valid Editora editora, BindingResult result, Model model, RedirectAttributes flash) {
		if (result.hasErrors()) {
			model.addAttribute("editora", editora);
			return "/editora/cadastrar";	
		}
		
		editora = editoraService.update(editora);
		flash.addFlashAttribute("success", "Editora alterada com sucesso!");
		
		return "redirect:/editora/listar";
	}

	@GetMapping(value = "/excluir/{id}")
	public String excluirEditora(@PathVariable Long id, RedirectAttributes flash) {		
		editoraService.deleteById(id);
		flash.addFlashAttribute("success", "Editora excluída com sucesso!");
		
		return "redirect:/editora/listar";
	}
	
	@GetMapping(value = "/consultar/{id}")
	public ModelAndView consultarEditora(@PathVariable Long id) {		
		ModelAndView mv = new ModelAndView("/editora/consultar");
		mv.addObject("editora", editoraService.findById(id));
		
		return mv;	
	}
	
	@ExceptionHandler(NegocioException.class)
	public String handlerException(NegocioException ex, RedirectAttributes flash) {
		flash.addFlashAttribute("error", ex.getMessage());
		return "redirect:/editora/listar";
	}
	
}
