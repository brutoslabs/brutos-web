package org.brandao.brutos.web.mapping;

import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.web.RequestMethodType;

public class WebActionID extends ActionID{

	private static final long serialVersionUID = -8774025684963462253L;

	private String id;
	
	private RequestMethodType requestMethodType;

	public WebActionID(String id, RequestMethodType requestMethodType) {
		super(id + "[" + requestMethodType + "]");
		this.id = id;
		this.requestMethodType = requestMethodType;
	}

	public String getId() {
		return id;
	}

	public RequestMethodType getRequestMethodType() {
		return requestMethodType;
	}

	public boolean isDynamic() {
		return id.indexOf("{") != -1;
	}
	
}
