package br.com.tsmweb.biblioteca.web.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tsmweb.biblioteca.models.model.Livro;
import br.com.tsmweb.biblioteca.models.model.dto.LivroDTO;
import br.com.tsmweb.biblioteca.models.service.LivroService;
import br.com.tsmweb.biblioteca.models.service.assembler.LivroDTOAssembler;

@RestController
@RequestMapping(value = "/rest/livro")
public class LivroRestController {
	
	@Autowired
	private LivroService livroService;
	@Autowired
	private LivroDTOAssembler livroAssembler;
	
	
	@ResponseBody
	@GetMapping(value = "/listar")
	public Page<LivroDTO> findAll(
			@RequestParam(value = "currentPage", required = false) Optional<Integer> currentPage,
			@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
			@RequestParam(value = "dir", required = false) Optional<String> dir,
			@RequestParam(value = "props", required = false) Optional<String> props) {
		
		Pageable pageable = gerarPagina(currentPage.orElse(0), pageSize.orElse(5), dir, props);
		Page<Livro> listaLivro = livroService.findAll(pageable);
		List<LivroDTO> livrosDTO = livroAssembler.toCollectionDTO(listaLivro.getContent());
		Page<LivroDTO> paginaLivroDTO = new PageImpl<>(livrosDTO, pageable, listaLivro.getTotalElements());
		
		return paginaLivroDTO;
	}

	@ResponseBody
	@GetMapping(value = "/listar/{title}")
	public Page<LivroDTO> findLivroByName(
			@PathVariable("title") String title,
			@RequestParam(value = "currentPage", required = false) Optional<Integer> currentPage,
			@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
			@RequestParam(value = "dir", required = false) Optional<String> dir,
			@RequestParam(value = "props", required = false) Optional<String> props) {
		
		Pageable pageable = gerarPagina(currentPage.orElse(0), pageSize.orElse(5), dir, props);
		Page<Livro> listaLivro = livroService.findBookByTitle(title, pageable);
		List<LivroDTO> livrosDTO = livroAssembler.toCollectionDTO(listaLivro.getContent());
		Page<LivroDTO> paginaLivroDTO = new PageImpl<>(livrosDTO, pageable, listaLivro.getTotalElements());
		
		return paginaLivroDTO;
	}
	
	@ResponseBody
	@GetMapping(value = "/buscar/{id}")
	public Livro findLivroById(@PathVariable("id") Long id) {
		return livroService.findLivroById(id);
	}

	@ResponseBody
	@PostMapping(value = "/inserir", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void saveLivro(@RequestBody @Valid Livro livro) {
		livroService.save(livro);
	}
	
	@ResponseBody
	@PostMapping(value = "/alterar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateLivro(@RequestBody @Valid Livro livro) {
		livroService.save(livro);
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
