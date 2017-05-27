package org.brandao.brutos.web;

import org.brandao.brutos.DefaultResourceAction;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.mapping.WebActionID;

public class ParamActionTypeResolver 
	extends AbstractWebActionTypeResolver{

	public ResourceAction getResourceAction(Controller controller, Scope scope,
			MutableMvcRequest request) {

		String requestID     = request.getRequestId();
		String controllerId  = controller.getId();
        StringPattern uriMap = getURIMapping( controllerId );

        if(uriMap.matches(requestID)){
        	return this.getResourceAction(controller, scope, request, uriMap);
        }
		
        for(String alias: controller.getAlias()){
            uriMap = getURIMapping(alias);
            
            if(uriMap.matches(requestID)){
            	return this.getResourceAction(controller, scope, request, uriMap);
            }
        	
        }
        
		return null;
	}

	public ResourceAction getResourceAction(Controller controller, Scope scope,
			MutableMvcRequest request, StringPattern uriMap) {

    	String actionId                     = String.valueOf(scope.get( controller.getActionId()));
    	WebMvcRequest webRequest            = (WebMvcRequest)request;
    	RequestMethodType requestMethodType = webRequest.getRequestMethodType();
    	WebActionID id                      = new WebActionID(actionId, requestMethodType);
        Action method                       = controller.getAction(id);
        
        if(method == null && controller.getDefaultAction() != null){
    		method = controller.getAction(controller.getDefaultAction());
        }
        
        if(method != null){
        	super.updateRequest(request.getRequestId(), scope, uriMap);
        	return new DefaultResourceAction(method.getController(), method);
        }
        else{
        	return null;
        }
	}
	
}
