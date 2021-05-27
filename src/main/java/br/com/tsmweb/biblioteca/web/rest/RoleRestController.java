package br.com.tsmweb.biblioteca.web.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tsmweb.biblioteca.models.model.Role;
import br.com.tsmweb.biblioteca.models.service.RoleService;

@RestController
@RequestMapping(value = "/rest/role")
public class RoleRestController {
	
	@Autowired
	private RoleService roleService;
	
	@ResponseBody
	@GetMapping(value = "/listar")
	public Page<Role> findAll(
			@RequestParam(value = "currentPage", required = false) Optional<Integer> currentPage,
			@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
			@RequestParam(value = "dir", required = false) Optional<String> dir,
			@RequestParam(value = "props", required = false) Optional<String> props) {
		
		Pageable pageable = gerarPagina(currentPage.orElse(0), pageSize.orElse(5), dir, props);
		Page<Role> listaRoles = roleService.findAll(pageable);
		
		return listaRoles;
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
