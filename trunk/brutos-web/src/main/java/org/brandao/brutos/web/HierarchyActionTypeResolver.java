package org.brandao.brutos.web;

import java.util.Iterator;
import java.util.Map;

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
        	return new DefaultResourceAction( controller, null );
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
            	return new DefaultResourceAction( controller, null );
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
    	
        Map<String,Action> actions = controller.getActions();
        Iterator<String> actionsId = actions.keySet().iterator();
        
        while(actionsId.hasNext()){
            String actionId = (String) actionsId.next();
            String fullActionId =
            		controllerId + actionId;

            StringPattern uriMap = getURIMapping( fullActionId );

            if(uriMap.matches(uri)){
                updateRequest(uri, paramScope, uriMap);
                Action a = actions.get(actionId);
                return a == null? null : new DefaultResourceAction(controller, a);
            }
            
        }
        
        return null;
    }
	
}
