package org.brandao.brutos.web;

import org.brandao.brutos.DefaultResourceAction;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.Scope;

public class HierarchyActionTypeResolver 
	extends AbstractWebActionTypeResolver{

	public ResourceAction getResourceAction(Controller controller, Scope scope,
			MutableMvcRequest request) {

		String requestID     = request.getRequestId();
		String controllerId  = controller.getId();
        StringPattern uriMap = getURIMapping( controllerId );
        
        if(uriMap.matches(requestID)){
        	if(controller.getDefaultAction() != null){
        		return new DefaultResourceAction(
        				controller, 
        				controller.getAction(controller.getDefaultAction()) );
        	}
        	else{
        		return new DefaultResourceAction( controller, null );
        	}
        }
        else{
        	ResourceAction resourceAction = this.getResourceAction(controller, 
        			controllerId, requestID, scope);
        	
        	if(resourceAction != null){
        		return resourceAction;
        	}
        	
        }
        
        for(String alias: controller.getAlias()){
            uriMap = getURIMapping( alias );
            
            if(uriMap.matches(requestID)){
            	if(controller.getDefaultAction() != null){
            		return new DefaultResourceAction(
            				controller, 
            				controller.getAction(controller.getDefaultAction()) );
            	}
            	else{
            		return new DefaultResourceAction( controller, null );
            	}
            }
            else{
            	ResourceAction resourceAction = this.getResourceAction(controller, 
            			alias, requestID, scope);
            	
            	if(resourceAction != null){
            		return resourceAction;
            	}
            	
            }
        	
        }
		
		return null;
	}

    private ResourceAction getResourceAction(Controller controller, 
    		String controllerId, String uri, Scope paramScope){
    	
        for(Action action: controller.getActions().values()){
            String fullActionId = controllerId + controller.getName();
        	
            StringPattern uriMap = getURIMapping( fullActionId );

            if(uriMap.matches(uri)){
                updateRequest(uri, paramScope, uriMap);
                return new DefaultResourceAction(controller, action);
            }
            
        }
        
        return null;
    }
	
}
