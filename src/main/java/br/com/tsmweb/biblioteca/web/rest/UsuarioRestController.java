package br.com.tsmweb.biblioteca.web.rest;

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

import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.model.dto.UsuarioDTO;
import br.com.tsmweb.biblioteca.models.service.UsuarioService;
import br.com.tsmweb.biblioteca.models.service.assembler.UsuarioDTOAssembler;

@RestController
@RequestMapping(value = "/rest/usuario")
public class UsuarioRestController {
	
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioDTOAssembler usuarioAssembler;
	
	
	@ResponseBody
	@GetMapping(value = "/listar")
	public Page<UsuarioDTO> findAll(
			@RequestParam(value = "currentPage", required = false) Optional<Integer> currentPage,
			@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
			@RequestParam(value = "dir", required = false) Optional<String> dir,
			@RequestParam(value = "props", required = false) Optional<String> props) {
		
		var pageable = gerarPagina(currentPage.orElse(0), pageSize.orElse(5), dir, props);
		var listaUsuario = usuarioService.findAll(pageable);
		var usuariosDTO = usuarioAssembler.toCollectionDTO(listaUsuario.getContent());
		var paginaUsuarioDTO = new PageImpl<>(usuariosDTO, pageable, listaUsuario.getTotalElements());
		
		return paginaUsuarioDTO;
	}

	@ResponseBody
	@GetMapping(value = "/listar/{name}")
	public Page<UsuarioDTO> findUserByName(
			@PathVariable("name") String name,
			@RequestParam(value = "currentPage", required = false) Optional<Integer> currentPage,
			@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
			@RequestParam(value = "dir", required = false) Optional<String> dir,
			@RequestParam(value = "props", required = false) Optional<String> props) {
		
		var pageable = gerarPagina(currentPage.orElse(0), pageSize.orElse(5), dir, props);
		var listaUsuario = usuarioService.findUserByName(name, pageable);
		var usuariosDTO = usuarioAssembler.toCollectionDTO(listaUsuario.getContent());
		var paginaUsuarioDTO = new PageImpl<>(usuariosDTO, pageable, listaUsuario.getTotalElements());
		
		return paginaUsuarioDTO;
	}
	
	@ResponseBody
	@GetMapping(value = "/buscar/{id}")
	public Usuario findUserById(@PathVariable("id") Long id) {
		return usuarioService.findUserById(id);
	}
	
	@ResponseBody
	@PostMapping(value = "/inserir", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void saveUser(@RequestBody @Valid Usuario usuario) {
		usuarioService.save(usuario);
	}
	
	@ResponseBody
	@PostMapping(value = "/alterar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@RequestBody @Valid Usuario usuario) {
		usuarioService.save(usuario);
	}

	private Pageable gerarPagina(Integer currentPage, Integer pageSize, Optional<String> dir, Optional<String> props) {
		return PageRequest.of(currentPage, pageSize, getDirection(dir), getAtributte(props));
	}

	private String getAtributte(Optional<String> atributo) {
		return atributo.orElse("id");
	}
	
	private Direction getDirection(Optional<String> dir) {
		var direcao = dir.orElse("asc");
		return direcao.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
	}
	
}
