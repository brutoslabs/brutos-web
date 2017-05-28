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

package org.brandao.brutos;

import org.brandao.brutos.interceptor.ConfigurableInterceptorHandler;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ThrowableSafeData;

/**
 * 
 * @author Brandao
 */
public class StackRequestElementImp implements StackRequestElement {

	private Throwable objectThrow;
	
	private ThrowableSafeData throwableSafeData;
	
	private Object[] parameters;
	
	private Controller controller;
	
	private ResourceAction action;
	
	private Object resultAction;
	
	private ConfigurableInterceptorHandler handler;
	
	private Object resource;
	
	private String view;
	
	private DispatcherType dispatcherType;
	
	private MutableMvcRequest request;
	
	private MutableMvcResponse response;
		
	private StackRequestElement nextStackRequestElement;
	
	private StackRequestElement previousStackRequestElement;
	
	public Throwable getObjectThrow() {
		return objectThrow;
	}

	public void setObjectThrow(Throwable objectThrow) {
		this.objectThrow = objectThrow;
	}

	public ThrowableSafeData getThrowableSafeData() {
		return throwableSafeData;
	}

	public void setThrowableSafeData(ThrowableSafeData throwableSafeData) {
		this.throwableSafeData = throwableSafeData;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public ResourceAction getAction() {
		return action;
	}

	public void setAction(ResourceAction action) {
		this.action = action;
	}

	public Object getResultAction() {
		return resultAction;
	}

	public void setResultAction(Object resultAction) {
		this.resultAction = resultAction;
	}

	public ConfigurableInterceptorHandler getHandler() {
		return handler;
	}

	public void setHandler(ConfigurableInterceptorHandler handler) {
		this.handler = handler;
	}

	public Object getResource() {
		return resource;
	}

	public void setResource(Object resource) {
		this.resource = resource;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public DispatcherType getDispatcherType() {
		return dispatcherType;
	}

	public void setDispatcherType(DispatcherType dispatcherType) {
		this.dispatcherType = dispatcherType;
	}

	public MutableMvcRequest getRequest() {
		return this.request;
	}

	public MutableMvcResponse getResponse() {
		return this.response;
	}

	public void setRequest(MutableMvcRequest value) {
		this.request = value;
	}

	public void setResponse(MutableMvcResponse value) {
		this.response = value;
	}

	public StackRequestElement getNextStackRequestElement() {
		return this.nextStackRequestElement;
	}

	public StackRequestElement getPreviousStackRequestElement() {
		return this.previousStackRequestElement;
	}

	public void setNextStackRequestElement(StackRequestElement value) {
		this.nextStackRequestElement = value;
	}

	public void setPreviousStackRequestElement(StackRequestElement value) {
		this.previousStackRequestElement = value;
	}

}
