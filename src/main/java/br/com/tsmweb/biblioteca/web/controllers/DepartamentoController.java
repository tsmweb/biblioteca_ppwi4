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

import br.com.tsmweb.biblioteca.models.model.Departamento;
import br.com.tsmweb.biblioteca.models.service.DepartamentoService;

@Controller
@RequestMapping(value="/departamento")
public class DepartamentoController {
	
	@Autowired
	private DepartamentoService departamentoService;

	@GetMapping(value="/listar")
	public ModelAndView listarDepartamento() {
		ModelAndView mv = new ModelAndView("/departamento/listar");
		mv.addObject("departamentos", departamentoService.findAll());
		
		return mv;
	}    
	
	@GetMapping(value="/cadastro")
	public String cadastroDepartamento(Departamento departamento) {
		return "/departamento/cadastro";
	}
	
	@PostMapping(value="/incluir")
	public String inserirDepartamento(@Valid Departamento departamento, BindingResult result) {
		if (result.hasErrors()) {
			return "/departamento/cadastro";
		}
		
		departamentoService.save(departamento);
		
		return "redirect:/departamento/listar";
	}
	
	@GetMapping(value="/alterar/{id}")
	public String buscarDepartamentoParaAlteracao(@PathVariable Long id,  Model model) {
		Departamento departamento = departamentoService.findById(id);
		model.addAttribute("departamento", departamento);
		
		return "/departamento/cadastro";
	}
	
	@PostMapping(value="/alterar")
	public String salvarEdicaoDepartamento(@Valid Departamento departamento, BindingResult result, Model model) {
		if (result.hasErrors() ) {
			model.addAttribute("departamento", departamento);
			return "/departamento/cadastro";
		}
		
		departamento = departamentoService.update(departamento);
		
		return "redirect:/departamento/listar";
	}
		
	@GetMapping(value="/excluir/{id}")
	public String buscarDepartamentoParaExclusao(@PathVariable Long id) {
		departamentoService.deleteById(id);
		
		return "redirect:/departamento/listar";
	}
	
	@GetMapping(value="/consultar/{id}")
	public ModelAndView buscarDepartamentoParaConsulta(@PathVariable Long id, Model model) {
		ModelAndView mv = new ModelAndView("/departamento/consultar");
		mv.addObject("departamento", departamentoService.findById(id));
		
		return mv;
	}
	
}
