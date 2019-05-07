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
public class HierarchyActionTypeResolver 
	extends AbstractWebActionTypeResolver{

	public ResourceAction getResourceAction(Controller controller,
			MutableMvcRequest request) {
		WebActionID actionID = (WebActionID) controller.getDefaultAction();
		
		if(actionID == null){
			return new WebResourceAction(
					((WebController)controller).getRequestMethod(), 
					(WebController) controller, null);
		}
		else{
			WebAction action = (WebAction)controller.getAction(actionID);
			return new WebResourceAction(
					action.getRequestMethod(), 
					(WebController) controller,  action);
		}
	}

    
}
