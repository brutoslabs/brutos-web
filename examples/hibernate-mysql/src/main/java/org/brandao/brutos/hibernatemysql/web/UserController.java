package org.brandao.brutos.hibernatemysql.web;

import java.util.List;

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
import org.brandao.brutos.hibernatemysql.entity.User;
import org.brandao.brutos.hibernatemysql.registry.UserRegistry;
import org.brandao.brutos.hibernatemysql.registry.UserRegistryException;
import org.brandao.brutos.validator.ValidatorException;
import org.brandao.brutos.web.RequestMethodTypes;
import org.brandao.brutos.web.WebFlowController;

@Singleton
@Action(value="/users/add", view=@View("users/userForm"))
@ResponseErrors(rendered=false)
public class UserController {

	@Transient
	@Inject
	private UserRegistry userRegistry;

	@Action("/")
	public void index(){
		WebFlowController.redirectTo("/users");
	}
	
	@Action("/users")
	@View("users/list")
	@Result("users")
	public List<User> findAllUsers() throws UserRegistryException{
		return this.userRegistry.findAll();
	}

	@Action("/users")
	@RequestMethod(RequestMethodTypes.POST)
	@ResponseErrors(code=200, view="users/userForm")
	public void updateUser(
			@Valid
			@Basic(bean="user")User user) throws ValidatorException, UserRegistryException{
		
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
	public User showUpdateUser(@Basic(bean="id") Integer id) throws UserRegistryException{
		return this.userRegistry.findById(id);
	}

	@Action("/users/{id:\\d{1,6}}/delete")
	@RequestMethod(RequestMethodTypes.POST)
	public void deleteUser(@Basic(bean="id") Integer id) throws UserRegistryException{
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
	public User showUser(@Basic(bean="id") Integer id) throws UserRegistryException{
		return this.userRegistry.findById(id);
	}
	
}
