package org.brandao.brutos.formhandling.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Result;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.formhandling.entity.User;
import org.brandao.brutos.formhandling.registry.UserRegistry;
import org.brandao.brutos.validator.ValidatorException;
import org.brandao.brutos.web.RequestMethodTypes;
import org.brandao.brutos.web.WebFlowController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Action(value="/users/add", view=@View("users/userForm"))
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Transient
	@Inject
	private UserRegistry userRegistry;

	@Action("/")
	public void index(){
	
		if(logger.isDebugEnabled()){
			logger.debug("index()");
		}
		
		WebFlowController.redirectTo("/users");
	}
	
	@Action("/users")
	@View("users/list")
	@Result("users")
	public List<User> findAllUsers(){
	
		if(logger.isDebugEnabled()){
			logger.debug("findAllUsers()");
		}
		
		return this.userRegistry.findAll();
	}

	@Action("/users")
	@RequestMethod(RequestMethodTypes.POST)
	@ResponseErrors(code=200, view="users/userForm")
	public void updateUser(
			@Valid
			@Basic(bean="user")User user) throws ValidatorException{
		
		if(logger.isDebugEnabled()){
			logger.debug("updateUser(): {}", user);
		}
		
		boolean isNew = user.getId() == null;
		
		this.userRegistry.registerUser(user);

		WebFlowController
		.redirect()
			.put("css", "success")
			.put("msg", isNew? "User added successfully!" : "User updated successfully!")
		.to("/users/" + user.getId());
	}

	@Action("/users/{id:\\d{1,6}}/update")
	@View("users/userForm")
	@Result("user")
	public User showUpdateUser(@Basic(bean="id") Integer id){
		
		if(logger.isDebugEnabled()){
			logger.debug("showUpdateUser()");
		}
		
		return this.userRegistry.findById(id);
	}

	@Action("/users/{id:\\d{1,6}}/delete")
	@RequestMethod(RequestMethodTypes.POST)
	public void deleteUser(@Basic(bean="id") Integer id){
		
		if(logger.isDebugEnabled()){
			logger.debug("deleteUser()");
		}
		
		this.userRegistry.removeUser(id);
		
		WebFlowController
		.redirect()
			.put("css", "success")
			.put("msg", "User is deleted!")
		.to("/users");
	}

	@Action("/users/{id:\\d{1,6}}")
	@View("users/show")
	@Result("user")
	public User showUser(@Basic(bean="id") Integer id){
		
		if(logger.isDebugEnabled()){
			logger.debug("showUser()");
		}
		
		return this.userRegistry.findById(id);
	}

	public List<String> getFrameworksList(){
		List<String> frameworksList = new ArrayList<String>();
		frameworksList.add("Brutos MVC");
		frameworksList.add("Struts 2");
		frameworksList.add("JSF 2");
		frameworksList.add("Spring MVC");
		frameworksList.add("GWT");
		frameworksList.add("Play");
		frameworksList.add("Apache Wicket");
		return frameworksList;
	}

	public Map<String, String> getJavaSkillList(){
		Map<String, String> skill = new LinkedHashMap<String, String>();
		skill.put("Hibernate", "Hibernate");
		skill.put("Spring", "Spring");
		skill.put("Struts", "Struts");
		skill.put("Groovy", "Groovy");
		skill.put("Grails", "Grails");
		return skill;
	}

	public List<Integer> getNumberList(){
		List<Integer> numbers = new ArrayList<Integer>();
		numbers.add(1);
		numbers.add(2);
		numbers.add(3);
		numbers.add(4);
		numbers.add(5);
		return numbers;
	}

	public Map<String, String> getCountryList(){
		Map<String, String> country = new LinkedHashMap<String, String>();
		country.put("BR", "Brazil");
		country.put("US", "United Stated");
		country.put("CN", "China");
		country.put("SG", "Singapore");
		country.put("MY", "Malaysia");
		return country;
	}
	
}
