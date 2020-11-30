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
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 4786887518061634426L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Min(value = 10)
	@Max(value = 100)
	@NotBlank(message = "o campo nome do usuário é obrigatório")
	@NotNull(message = "o campo nome do usuário é obrigatório")
	@Column(name = "USER_NAME", length = 100, nullable = false)
	private String username;
	
	@Min(value = 4)
	@Max(value = 100)
	@NotBlank(message = "o campo senha do usuário é obrigatório")
	@NotNull(message = "o campo senha do usuário é obrigatório")
	@Column(name = "PASSWORD", length = 100, nullable = false)
	private String password;
	
	@NotBlank(message = "o campo confirmação de senha do usuário é obrigatório")
	@NotNull(message = "o campo confirmação de senha do usuário é obrigatório")
	@Transient
	private String confirmPassword;
	
	@Max(value = 100)
	@NotBlank(message = "o campo e-mail do usuário é obrigatório")
	@NotNull(message = "o campo e-mail do usuário é obrigatório")
	@Column(name = "EMAIL", length = 100, nullable = false, unique = true)
	private String email;
	
	@Column(name = "ACTIVE", nullable = false)
	private boolean active = false;
	
	@Column(name = "FAILED_LOGIN", nullable = true)
	private Integer failedLogin = 0;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_LOGIN", columnDefinition = "DATE", nullable = true)
	private Date lastLogin;
	
	@Column(name = "PHOTO", length = 100, nullable = true)
	private String photo;
	
	@Column(name = "CONTENT_TYPE", length = 30, nullable = true)
	private String contentType;
	
	@Column(name = "ADMIN", nullable = true)
	private boolean admin;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DEPARTAMENTO_ID", nullable = false)
	private Departamento departamento;
	
	public Usuario(Long id, String username, String password, String confirmPassword, String email, boolean active,
			Integer failedLogin, Date lastLogin, String photo, String contentType, Boolean admin, Departamento departamento) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.email = email;
		this.active = active;
		this.failedLogin = failedLogin;
		this.lastLogin = lastLogin;
		this.photo = photo;
		this.contentType = contentType;
		this.admin = admin;
		this.departamento = departamento;
	}

	public Usuario() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getFailedLogin() {
		return failedLogin;
	}

	public void setFailedLogin(Integer failedLogin) {
		this.failedLogin = failedLogin;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", password=" + password + ", confirmPassword="
				+ confirmPassword + ", email=" + email + ", active=" + active + ", failedLogin=" + failedLogin
				+ ", lastLogin=" + lastLogin + ", photo=" + photo + ", contentType=" + contentType + ", admin=" + admin
				+ ", departamento=" + departamento + "]";
	}

}
