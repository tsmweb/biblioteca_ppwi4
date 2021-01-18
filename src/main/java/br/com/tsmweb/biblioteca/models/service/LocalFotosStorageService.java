package br.com.tsmweb.biblioteca.models.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import br.com.tsmweb.biblioteca.models.config.ConfigProjeto;
import br.com.tsmweb.biblioteca.models.model.dto.Foto;
import br.com.tsmweb.biblioteca.models.service.exception.FileStorageException;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;


public abstract class LocalFotosStorageService<T> {

	private Path diretorioFotos;
	
	@Autowired
	public LocalFotosStorageService() {
		try {
            diretorioFotos = Paths.get(ConfigProjeto.DIRETORIO_FOTOS).toAbsolutePath().normalize();  
			Files.createDirectories(diretorioFotos);
		} catch (IOException e) {
			throw new FileStorageException("Não foi possível criar diretórios de fotos ", e);
		}
	}

	public Foto armazenar(Foto foto) {
		Optional<String> nomeArquivo = buscarNomeArquivoPorId(foto.getId());
		
		if (nomeArquivo.isPresent()) {
			 remover(nomeArquivo.get());
		}
		
		String nomeFoto = gerarNomeArquivo(foto.getNomeArquivo());
		
		try {
			Path arquivoPath = getArquivoPath(nomeFoto);
			FileCopyUtils.copy(foto.getInputStream(), Files.newOutputStream(arquivoPath));
			Thumbnails.of(arquivoPath.toString()).size(50, 78).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
			foto.setNomeArquivo(nomeFoto);
			
			return foto;
		} catch (IOException e) {
			throw new FileStorageException("Erro na gravação da foto ", e);
		}
	}

	protected abstract Optional<String> buscarNomeArquivoPorId(Long id);
	
	protected abstract String getNomeArquivoDefault();
	
	public Foto excluirFoto(Foto foto) {
		Optional<String> nomeArquivo = buscarNomeArquivoPorId(foto.getId());
		String nomeFoto = nomeArquivo.orElse(foto.getNomeArquivo());
		
		if (remover(nomeFoto)) {
			foto.setNomeArquivo(getNomeArquivoDefault());
		};
		
		return foto;
	}

	private boolean remover(String foto) {
		String thumbnail = "thumbnail."+foto;
		
        if (!foto.isEmpty()) { 
			try {
				
				Path arquivoThumbnailPath = getArquivoPath(thumbnail);
				Files.deleteIfExists(arquivoThumbnailPath);
				
				Path arquivoPath = getArquivoPath(foto);
				Files.deleteIfExists(arquivoPath);
				
				return true;
			} catch (IOException e) {
				throw new FileStorageException("Erro na exclusão da foto", e);
			}
		}
        
        return false;
	}

	private InputStream recuperar(String nomeArquivo) {
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);
			return Files.newInputStream(arquivoPath);
		} catch (IOException e) {
			throw new FileStorageException("Erro na recuperação da foto ", e);
		}
	}

	public byte[] recuperarFoto(String nomeArquivo) {
		try {
			return Files.readAllBytes(getArquivoPath(nomeArquivo));
		} catch (IOException e) {
			throw new FileStorageException("Erro na leitura da foto ", e);
		}
	}

	private String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}

	private Path getArquivoPath(String nomeArquivo) {
		return diretorioFotos.resolve(Paths.get(nomeArquivo));
	}
	
}
