package org.brandao.brutos.web.mapping;

import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.RequestMethodType;

public class WebController extends Controller{

	private RequestMethodType requestMethod;

	public WebController(ConfigurableApplicationContext context) {
		super(context);
		super.setRequestTypes(new MediaTypeMap());
		super.setResponseTypes(new MediaTypeMap());
	}

	public void addAction(ActionID id, Action method) {
		WebActionID i = 
				id instanceof WebActionID?
					(WebActionID)id :
					new WebActionID(id.getName(), null);
		super.addAction(i, method);
	}
	
	public RequestMethodType getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(RequestMethodType requestMethod) {
		this.requestMethod = requestMethod;
	}
	
}
