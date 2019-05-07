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

package org.brandao.brutos.web;

import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebActionID;
import org.brandao.brutos.web.mapping.WebController;

/**
 * 
 * @author Brandao
 *
 */
public class ParamActionTypeResolver 
	extends AbstractWebActionTypeResolver{

	public ResourceAction getResourceAction(Controller controller,
			MutableMvcRequest request) {

    	String actionId                     = String.valueOf(request.getParameter(controller.getActionId()));
    	WebMvcRequest webRequest            = (WebMvcRequest)request;
    	RequestMethodType requestMethodType = webRequest.getRequestMethodType();
    	WebActionID id                      = new WebActionID(actionId, requestMethodType);
        WebAction method                    = (WebAction) controller.getAction(id);
        
        if(method != null){
        	return new WebResourceAction(
        			((WebController)method.getController()).getRequestMethod(),
        			(WebController)method.getController(), (WebAction)method);
        }
        else
        if(controller.getDefaultAction() != null){
        	method = (WebAction)controller.getAction(controller.getDefaultAction());
    		return new WebResourceAction(
    				method.getRequestMethod(),
    				(WebController)controller, 
    				(WebAction)controller.getAction(controller.getDefaultAction()));
        }
        else{
        	return null;
        }
	}
	
}
