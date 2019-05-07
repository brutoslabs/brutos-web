package org.brandao.brutos.web.mapping;

import org.brandao.brutos.mapping.ControllerID;
import org.brandao.brutos.web.RequestMethodType;

public class WebControllerID extends ControllerID{

	private static final long serialVersionUID = -8774025684963462253L;

	private String id;
	
	private RequestMethodType requestMethodType;

	public WebControllerID(String id, RequestMethodType requestMethodType) {
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

}
