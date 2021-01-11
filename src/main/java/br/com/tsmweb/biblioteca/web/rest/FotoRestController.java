package br.com.tsmweb.biblioteca.web.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/foto")
public class FotoRestController {
	
	@PostMapping(value = "/gravar", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String uploadFoto(@RequestParam MultipartFile foto) {
		System.out.println("$====== PASSEI ======$");
		return "";
	}

}
