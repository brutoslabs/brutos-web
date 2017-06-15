package org.brandao.brutos.web;

import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebController;

public class HierarchyActionTypeResolver 
	extends AbstractWebActionTypeResolver{

	public ResourceAction getResourceAction(Controller controller,
			MutableMvcRequest request) {
		ActionID actionID = controller.getDefaultAction();
		
		if(actionID == null){
			return new WebResourceAction((WebController) controller, null);
		}
		else{
			WebAction action = (WebAction)controller.getAction(actionID);
			return new WebResourceAction((WebController) controller,  action);
		}
	}

    
}
