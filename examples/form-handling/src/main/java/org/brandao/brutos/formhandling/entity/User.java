package org.brandao.brutos.formhandling.entity;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.brandao.brutos.formhandling.entity.validation.Save;
import org.brandao.brutos.formhandling.entity.validation.Update;
import org.hibernate.validator.constraints.Length;

public class User {

	@NotNull(groups=Update.class)
	private Integer id;

	@NotNull(groups={Save.class, Update.class})
	@Length(min=3, max=60, groups={Save.class, Update.class})
	private String name;

	@NotNull(groups={Save.class, Update.class})
	@Pattern(regexp="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", groups={Save.class, Update.class})
	private String email;

	@NotNull(groups={Save.class, Update.class})
	@Length(min=5, max=60, groups={Save.class, Update.class})
	private String address;

	@NotNull(groups={Save.class, Update.class})
	@Length(min=5, max=60, groups={Save.class, Update.class})
	private String password;

	@NotNull(groups={Save.class, Update.class})
	@Length(min=5, max=60, groups={Save.class, Update.class})
	private String confirmPassword;

	private boolean newsletter;

	@NotNull(groups={Save.class, Update.class})
	@Size(min=1, groups={Save.class, Update.class})
	private List<String> framework;

	@NotNull(groups={Save.class, Update.class})
	private Sex sex;

	@NotNull(groups={Save.class, Update.class})
	private Integer number;

	@NotNull(groups={Save.class, Update.class})
	@Pattern(regexp="^[A-Z]{2,2}$", groups={Save.class, Update.class})
	private String country;

	@NotNull(groups={Save.class, Update.class})
	@Size(min=2, groups={Save.class, Update.class})
	private List<String> skill;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public boolean isNewsletter() {
		return newsletter;
	}

	public void setNewsletter(boolean newsletter) {
		this.newsletter = newsletter;
	}

	public List<String> getFramework() {
		return framework;
	}

	public void setFramework(List<String> framework) {
		this.framework = framework;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

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
