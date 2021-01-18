package br.com.tsmweb.biblioteca.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tsmweb.biblioteca.models.service.LivroFotosStorageService;

@RestController
@RequestMapping(value = "/livro/foto")
public class LivroFotoRestController extends FotoRestController {

	@Autowired
	public LivroFotoRestController(LivroFotosStorageService livroFotosStorageService) {
		super(livroFotosStorageService);
	}

}
