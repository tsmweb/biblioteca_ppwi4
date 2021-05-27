package br.com.tsmweb.biblioteca.models.service.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tsmweb.biblioteca.models.model.Livro;
import br.com.tsmweb.biblioteca.models.model.dto.LivroDTO;

@Component
public class LivroDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public LivroDTO toDTO(Livro livro) {
//		modelMapper.typeMap(Livro.class, LivroDTO.class).addMappings(mapper -> {
//			mapper.map(src -> src.getPublisher().getName(), 
//					LivroDTO::setPublisherName);
//		});
		
		return modelMapper.map(livro, LivroDTO.class);
	}
	
	public List<LivroDTO> toCollectionDTO(List<Livro> livros) {
		return livros.stream().
				map(livro -> toDTO(livro)).
				collect(Collectors.toList());
	}
	
}
