package org.brandao.brutos.web.mapping;

import org.brandao.brutos.Invoker;
import org.brandao.brutos.RequestProvider;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.mapping.DataTypeMap;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebMvcRequest;

public class ThrowWebAction 
	extends WebAction{

	public RequestMethodType getRequestMethod() {
		StackRequestElement sre = 
				Invoker.getInstance()
					.getStackRequestElement()
					.getPreviousStackRequestElement();		
		
		return
			sre == null?
			((WebMvcRequest)RequestProvider.getRequest()).getRequestMethodType() :
			((WebMvcRequest)sre.getAction().getMethodForm()).getRequestMethodType();
	}
	
	public DataTypeMap getRequestTypes() {
		StackRequestElement sre = 
				Invoker.getInstance()
					.getStackRequestElement()
					.getPreviousStackRequestElement();
		
		return 
			sre == null?
				(RequestProvider.getRequest().getResourceAction().getMethodForm()).getRequestTypes() :
				sre.getAction().getRequestTypes();
	}
	
	public DataTypeMap getResponseTypes() {
		StackRequestElement sre = 
				Invoker.getInstance()
					.getStackRequestElement()
					.getPreviousStackRequestElement();
		
		return 
			sre == null?
				(RequestProvider.getRequest().getResourceAction().getMethodForm()).getResponseTypes() :
				sre.getAction().getResponseTypes();
	}
	
}
