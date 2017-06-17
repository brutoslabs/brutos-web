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

import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.RequestMethodType;

/**
 * 
 * @author Brandao
 *
 */
public class WebController extends Controller{

	private int responseStatus;
	
	private RequestMethodType requestMethod;
	
	public WebController(ConfigurableApplicationContext context) {
		super(context);
		super.setRequestTypes(new MediaTypeMap());
		super.setResponseTypes(new MediaTypeMap());
	}

	public void addAction(ActionID id, Action method) {
		WebControllerID wcid = (WebControllerID)method.getController().getId();
		WebActionID wid = (WebActionID)id;
		super.getActions().put(wid, method);
		super.getContext().getActionResolver()
			.registry(wcid, method.getController(), wid, method);
	}

	public void removeAction(ActionID id) {
		WebActionID wid = (WebActionID)id;
		Action method   = this.getActions().get(id);
		
		this.getActions().remove(id);
		
		if(method != null){
			this.getContext().getActionResolver()
			.remove(method.getController().getId(), method.getController(), wid, method);
		}
	}
	
	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	public RequestMethodType getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(RequestMethodType requestMethod) {
		this.requestMethod = requestMethod;
	}
	
}
