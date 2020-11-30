package br.com.tsmweb.biblioteca.models.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "EMPRESTIMO")
public class Emprestimo implements Serializable {
	
	private static final long serialVersionUID = 7373672968004641315L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", nullable = false)
	private Usuario user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BOOK_ID", nullable = false)
	private Livro book;
	
	@Column(name = "LOAN_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date loanDate;
	
	@Column(name = "RETURN_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date returnDate;
	
	@Column(name = "ACTUAL_RETURN_DATE", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date actualReturnDate;
	
	public Emprestimo(Long id, Usuario user, Livro book, Date loanDate, Date returnDate, Date actualReturnDate) {
		this.id = id;
		this.user = user;
		this.book = book;
		this.loanDate = loanDate;
		this.returnDate = returnDate;
		this.actualReturnDate = actualReturnDate;
	}

	public Emprestimo() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public Livro getBook() {
		return book;
	}

	public void setBook(Livro book) {
		this.book = book;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Date getActualReturnDate() {
		return actualReturnDate;
	}

	public void setActualReturnDate(Date actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
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
		Emprestimo other = (Emprestimo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Emprestimo [id=" + id + ", user=" + user + ", book=" + book + ", loanDate=" + loanDate + ", returnDate="
				+ returnDate + ", actualReturnDate=" + actualReturnDate + "]";
	}
	
}
