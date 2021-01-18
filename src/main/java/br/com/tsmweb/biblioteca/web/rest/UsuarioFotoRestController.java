package br.com.tsmweb.biblioteca.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tsmweb.biblioteca.models.service.UsuarioFotosStorageService;

@RestController
@RequestMapping(value = "/usuario/foto")
public class UsuarioFotoRestController extends FotoRestController {

	@Autowired
	public UsuarioFotoRestController(UsuarioFotosStorageService usuarioFotosStorageService) {
		super(usuarioFotosStorageService);
	}
	
}
