package org.brandao.brutos.mapform.web;

import java.util.Map;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.web.RequestMethodTypes;

@ResponseErrors(rendered=false)
@Action(value="/", view=@View("contacts/form"))
public class ContactController {

	@Action("/contact")
	@View("contacts/show")
	@RequestMethod(RequestMethodTypes.POST)
	public void post(
			@Basic(mappingType=MappingTypes.OBJECT)
			Map<String, String> contactForm){
		//...
	}
	
}
