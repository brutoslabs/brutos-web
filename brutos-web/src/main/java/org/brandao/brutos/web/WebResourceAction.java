package org.brandao.brutos.web;

import org.brandao.brutos.DefaultResourceAction;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebController;

public class WebResourceAction extends DefaultResourceAction{

	protected WebAction action;

	protected WebController controller;
	
	public WebResourceAction(WebController controller, WebAction action) {
		super(controller, action);
		// TODO Auto-generated constructor stub
	}

	public RequestMethodType getRequestMethod(){
		return this.action == null? this.controller.getRequestMethod() : action.getRequestMethod();
	}
}
