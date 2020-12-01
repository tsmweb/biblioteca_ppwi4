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

import br.com.tsmweb.biblioteca.models.model.Role;
import br.com.tsmweb.biblioteca.models.service.RoleService;

@Controller
@RequestMapping(value = "/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	@GetMapping(value = "/listar")
	public ModelAndView listarRole() {
		ModelAndView mv = new ModelAndView("/role/listar");
		mv.addObject("roles", roleService.findAll());
		
		return mv;		
	}
	
	@GetMapping(value = "/cadastro")
	public String cadastroRole(Role role) {
		return "/role/cadastrar";
	}
	
	@PostMapping(value = "/incluir")
	public String inserirRole(@Valid Role role, BindingResult result) {
		if (result.hasErrors()) {
			return "/role/cadastrar";
		}
		
		roleService.save(role);
		
		return "redirect:/role/listar";
	}
	
	@GetMapping(value = "/alterar/{id}")
	public String buscarRoleParaAlteracao(@PathVariable Long id, Model model) {
		Role role = roleService.findById(id);
		model.addAttribute("role", role);
		
		return "/role/cadastrar";
	}
	
	@PostMapping(value = "/alterar")
	public String alterarRole(@Valid Role role, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("role", role);
			return "/role/cadastro";	
		}
		
		role = roleService.update(role);
		
		return "redirect:/role/listar";
	}

	@GetMapping(value = "/excluir/{id}")
	public String excluirRole(@PathVariable Long id) {		
		roleService.deleteById(id);
		
		return "redirect:/role/listar";
	}
	
	@GetMapping(value = "/consultar/{id}")
	public ModelAndView consultarRole(@PathVariable Long id) {		
		ModelAndView mv = new ModelAndView("/role/consultar");
		mv.addObject("role", roleService.findById(id));
		
		return mv;	
	}

}
