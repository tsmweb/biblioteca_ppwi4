package br.com.tsmweb.biblioteca.web.controllers;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.tsmweb.biblioteca.models.config.ConfigProjeto;
import br.com.tsmweb.biblioteca.models.config.PageRequestConfig;
import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.model.Livro;
import br.com.tsmweb.biblioteca.models.reports.LivroReportPdf;
import br.com.tsmweb.biblioteca.models.repository.filtros.LivroFiltro;
import br.com.tsmweb.biblioteca.models.repository.pagination.Pagina;
import br.com.tsmweb.biblioteca.models.service.EditoraService;
import br.com.tsmweb.biblioteca.models.service.LivroService;
import br.com.tsmweb.biblioteca.web.response.ResponseSelect2Data;

@Controller
@RequestMapping(value = "/livro")
public class LivroController {

	@Autowired
	private LivroService livroService;
	
	@Autowired
	private EditoraService editoraService;
	
	@Autowired
	private LivroReportPdf livroReportPdf;
	
	@GetMapping(value = "/listar")
	public ModelAndView listarLivro(LivroFiltro livroFiltro,
			HttpServletRequest request,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@RequestParam(value = "page", required = false) Optional<Integer> page,
			@RequestParam(value = "sort", required = false) Optional<String> sort,
			@RequestParam(value = "dir", required = false) Optional<String> dir) {
		
		Pageable pageable = PageRequestConfig.requestPage(size, page, sort, dir);

		Page<Livro> listaLivro = livroService.listLivroByPage(livroFiltro, pageable);
		
		Pagina<Livro> pagina = new Pagina<>(listaLivro, size.orElse(ConfigProjeto.SIZE), request);
		
		ModelAndView mv = new ModelAndView("/livro/listar");
		mv.addObject("livroFiltro", livroFiltro);
		mv.addObject("pageSizes", ConfigProjeto.PAGE_SIZES);
		mv.addObject("size", size.orElse(ConfigProjeto.SIZE));
		mv.addObject("dir", dir.orElse("asc"));
		mv.addObject("sort", sort.orElse("id"));
		mv.addObject("pagina", pagina);
		
		return mv;		
	}
	
	@GetMapping(value = "/cadastrar")
	public String cadastroLivro(Livro livro) {
		return "/livro/cadastrar";
	}
	
	@PostMapping(value = "/incluir")
	public String inserirLivro(@Valid Livro livro, BindingResult result, RedirectAttributes flash) {
		if (result.hasErrors()) {
			return "/livro/cadastrar";
		}
		
		livroService.save(livro);
		flash.addFlashAttribute("success", "Livro cadastrado com sucesso!");
		
		return "redirect:/livro/listar";
	}
	
	@GetMapping(value = "/alterar/{id}")
	public String buscarLivroParaAlteracao(@PathVariable Long id, Model model) {
		Livro livro = livroService.findById(id);
		model.addAttribute("livro", livro);
		
		return "/livro/cadastrar";
	}
	
	@PostMapping(value = "/alterar")
	public String alterarLivro(@Valid Livro livro, BindingResult result, Model model, RedirectAttributes flash) {
		if (result.hasErrors()) {
			model.addAttribute("livro", livro);
			return "/livro/cadastrar";	
		}
		
		livro = livroService.update(livro);
		flash.addFlashAttribute("success", "Livro alterado com sucesso!");
		
		return "redirect:/livro/listar";
	}

	@GetMapping(value = "/excluir/{id}")
	public String excluirLivro(@PathVariable Long id, RedirectAttributes flash) {		
		livroService.deleteById(id);
		flash.addFlashAttribute("success", "Livro excluído com sucesso!");
		
		return "redirect:/livro/listar";
	}
	
	@GetMapping(value = "/consultar/{id}")
	public ModelAndView consultarLivro(@PathVariable Long id) {		
		ModelAndView mv = new ModelAndView("/livro/consultar");
		mv.addObject("livro", livroService.findById(id));
		
		return mv;	
	}
	
	@GetMapping(value = "/relatorio")
	public ResponseEntity<InputStreamResource> imprimirRelatorioPdf(HttpServletRequest request) {
		List<Livro> listaLivro = livroService.findAll();
		ByteArrayInputStream pdf = livroReportPdf.generateReport(request, listaLivro);
		
		return ResponseEntity.ok()
				.header("Content-Disposition", "inline; filename=report.pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(pdf));
	}
	
	@ResponseBody
	@GetMapping(value = "/buscaEditora")
	public List<ResponseSelect2Data> selectEditora(@RequestParam(value = "q", required = false) String query) {
		return StringUtils.isEmpty(query) 
				? editoraService.buscaSemParametro() 
				: editoraService.buscaPorParametro(query);
	}
	
	@ModelAttribute("editoras")
	public List<Editora> listarEditoras() {
		return editoraService.findAll();
	}
	
}
