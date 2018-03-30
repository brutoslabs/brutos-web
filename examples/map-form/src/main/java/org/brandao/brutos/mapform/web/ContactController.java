package org.brandao.brutos.mapform.web;

import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.Result;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.mapform.entity.Contact;
import org.brandao.brutos.mapform.entity.ContactKey;
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
	@Result(value="contacts", mappingType=MappingTypes.VALUE)
	public Map<ContactKey, Contact> list(){
		Map<ContactKey, Contact> contacts = new HashMap<>();
		contacts.put(new ContactKey("jose", "aparecido"), new Contact("jose", "aparecido", "jose@brutosframework.com.br", "456-456-466"));
		contacts.put(new ContactKey("maria", "benedita"), new Contact("maria", "benedita", "maria@brutosframework.com.br", "222-222-222"));
		contacts.put(new ContactKey("afonso", "brandao"), new Contact("afonso", "brandao", "afonso@brutosframework.com.br", "123-123-123"));
		return contacts;
	}

	@Action("/contacts")
	@View("contacts/form")
	@RequestMethod(RequestMethodTypes.POST)
	public void updateContacts(
			@Basic(mappingType=MappingTypes.OBJECT)
			@KeyCollection(bean="itens")
			@ElementCollection(bean="itens")
			Map<ContactKey, Contact> contacts){
		//...
	}
	
}
