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

package org.brandao.brutos.web.mapping;

import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.web.RequestMethodType;

public class WebAction extends Action{

	private RequestMethodType requestMethod;

	private int responseStatus;
	
	private Map<Class<?>, Integer> responseErrors;
	
	public WebAction(){
		super();
		super.setRequestTypes(new MediaTypeMap());
		super.setResponseTypes(new MediaTypeMap());
		this.responseErrors = new HashMap<Class<?>, Integer>();
	}
	
	public RequestMethodType getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(RequestMethodType requestMethod) {
		this.requestMethod = requestMethod;
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Map<Class<?>, Integer> getResponseErrors() {
		return responseErrors;
	}

	public void setResponseErrors(Map<Class<?>, Integer> responseErrors) {
		this.responseErrors = responseErrors;
	}
	
}
