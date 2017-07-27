package org.brandao.brutos.interceptor;

import java.text.ParseException;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.DataType;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.MutableMvcResponse;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.ResourceAction;

public class InterceptorHandlerImp 
	implements ConfigurableInterceptorHandler {

	private MutableMvcRequest request;

	private MutableMvcResponse response;

	//private Object[] params;
	
	public InterceptorHandlerImp(MutableMvcRequest request,
			MutableMvcResponse response) {
		this.request  = request;
		this.response = response;
		//this.params   = null;
	}

	public ResourceAction getResourceAction() {
		return request.getResourceAction();
	}

	public DataType getRequestType() {
		return request.getType();
	}

	public Object getResource() {
		return request.getResource();
	}

	public String requestId() {
		return request.getRequestId();
	}

	public ApplicationContext getContext() {
		return request.getApplicationContext();
	}

	public Object[] getParameters() throws InstantiationException,
			IllegalAccessException, ParseException {
		
		return request.getParameters();
		/*
		this.params = request.getParameters();
		
		if (params == null) {
			StackRequestElement stackRequestElement = request.getStackRequestElement();
			if (stackRequestElement.getParameters() == null) {
				Action action = request.getResourceAction().getMethodForm();
				this.params = action == null? null : action.getParameterValues(request.getResource());
			}
			else{
				this.params = request.getResourceAction().getMethodForm()
						.getParameterValues(request.getResource(), stackRequestElement.getParameters());
			}
		}
		
		return this.params;
		*/
	}

	public Object getResult() {
		return this.response.getResult();
	}

	public MvcRequest getRequest() {
		return this.request;
	}

	public MvcResponse getResponse() {
		return this.response;
	}
	
}
