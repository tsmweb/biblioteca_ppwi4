package br.com.tsmweb.biblioteca.web.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import br.com.tsmweb.biblioteca.models.model.Departamento;
import br.com.tsmweb.biblioteca.models.model.Role;
import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.repository.filtros.UsuarioFiltro;
import br.com.tsmweb.biblioteca.models.repository.pagination.Pagina;
import br.com.tsmweb.biblioteca.models.service.DepartamentoService;
import br.com.tsmweb.biblioteca.models.service.RoleService;
import br.com.tsmweb.biblioteca.models.service.UsuarioService;
import br.com.tsmweb.biblioteca.models.service.components.PrintJasperReport;
import br.com.tsmweb.biblioteca.models.service.exception.ConfirmPasswordNaoInformadoException;
import br.com.tsmweb.biblioteca.models.service.exception.EmailCadastradoException;
import br.com.tsmweb.biblioteca.models.service.exception.NegocioException;
import br.com.tsmweb.biblioteca.models.service.reports.JasperReportsService;
import br.com.tsmweb.biblioteca.models.service.reports.UsuarioReportPdf;
import br.com.tsmweb.biblioteca.web.response.ResponseSelect2Data;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DepartamentoService departamentoService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UsuarioReportPdf usuarioReportPdf;
	
	@Autowired
	private JasperReportsService jasperReportsService;
	
	@Autowired
	private PrintJasperReport printJasperReport;
	
	private Map<String, Object> params = new HashMap<>();

	@GetMapping(value = "/listar")
	public ModelAndView listarUsuario(UsuarioFiltro usuarioFiltro, 
			HttpServletRequest request,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@RequestParam(value = "page", required = false) Optional<Integer> page,
			@RequestParam(value = "sort", required = false) Optional<String> sort,
			@RequestParam(value = "dir", required = false) Optional<String> dir) {
		
		Pageable pageable = PageRequestConfig.requestPage(size, page, sort, dir);
		
		Page<Usuario> listaUsuario = usuarioService.listUsuarioByPage(usuarioFiltro, pageable);
		
		Pagina<Usuario> pagina = new Pagina<>(listaUsuario, size.orElse(ConfigProjeto.SIZE), request);
		
		ModelAndView mv = new ModelAndView("/usuario/listar");
		mv.addObject("usuarioFiltro", usuarioFiltro);
		mv.addObject("pageSizes", ConfigProjeto.PAGE_SIZES);
		mv.addObject("size", size.orElse(ConfigProjeto.SIZE));
		mv.addObject("dir", dir.orElse("asc"));
		mv.addObject("sort", sort.orElse("id"));
		mv.addObject("pagina", pagina);
		
		return mv;		
	}

	@GetMapping(value = "/cadastrar")
	public String cadastroUsuario(Usuario usuario) {
		return "/usuario/cadastrar";
	}
	
	@PostMapping(value = "/incluir")
	public String inserirUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes flash) {
		if (result.hasErrors()) {
			return "/usuario/cadastrar";
		}
		
		try {
			usuarioService.save(usuario);
			flash.addFlashAttribute("success", "Usuário cadastrado com sucesso!");
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
	public String buscarUsuarioParaAlteracao(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Usuario usuario = usuarioService.findUserById(id);
//		model.addAttribute("departamentos", usuario.getDepartamento());
		model.addAttribute("usuario", usuario);
		
		return "/usuario/cadastrar";
	}
	
	@PostMapping(value = "/alterar")
	public String alterarUsuario(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes flash) {
		if (result.hasErrors()) {
			model.addAttribute("usuario", usuario);
			return "/usuario/cadastrar";	
		}
		
		try {
			usuario = usuarioService.update(usuario);
			flash.addFlashAttribute("success", "Usuário alterado com sucesso!");
		} catch(EmailCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return "/usuario/cadastrar";
		} catch(ConfirmPasswordNaoInformadoException e) {
			result.rejectValue("confirmPassword", e.getMessage(), e.getMessage());
			return "/usuario/cadastrar";
		}
		
		return "redirect:/usuario/listar";
	}

	@GetMapping(value = "/excluir/{id}")
	public String excluirUsuario(@PathVariable Long id, RedirectAttributes flash) {	
		usuarioService.deleteById(id);
		flash.addFlashAttribute("success", "Usuário excluído com sucesso!");
		
		return "redirect:/usuario/listar";
	}
	
	@GetMapping(value = "/consultar/{id}")
	public ModelAndView consultarUsuario(@PathVariable Long id) {		
		ModelAndView mv = new ModelAndView("/usuario/consultar");
		mv.addObject("usuario", usuarioService.findUserById(id));
		
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
	
	@ResponseBody
	@GetMapping(value = "/buscaDepartamento")
	public List<ResponseSelect2Data> selectDepartamento(@RequestParam(value = "q", required = false) String query) {
		return StringUtils.isEmpty(query) 
				? departamentoService.buscaSemParametro() 
				: departamentoService.buscaPorParametroDepartamento(query);
	}

	@ModelAttribute("departamentos")
	public List<Departamento> listarDepartamento() {
		return departamentoService.findAll();
	}
	
	@ExceptionHandler(NegocioException.class)
	public String handlerException(NegocioException ex, RedirectAttributes flash) {
		flash.addFlashAttribute("error", ex.getMessage());
		return "redirect:/usuario/listar";
	}
	
	@ModelAttribute("roles")
	public List<Role> listaRoles() {
		return roleService.findAll();
	}
	
	@GetMapping(value = "/pdflista")
	public ResponseEntity<byte[]> imprimeRelatorioPdfFromLista() {
		List<Usuario> listaUsuario = usuarioService.findAll();
		
		params.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
		
		printJasperReport.setFile("rel_usuarios");
		printJasperReport.setParams(params);
		printJasperReport.setCollection(listaUsuario);
		
		byte[] relatorio = jasperReportsService.generateListReport(printJasperReport);
		
		return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
					.body(relatorio);
	}
	
	@GetMapping(value = "/pdfsql")
	public ResponseEntity<byte[]> imprimeRelatorioPdfFromSql() {
		params.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
		params.put("CODIGO_INICIAL", 1L);
		params.put("CODIGO_FINAL", 100L);
		
		printJasperReport.setFile("rel_consulta_usuario");
		printJasperReport.setParams(params);
		
		byte[] relatorio = jasperReportsService.generateNativeSqlReport(printJasperReport);
		
		return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
					.body(relatorio);
	}
	
	@GetMapping(value = "/pdfdownload")
	public void downloadRelatorioPdf(HttpServletResponse response) {
		params.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
		params.put("CODIGO_INICIAL", 1L);
		params.put("CODIGO_FINAL", 100L);
		
		printJasperReport.setFile("rel_consulta_usuario");
		printJasperReport.setParams(params);
		
		response.setContentType("application/x-download");
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"usuario.pdf\""));
		
		try {
			JasperPrint jasperPrint = jasperReportsService.downloadReportPdf(printJasperReport);
			OutputStream out = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);
		} catch(IOException e) {
			e.printStackTrace();
		} catch(JRException e) {
			e.printStackTrace();
		}
	}
	
}
