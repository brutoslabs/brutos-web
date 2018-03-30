package org.brandao.brutos.passwordform.web;

import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.passwordform.entity.User;
import org.brandao.brutos.validator.ValidatorException;
import org.brandao.brutos.web.RequestMethodTypes;

@Singleton
@Action(value="/", view=@View("users/passForm"))
public class PasswordController {

	@Action(value="/")
	@RequestMethod(RequestMethodTypes.POST)
	@View("users/passSuccess")
	@ResponseErrors(code=200, view="users/passForm")
	public void sign(@Valid	@NotNull @Basic(bean="user")User user) throws ValidatorException{
		//check the user
	}
	
}
