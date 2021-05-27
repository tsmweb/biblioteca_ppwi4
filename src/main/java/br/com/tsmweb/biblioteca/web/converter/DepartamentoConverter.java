package br.com.tsmweb.biblioteca.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.tsmweb.biblioteca.models.model.Departamento;
import br.com.tsmweb.biblioteca.models.service.DepartamentoService;

@Component
public class DepartamentoConverter implements Converter<String, Departamento> {

	@Autowired
	private DepartamentoService departamentoService;
	
	@Override
	public Departamento convert(String source) {
		if (source.isEmpty()) {
			return null;
		}
		
		var id = Long.valueOf(source);
		return departamentoService.findById(id);
	}

}
