package org.brandao.brutos.web;

import org.brandao.brutos.DefaultResourceAction;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebController;

public class WebResourceAction extends DefaultResourceAction{

	protected RequestMethodType requestMethod;
	
	protected WebAction action;

	protected WebController controller;
	
	public WebResourceAction(RequestMethodType requestMethod, 
			WebController controller, WebAction action) {
		super(controller, action);
		this.controller = controller;
		this.action = action;
		this.requestMethod = requestMethod;
	}

	public RequestMethodType getRequestMethod(){
		return this.requestMethod;
		//return this.action == null? this.controller.getRequestMethod() : action.getRequestMethod();
	}
	
}
