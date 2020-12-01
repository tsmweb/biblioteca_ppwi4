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
import br.com.tsmweb.biblioteca.models.model.Departamento;
import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.repository.filtros.UsuarioFiltro;
import br.com.tsmweb.biblioteca.models.service.DepartamentoService;
import br.com.tsmweb.biblioteca.models.service.UsuarioService;
import br.com.tsmweb.biblioteca.models.service.exception.ConfirmPasswordNaoInformadoException;
import br.com.tsmweb.biblioteca.models.service.exception.EmailCadastradoException;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DepartamentoService departamentoService;

	@GetMapping(value = "/listar")
	public ModelAndView listarUsuario(UsuarioFiltro usuarioFiltro) {
		ModelAndView mv = new ModelAndView("/usuario/listar");
		mv.addObject("usuarioFiltro", usuarioFiltro);
		mv.addObject("pageSizes", ConfigProjeto.PAGE_SIZES);
		mv.addObject("size", ConfigProjeto.SIZE);
		mv.addObject("usuarios", usuarioService.findAll());
		
		return mv;		
	}
	
	@GetMapping(value = "/cadastro")
	public String cadastroUsuario(Usuario usuario) {
		return "/usuario/cadastrar";
	}
	
	@PostMapping(value = "/incluir")
	public String inserirUsuario(@Valid Usuario usuario, BindingResult result) {
		if (result.hasErrors()) {
			return "/usuario/cadastrar";
		}
		
		try {
			usuarioService.save(usuario);
		} catch(EmailCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return "usuario/cadastrar";
		} catch(ConfirmPasswordNaoInformadoException e) {
			result.rejectValue("confirmPassword", e.getMessage(), e.getMessage());
			return "usuario/cadastrar";
		}
		
		return "redirect:/usuario/listar";
	}
	
	@GetMapping(value = "/alterar/{id}")
	public String buscarUsuarioParaAlteracao(@PathVariable Long id, Model model) {
		Usuario usuario = usuarioService.findById(id);
		model.addAttribute("usuario", usuario);
		
		return "/usuario/cadastrar";
	}
	
	@PostMapping(value = "/alterar")
	public String alterarUsuario(@Valid Usuario usuario, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("usuario", usuario);
			return "/usuario/cadastro";	
		}
		
		usuario = usuarioService.update(usuario);
		
		return "redirect:/usuario/listar";
	}

	@GetMapping(value = "/excluir/{id}")
	public String excluirUsuario(@PathVariable Long id) {		
		usuarioService.deleteById(id);
		
		return "redirect:/usuario/listar";
	}
	
	@GetMapping(value = "/consultar/{id}")
	public ModelAndView consultarUsuario(@PathVariable Long id) {		
		ModelAndView mv = new ModelAndView("/usuario/consultar");
		mv.addObject("usuario", usuarioService.findById(id));
		
		return mv;	
	}
	
	@ModelAttribute("departamentos")
	public List<Departamento> listarDepartamento() {
		return departamentoService.findAll();
	}
	
}
