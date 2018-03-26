package org.brandao.brutos.formhandling.entity;

import javax.validation.constraints.NotNull;

import org.brandao.brutos.formhandling.validation.Equals;
import org.hibernate.validator.constraints.Length;

@Equals(first="confirmPassword", second="password")
public class User {

	@NotNull
	@Length(min=2, max=20)
	private String password;
	
	@NotNull
	@Length(min=2, max=20)
	private String confirmPassword;

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

}
