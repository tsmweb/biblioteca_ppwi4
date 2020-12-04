package br.com.tsmweb.biblioteca.models.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "EDITORA")
public class Editora implements Serializable {

	private static final long serialVersionUID = -4532432672297537484L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Size(min = 3, max = 100, message="Digite o mínimo {min} e o máximo {max} caracteres")
	@NotEmpty(message = "O nome da editora é obrigatório")
	@NotBlank(message = "O nome da editora é obrigatório")
	@NotNull(message = "O nome da editora é obrigatório")
	@Column(name = "NAME", length = 100, nullable = false)
	private String name;
	
	public Editora(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Editora() {}

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
		Editora other = (Editora) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Editora [id=" + id + ", name=" + name + "]";
	}
	
}
