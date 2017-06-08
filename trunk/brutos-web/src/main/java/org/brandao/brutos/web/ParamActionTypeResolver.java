package org.brandao.brutos.web;

import org.brandao.brutos.DefaultResourceAction;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.mapping.WebActionID;

public class ParamActionTypeResolver 
	extends AbstractWebActionTypeResolver{

	public ResourceAction getResourceAction(Controller controller,
			MutableMvcRequest request) {

    	String actionId                     = String.valueOf(request.getParameter(controller.getActionId()));
    	WebMvcRequest webRequest            = (WebMvcRequest)request;
    	RequestMethodType requestMethodType = webRequest.getRequestMethodType();
    	WebActionID id                      = new WebActionID(actionId, requestMethodType);
        Action method                       = controller.getAction(id);
        
        if(method != null){
        	return new DefaultResourceAction(method.getController(), method);
        }
        else
        if(controller.getDefaultAction() != null){
    		return new DefaultResourceAction(controller, controller.getAction(controller.getDefaultAction()));
        }
        else{
        	return null;
        }
	}
	
}
