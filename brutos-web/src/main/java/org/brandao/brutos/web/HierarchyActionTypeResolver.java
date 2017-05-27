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

        ResourceAction resourceAction = 
    		this.getResourceAction(requestID, controllerId, controller, scope);
        
        if(resourceAction != null){
        	return resourceAction;
        }
        
        for(String alias: controller.getAlias()){
        	
            resourceAction = 
            		this.getResourceAction(requestID, alias, controller, scope);
        	
            if(resourceAction != null){
            	return resourceAction;
            }
        	
        }
		
		return null;
	}

	private ResourceAction getResourceAction(String requestID, String controllerID, 
			Controller controller, Scope scope){
		
		StringPattern uriMap = getURIMapping(controllerID);
		
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
        			controllerID, requestID, scope);
        	
        	if(resourceAction != null){
        		return resourceAction;
        	}
        	
        }
		
        return null;
	}
	
    private ResourceAction getResourceAction(Controller controller, 
    		String controllerId, String uri, Scope paramScope){
    	
        for(Action action: controller.getActions().values()){
        	
        	ResourceAction resourceAction =
        			this.getResourceAction(controller, action, controllerId, 
        					action.getName(), uri, paramScope);

        	if(resourceAction != null){
        		return resourceAction;
        	}

        	for(String alias: action.getAlias()){

            	resourceAction =
            			this.getResourceAction(controller, action, controllerId, 
            					alias, uri, paramScope);

            	if(resourceAction != null){
            		return resourceAction;
            	}
        		
        	}
        	
        }
        
        return null;
    }

    private ResourceAction getResourceAction(
    		Controller controller, Action action, 
    		String controllerId, String actionId, String uri, Scope paramScope){
    	
        String fullActionId = controllerId + actionId;
    	
        StringPattern uriMap = getURIMapping( fullActionId );

        if(uriMap.matches(uri)){
            updateRequest(uri, paramScope, uriMap);
            return new DefaultResourceAction(controller, action);
        }
        
        return null;
    }
    
}
