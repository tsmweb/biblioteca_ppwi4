package br.com.tsmweb.biblioteca.web.controllers;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.tsmweb.biblioteca.models.config.ConfigProjeto;
import br.com.tsmweb.biblioteca.models.model.Departamento;
import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.reports.UsuarioReportPdf;
import br.com.tsmweb.biblioteca.models.repository.filtros.UsuarioFiltro;
import br.com.tsmweb.biblioteca.models.repository.pagination.Pagina;
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
	
	@Autowired
	private UsuarioReportPdf usuarioReportPdf;

	@GetMapping(value = "/listar")
	public ModelAndView listarUsuario(UsuarioFiltro usuarioFiltro, 
			HttpServletRequest request,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@RequestParam(value = "page", required = false) Optional<Integer> page) {
		
		Pageable pageable = PageRequest.of(page.orElse(ConfigProjeto.INITIAL_PAGE), 
										size.orElse(ConfigProjeto.SIZE),
										Sort.Direction.ASC, "id");
		
		Page<Usuario> listaUsuario = usuarioService.listUsuarioByPage(usuarioFiltro, pageable);
		
		Pagina<Usuario> pagina = new Pagina<>(listaUsuario, size.orElse(ConfigProjeto.SIZE), request);
		
		ModelAndView mv = new ModelAndView("/usuario/listar");
		mv.addObject("usuarioFiltro", usuarioFiltro);
		mv.addObject("pageSizes", ConfigProjeto.PAGE_SIZES);
		mv.addObject("size", size.orElse(ConfigProjeto.SIZE));
		mv.addObject("pagina", pagina);
		
		return mv;		
	}
	
	@GetMapping(value = "/cadastrar")
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
			return "/usuario/cadastrar";
		} catch(ConfirmPasswordNaoInformadoException e) {
			result.rejectValue("confirmPassword", e.getMessage(), e.getMessage());
			return "/usuario/cadastrar";
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
			return "/usuario/cadastrar";	
		}
		
		try {
			usuario = usuarioService.update(usuario);
		} catch(ConfirmPasswordNaoInformadoException e) {
			result.rejectValue("confirmPassword", e.getMessage(), e.getMessage());
			return "/usuario/cadastrar";
		}
		
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
	
	@GetMapping(value = "/relatorio")
	public ResponseEntity<InputStreamResource> imprimirRelatorioPdf(HttpServletRequest request) {
		List<Usuario> listaUsuario = usuarioService.findAll();
		ByteArrayInputStream pdf = usuarioReportPdf.generateReport(request, listaUsuario);
		
		return ResponseEntity.ok()
				.header("Content-Disposition", "inline; filename=report.pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(pdf));
	}
	
	@ModelAttribute("departamentos")
	public List<Departamento> listarDepartamento() {
		return departamentoService.findAll();
	}
	
}
