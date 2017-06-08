package org.brandao.brutos;

import org.brandao.brutos.mapping.Controller;

public interface ActionTypeResolver {

	ResourceAction getResourceAction(Controller controller, MutableMvcRequest request);
	
}
