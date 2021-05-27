package br.com.tsmweb.biblioteca.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.tsmweb.biblioteca.models.model.Editora;
import br.com.tsmweb.biblioteca.models.service.EditoraService;

@Component
public class EditoraConverter implements Converter<String, Editora> {

	@Autowired
	private EditoraService editoraService;
	
	@Override
	public Editora convert(String source) {
		if (source.isEmpty()) {
			return null;
		}
		
		var id = Long.valueOf(source);
		return editoraService.findById(id);
	}

}
