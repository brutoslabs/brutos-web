package org.brandao.brutos.web;

import org.brandao.brutos.DefaultResourceAction;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.Scope;

public class DetachedActionTypeResolver 
	extends AbstractWebActionTypeResolver{

	public ResourceAction getResourceAction(Controller controller, Scope scope,
			MutableMvcRequest request) {

		String requestID     = request.getRequestId();
        
    	ResourceAction resourceAction = this.getResourceAction(controller, 
    			requestID, scope);
    	
    	if(resourceAction != null){
    		return resourceAction;
    	}
    	
		return null;
	}

    private ResourceAction getResourceAction(Controller controller, 
    		String uri, Scope paramScope){
    	
        for(Action action: controller.getActions().values()){

            StringPattern uriMap = getURIMapping(action.getName());

            if(uriMap.matches(uri)){
                updateRequest(uri, paramScope, uriMap);
                return new DefaultResourceAction(controller, action);
            }

            for(String name: action.getAlias()){
            	
            	uriMap = getURIMapping(name);
            	
                if(uriMap.matches(uri)){
                    updateRequest(uri, paramScope, uriMap);
                    return new DefaultResourceAction(controller, action);
                }
            	
            }
            
        }
        
        return null;
    }
	
}
