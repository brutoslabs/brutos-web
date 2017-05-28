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

import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ThrowableSafeData;

/**
 * 
 * @author Brandao
 */
public interface StackRequestElement {

	Throwable getObjectThrow();

	ThrowableSafeData getThrowableSafeData();

	Object[] getParameters();

	Controller getController();

	ResourceAction getAction();

	Object getResultAction();

	Object getResource();

	MutableMvcRequest getRequest();

	MutableMvcResponse getResponse();

	String getView();

	DispatcherType getDispatcherType();

	StackRequestElement getNextStackRequestElement();
	
	StackRequestElement getPreviousStackRequestElement();
	
	void setObjectThrow(Throwable objectThrow);

	void setThrowableSafeData(ThrowableSafeData throwableSafeData);

	void setParameters(Object[] parameters);

	void setController(Controller controller);

	void setAction(ResourceAction action);

	void setResultAction(Object resultAction);

	void setRequest(MutableMvcRequest value);

	void setResponse(MutableMvcResponse value);
	
	void setResource(Object resource);

	void setView(String view);

	void setDispatcherType(DispatcherType dispatcherType);

	void setNextStackRequestElement(StackRequestElement value);
	
	void setPreviousStackRequestElement(StackRequestElement value);
	
}
