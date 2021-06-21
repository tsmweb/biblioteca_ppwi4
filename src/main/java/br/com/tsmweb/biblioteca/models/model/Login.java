package br.com.tsmweb.biblioteca.models.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class Login implements Serializable {
	
	private static final long serialVersionUID = 4955184079794474996L;
	
	@Email
	@NotEmpty
	@NotBlank
	private String email;
	
	@NotEmpty
	@NotBlank
	private String password;
	
	public Login(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public Login() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
