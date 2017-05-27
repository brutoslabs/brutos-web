package org.brandao.brutos.web.mapping;

import java.util.HashSet;
import java.util.Set;

import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.DataType;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.MediaType;
import org.brandao.brutos.web.RequestMethodType;

public class WebController extends Controller{

	private RequestMethodType requestMethod;

	private MediaTypeMap requestTypeMap;

	private MediaTypeMap responseTypeMap;
	
	public WebController(ConfigurableApplicationContext context) {
		super(context);
		this.requestTypeMap  = new MediaTypeMap();
		this.responseTypeMap = new MediaTypeMap();
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

	public Set<DataType> getRequestTypes() {
		return new HashSet<DataType>(this.requestTypeMap.getSet());
	}

	public void setRequestTypes(Set<DataType> requestTypes) {
		this.requestTypeMap = new MediaTypeMap(requestTypes);
	}

	public Set<DataType> getResponseTypes() {
		return new HashSet<DataType>(this.responseTypeMap.getSet());
	}

	public void setResponseTypes(Set<DataType> responseTypes) {
		this.responseTypeMap = new MediaTypeMap(responseTypes);
	}
	
	public boolean acceptRequestType(MediaType value){
		return this.requestTypeMap.accept(value);
	}

	public MediaTypeMap getRequestTypeMap() {
		return requestTypeMap;
	}

	public MediaTypeMap getResponseTypeMap() {
		return responseTypeMap;
	}
	
}
