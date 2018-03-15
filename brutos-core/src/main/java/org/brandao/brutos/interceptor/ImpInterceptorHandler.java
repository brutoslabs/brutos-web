/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.interceptor;

import java.text.ParseException;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.DataType;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.StackRequestElement;

/**
 * 
 * @author Brandao
 */
@Deprecated
public class ImpInterceptorHandler implements ConfigurableInterceptorHandler {

	private String URI;

	private String requestId;

	private ResourceAction resourceAction;

	private ApplicationContext context;

	private Object resource;

	private Object[] parameters;

	private Object result;

	private RequestInstrument requestInstrument;

	private StackRequestElement stackRequestElement;

	public ImpInterceptorHandler() {
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String URI) {
		this.URI = URI;
		this.setRequestId(URI);
	}

	public ResourceAction getResourceAction() {
		return resourceAction;
	}

	public void setResourceAction(ResourceAction resourceAction) {
		this.resourceAction = resourceAction;
	}

	public Object getResource() {
		return resource;
	}

	public void setResource(Object resource) {
		this.resource = resource;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String requestId() {
		return this.requestId;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public void setParameters(Object[] value) {
		this.parameters = value;
	}

	public Object[] getParameters() throws InstantiationException,
			IllegalAccessException, ParseException {
		throw new UnsupportedOperationException();
		/*
		if (this.parameters == null) {
			if (stackRequestElement.getParameters() == null) {
				this.parameters = stackRequestElement.getAction()
						.getMethodForm().getParameterValues(this.resource);
			} else {
				this.parameters = stackRequestElement
						.getAction()
						.getMethodForm()
						.getParameterValues(this.resource,
								stackRequestElement.getParameters());
			}
		}

		return this.parameters;
		*/
	}

	public void setResult(Object value) {
		this.result = value;
	}

	public Object getResult() {
		return this.result;
	}

	public void setRequestInstrument(RequestInstrument requestInstrument) {
		this.requestInstrument = requestInstrument;
	}

	public RequestInstrument getRequestInstrument() {
		return this.requestInstrument;
	}

	public void setStackRequestElement(StackRequestElement stackRequestElement) {
		this.stackRequestElement = stackRequestElement;
	}

	public StackRequestElement getStackRequestElement() {
		return this.stackRequestElement;
	}

	public DataType getRequestType() {
		// TODO Auto-generated method stub
		return null;
	}

	public MvcRequest getRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	public MvcResponse getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

}
