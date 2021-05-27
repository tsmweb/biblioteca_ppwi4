package br.com.tsmweb.biblioteca.models.service.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tsmweb.biblioteca.models.model.Usuario;
import br.com.tsmweb.biblioteca.models.model.dto.UsuarioDTO;

@Component
public class UsuarioDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioDTO toDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}
	
	public List<UsuarioDTO> toCollectionDTO(List<Usuario> usuarios) {
		return usuarios.stream().
				map(usuario -> toDTO(usuario)).
				collect(Collectors.toList());
	}
	
}
