package br.com.tsmweb.biblioteca.models.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import br.com.tsmweb.biblioteca.models.model.dto.ExcluirFoto;
import br.com.tsmweb.biblioteca.models.model.dto.Foto;
import br.com.tsmweb.biblioteca.models.service.exception.FileStorageException;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

@Service
public class LocalFotosStorageService {

	@Value("${local.diretorio-fotos}")
	private Path diretorioFotos;
	
	public void armazenar(Foto foto) {
		try {
			Path arquivoPath = getArquivoPath(foto.getNomeArquivo());
			FileCopyUtils.copy(foto.getInputStream(), Files.newOutputStream(arquivoPath));
			Thumbnails.of(arquivoPath.toString()).size(50, 78).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new FileStorageException("Erro na gravação da foto", e.getCause());
		}
	}
	
	public void remover(ExcluirFoto foto) {
		try {
			Path arquivoPath = getArquivoPath(foto.getNomeArquivo());
			Files.deleteIfExists(arquivoPath);
		} catch (IOException e) {
			throw new FileStorageException("Erro na exclusão da foto", e.getCause());
		} 
	}
	
	public InputStream recuperar(String nomeArquivo) {
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);
			return Files.newInputStream(arquivoPath);
		} catch (IOException e) {
			throw new FileStorageException("Erro na recuperação da foto", e.getCause());
		}
	}
	
	public byte[] recuperarFoto(String nomeArquivo) {
		try {
			return Files.readAllBytes(getArquivoPath(nomeArquivo));
		} catch (IOException e) {
			throw new FileStorageException("Erro na leitura da foto", e.getCause());
		}
	}
	
	public String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString()+"_"+nomeOriginal;
	}

	private Path getArquivoPath(String nomeArquivo) {
		return diretorioFotos.resolve(Paths.get(nomeArquivo));
	}
	
}
