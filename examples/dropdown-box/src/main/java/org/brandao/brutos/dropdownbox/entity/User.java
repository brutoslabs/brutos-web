package org.brandao.brutos.dropdownbox.entity;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User {

	@NotNull
	@Pattern(regexp="^[A-Z]{2,2}$")
	private String country;

	@NotNull
	@Size(min=2, max=5)
	private List<String> skill;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<String> getSkill() {
		return skill;
	}

	public void setSkill(List<String> skill) {
		this.skill = skill;
	}
	
}
