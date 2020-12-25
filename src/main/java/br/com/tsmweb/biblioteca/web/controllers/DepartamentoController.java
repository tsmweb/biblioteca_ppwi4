package br.com.tsmweb.biblioteca.web.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.tsmweb.biblioteca.models.config.ConfigProjeto;
import br.com.tsmweb.biblioteca.models.model.Departamento;
import br.com.tsmweb.biblioteca.models.repository.filtros.DepartamentoFiltro;
import br.com.tsmweb.biblioteca.models.service.DepartamentoService;
import br.com.tsmweb.biblioteca.models.service.exception.NegocioException;

@Controller
@RequestMapping(value="/departamento")
public class DepartamentoController {
	
	@Autowired
	private DepartamentoService departamentoService;

	@GetMapping(value="/listar")
	public ModelAndView listarDepartamento(DepartamentoFiltro departamentoFiltro) {
		ModelAndView mv = new ModelAndView("/departamento/listar");
		mv.addObject("departamentoFiltro", departamentoFiltro);
		mv.addObject("pageSizes", ConfigProjeto.PAGE_SIZES);
		mv.addObject("size", ConfigProjeto.SIZE);
		mv.addObject("departamentos", departamentoService.findAll());
		
		return mv;
	}    
	
	@GetMapping(value="/cadastrar")
	public String cadastroDepartamento(Departamento departamento) {
		return "/departamento/cadastrar";
	}
	
	@PostMapping(value="/incluir")
	public String inserirDepartamento(@Valid Departamento departamento, BindingResult result, RedirectAttributes flash) {
		if (result.hasErrors()) {
			return "/departamento/cadastrar";
		}
		
		departamentoService.save(departamento);
		flash.addFlashAttribute("success", "Departamento cadastrado com sucesso!");
		
		return "redirect:/departamento/listar";
	}
	
	@GetMapping(value="/alterar/{id}")
	public String buscarDepartamentoParaAlteracao(@PathVariable Long id,  Model model) {
		Departamento departamento = departamentoService.findById(id);
		model.addAttribute("departamento", departamento);
		
		return "/departamento/cadastrar";
	}
	
	@PostMapping(value="/alterar")
	public String salvarEdicaoDepartamento(@Valid Departamento departamento, BindingResult result, Model model, RedirectAttributes flash) {
		if (result.hasErrors() ) {
			model.addAttribute("departamento", departamento);
			return "/departamento/cadastrar";
		}
		
		departamento = departamentoService.update(departamento);
		flash.addFlashAttribute("success", "Departamento alterado com sucesso!");
		
		return "redirect:/departamento/listar";
	}
		
	@GetMapping(value="/excluir/{id}")
	public String buscarDepartamentoParaExclusao(@PathVariable Long id, RedirectAttributes flash) {
		departamentoService.deleteById(id);
		flash.addFlashAttribute("success", "Departamento excluído com sucesso!");
		
		return "redirect:/departamento/listar";
	}
	
	@GetMapping(value="/consultar/{id}")
	public ModelAndView buscarDepartamentoParaConsulta(@PathVariable Long id, Model model) {
		ModelAndView mv = new ModelAndView("/departamento/consultar");
		mv.addObject("departamento", departamentoService.findById(id));
		
		return mv;
	}
	
	@ExceptionHandler(NegocioException.class)
	public String handlerException(NegocioException ex, RedirectAttributes flash) {
		flash.addFlashAttribute("error", ex.getMessage());
		return "redirect:/departamento/listar";
	}
	
}
