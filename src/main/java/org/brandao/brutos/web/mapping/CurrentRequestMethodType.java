package org.brandao.brutos.web.mapping;

import org.brandao.brutos.RequestProvider;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebMvcRequest;

public class CurrentRequestMethodType extends RequestMethodType{

	public CurrentRequestMethodType() {
		super(null, null);
	}

	public String getId() {
		return ((WebMvcRequest)RequestProvider.getRequest()).getRequestMethodType().getId();
	}

	public String getName() {
		return ((WebMvcRequest)RequestProvider.getRequest()).getRequestMethodType().getName();
	}
	
	public String toString(){
		return ((WebMvcRequest)RequestProvider.getRequest()).getRequestMethodType().toString();
	}
	
}
