package br.com.tsmweb.biblioteca.models.service.components;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class PrintJasperReport {

	private String file;
	private Map<String, Object> params;
	private Collection<?> collection;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Collection<?> getCollection() {
		return collection;
	}

	public void setCollection(Collection<?> collection) {
		this.collection = collection;
	}
	
}
