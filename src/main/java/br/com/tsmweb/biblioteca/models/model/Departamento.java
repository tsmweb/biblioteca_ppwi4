package br.com.tsmweb.biblioteca.models.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "DEPARTAMENTO")
public class Departamento implements Serializable {

	private static final long serialVersionUID = -6492943105351536508L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Size(min = 2, max = 50, message="Digite o mínimo {min} e o máximo {max} caracteres")
	@NotEmpty(message = "O nome do departamento é obrigatório")
	@NotBlank(message = "O nome do departamento é obrigatório")
	@NotNull(message = "O nome do departamento é obrigatório")
	@Column(name = "NAME", length = 50, nullable = false)
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "departamento")
	private List<Usuario> listaUsuarios = new ArrayList<>();
	
	public Departamento(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Departamento(Long id) {
		this.id = id;
	}
	
	public Departamento() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Departamento [id=" + id + ", name=" + name + "]";
	}
	
}
