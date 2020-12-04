package br.com.tsmweb.biblioteca.models.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "LIVRO")
public class Livro implements Serializable  {

	private static final long serialVersionUID = 5651073696044216356L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull(message = "A editora do livro é obrigatório")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PUBLISHER_ID", nullable = false)
	private Editora publisher;
	
	@Size(min = 10, max = 100, message="Digite o mínimo {min} e o máximo {max} caracteres")
	@NotEmpty(message = "O título do livro é obrigatório")
	@NotBlank(message = "O título do livro é obrigatório")
	@NotNull(message = "O título do livro é obrigatório")
	@Column(name = "TITLE", length = 100, nullable = false)
	private String title;
	
	@Size(min = 3, max = 100, message="Digite o mínimo {min} e o máximo {max} caracteres")
	@NotEmpty(message = "O autor do livro é obrigatório")
	@NotBlank(message = "O autor do livro é obrigatório")
	@NotNull(message = "O autor do livro é obrigatório")
	@Column(name = "AUTHOR", length = 100, nullable = false)
	private String author;
	
	@Positive(message = "Digite um número maior que 0")
	@NotNull(message = "O número de páginas do livro é obrigatório")
	@Column(name = "NUMBER_PAGES", nullable = false)
	private Integer numberPages;
	
	@Positive(message = "Digite um número maior que 0")
	@NotNull(message = "O ano da publicação do livro é obrigatório")
	@Column(name = "YEAR_PUBLICATION", nullable = false)
	private Integer yearPublication;
	
	@Positive(message = "Digite um número maior que 0")
	@NotNull(message = "A quantidade total de livros é obrigatório")
	@Column(name = "TOTAL_AMOUNT", nullable = false)
	private Integer totalAmount;

	public Livro(Long id, Editora publisher, String title, String author, Integer numberPages, Integer yearPublication,
			Integer totalAmount) {
		this.id = id;
		this.publisher = publisher;
		this.title = title;
		this.author = author;
		this.numberPages = numberPages;
		this.yearPublication = yearPublication;
		this.totalAmount = totalAmount;
	}
	
	public Livro() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Editora getPublisher() {
		return publisher;
	}

	public void setPublisher(Editora publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getNumberPages() {
		return numberPages;
	}

	public void setNumberPages(Integer numberPages) {
		this.numberPages = numberPages;
	}

	public Integer getYearPublication() {
		return yearPublication;
	}

	public void setYearPublication(Integer yearPublication) {
		this.yearPublication = yearPublication;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
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
		Livro other = (Livro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", publisher=" + publisher + ", title=" + title + ", author=" + author
				+ ", numberPages=" + numberPages + ", yearPublication=" + yearPublication + ", totalAmount=" + totalAmount + "]";
	}

}
