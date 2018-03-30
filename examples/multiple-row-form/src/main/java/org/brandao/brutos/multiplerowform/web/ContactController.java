package org.brandao.brutos.multiplerowform.web;

import java.util.ArrayList;
import java.util.List;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.Result;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.multiplerowform.entity.Contact;
import org.brandao.brutos.web.RequestMethodTypes;
import org.brandao.brutos.web.WebFlowController;

@ResponseErrors(enabled=false)
public class ContactController {

	@Action("/")
	public void index(){
		WebFlowController.redirectTo("/contacts");
	}
	
	@Action("/contacts")
	@View("contacts/form")
	@Result("contacts")
	public List<Contact> list(){
		List<Contact> contacts = new ArrayList<>();
		contacts.add(new Contact("Jose", "Aparecido", "jose@brutosframework.com.br", "456-456-466"));
		contacts.add(new Contact("Maria", "Benedita", "maria@brutosframework.com.br", "222-222-222"));
		contacts.add(new Contact("afonso", "brandao", "afonso@brutosframework.com.br", "123-123-123"));
		return contacts;
	}

	@Action("/contacts")
	@View("contacts/form")
	@RequestMethod(RequestMethodTypes.POST)
	public void updateUser(
			@Basic(mappingType=MappingTypes.OBJECT)
			List<Contact> contacts){
		//...
	}
	
}
