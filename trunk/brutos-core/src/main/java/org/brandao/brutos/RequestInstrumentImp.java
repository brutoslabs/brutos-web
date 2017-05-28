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

/**
 * 
 * @author Brandao
 */
public class RequestInstrumentImp implements RequestInstrument, StackRequest {

	private ApplicationContext context;
	
	private boolean hasViewProcessed;
	
	private ObjectFactory objectFactory;
	
	private RenderView renderView;
	
	private StackRequestElement firstStackRequestElement;
	
	private StackRequestElement currentStackRequestElement;
	
	public RequestInstrumentImp(ApplicationContext context,
			ObjectFactory objectFactory, RenderView renderView) {
		this.context                    = context;
		this.hasViewProcessed           = false;
		this.objectFactory              = objectFactory;
		this.renderView                 = renderView;
		this.firstStackRequestElement   = null;
		this.currentStackRequestElement = null;
	}

	public void push(StackRequestElement value) {
		if(this.firstStackRequestElement == null){
			value.setNextStackRequestElement(null);
			value.setPreviousStackRequestElement(null);
			this.firstStackRequestElement   = value;
			this.currentStackRequestElement = value;
		}
		else{
			this.currentStackRequestElement.setNextStackRequestElement(value);
			value.setPreviousStackRequestElement(this.currentStackRequestElement);
			value.setNextStackRequestElement(null);
			this.currentStackRequestElement = value;
		}
	}

	public StackRequestElement getCurrent() {
		return this.currentStackRequestElement;
	}

	public StackRequestElement getFirst() {
		return this.currentStackRequestElement;
	}
	
	public StackRequestElement getNext(StackRequestElement stackrequestElement) {
		return stackrequestElement.getNextStackRequestElement();
	}

	public StackRequest getStackRequest(){
		return this;
	}
	
	public boolean isEmpty() {
		return this.firstStackRequestElement == null;
	}

	public void pop() {
		
		if(this.currentStackRequestElement == null){
			return;
		}
		
		this.currentStackRequestElement = 
				this.currentStackRequestElement.getPreviousStackRequestElement();
		
		if(this.currentStackRequestElement != null){
			this.currentStackRequestElement.setNextStackRequestElement(null);
		}
		else{
			this.firstStackRequestElement = null;
		}
		
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(AbstractApplicationContext context) {
		this.context = context;
	}

	public boolean isHasViewProcessed() {
		return hasViewProcessed;
	}

	public void setHasViewProcessed(boolean hasViewProcessed) {
		this.hasViewProcessed = hasViewProcessed;
	}

	public ObjectFactory getObjectFactory() {
		return objectFactory;
	}

	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	public RenderView getRenderView() {
		return this.renderView;
	}

	public void setRenderView(RenderView renderView) {
		this.renderView = renderView;
	}

}
