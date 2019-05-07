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

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.RequestMethodType;

public class RequestMappingEntry{
	
	private RequestMethodType requestMethodType;
	
	private Controller controller;
	
	private Action action;
	
	public RequestMappingEntry(RequestMethodType requestMethodType, 
			Controller controller, Action action) {
		this.controller = controller;
		this.action = action;
		this.requestMethodType = requestMethodType;
	}

	public Controller getController() {
		return controller;
	}

	public Action getAction() {
		return action;
	}

	public RequestMethodType getRequestMethodType() {
		return requestMethodType;
	}

}
