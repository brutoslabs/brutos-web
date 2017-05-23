package org.brandao.brutos;

import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.Scope;

public interface ActionTypeResolver {

	ResourceAction getResourceAction(Controller controller, Scope scope,
			MutableMvcRequest request);
	
}
