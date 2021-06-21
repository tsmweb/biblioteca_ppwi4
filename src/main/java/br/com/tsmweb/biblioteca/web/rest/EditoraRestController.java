package br.com.tsmweb.biblioteca.web.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.service.EditoraService;
import br.com.tsmweb.biblioteca.web.response.ResponseSelect2Data;

@RestController
@RequestMapping(value = "/rest/editora")
public class EditoraRestController {
	
	@Autowired
	private EditoraService editoraService;
	
	
	@ResponseBody
	@GetMapping(value = "/listar")
	public Page<Editora> findAll(
			@RequestParam(value = "currentPage", required = false) Optional<Integer> currentPage,
			@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
			@RequestParam(value = "dir", required = false) Optional<String> dir,
			@RequestParam(value = "props", required = false) Optional<String> props) {
		
		Pageable pageable = gerarPagina(currentPage.orElse(0), pageSize.orElse(5), dir, props);
		Page<Editora> listaEditora = editoraService.findAll(pageable);
		
		return listaEditora;
	}

	@ResponseBody
	@GetMapping(value = "/listar/{name}")
	public Page<Editora> findUserByName(
			@PathVariable("name") String name,
			@RequestParam(value = "currentPage", required = false) Optional<Integer> currentPage,
			@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
			@RequestParam(value = "dir", required = false) Optional<String> dir,
			@RequestParam(value = "props", required = false) Optional<String> props) {
		
		Pageable pageable = gerarPagina(currentPage.orElse(0), pageSize.orElse(5), dir, props);
		Page<Editora> listaEditora = editoraService.findPublisherByName(name, pageable);
		
		return listaEditora;
	}
	
	@ResponseBody
	@GetMapping(value = "/carregar")
	public List<Editora> loadAll() {
		return editoraService.findAll();
	}
	
	@ResponseBody
	@GetMapping(value = "/buscar")
	public List<ResponseSelect2Data> selectEditora(@RequestParam(value = "q", required = false) String query) {
		return StringUtils.isEmpty(query) 
				? editoraService.buscaSemParametro() 
				: editoraService.buscaPorParametro(query);
	}
	
	@ResponseBody
	@GetMapping(value = "/buscar/{id}")
	public Editora findEditoraById(@PathVariable("id") Long id) {
		return editoraService.findEditoraById(id);
	}

	@ResponseBody
	@PostMapping(value = "/inserir", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void saveEditora(@RequestBody @Valid Editora editora) {
		editoraService.save(editora);
	}
	
	@ResponseBody
	@PostMapping(value = "/alterar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateEditora(@RequestBody @Valid Editora editora) {
		editoraService.save(editora);
	}
	
	private Pageable gerarPagina(Integer currentPage, Integer pageSize, Optional<String> dir, Optional<String> props) {
		return PageRequest.of(currentPage, pageSize, getDirection(dir), getAtributte(props));
	}

	private String getAtributte(Optional<String> atributo) {
		return atributo.orElse("id");
	}
	
	private Direction getDirection(Optional<String> dir) {
		String direcao = dir.orElse("asc");
		return direcao.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
	}
	
}
