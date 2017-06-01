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

import org.brandao.brutos.ActionType;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManagerImp;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.mapping.ActionListener;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.mapping.WebController;
import org.brandao.brutos.web.util.WebUtil;

/**
 * 
 * @author Brandao
 */
public class WebControllerManager extends ControllerManagerImp{
 
    public WebControllerManager(){
        super();
    }
    
	protected Controller creatControllerInstance(){
		return new WebController(this.applicationContext);
	}
    
    public ControllerBuilder addController(String id, String view, 
            boolean resolvedView, DispatcherType dispatcherType,
            String name, Class<?> classType, String actionId ){
            return addController( id, view, resolvedView,
                    dispatcherType, name, classType, actionId, 
                    WebActionType.HIERARCHY);
    }
    
	public ControllerBuilder addController(String id, String view,
			DispatcherType dispatcherType, boolean resolvedView, String name,
			Class<?> classType, String actionId, ActionType actionType) {

		id       = StringUtil.adjust(id);
		view     = StringUtil.adjust(view);
		actionId = StringUtil.adjust(actionId);
		name     = StringUtil.adjust(name);
		actionType = actionType == null? WebActionType.HIERARCHY : WebActionType.valueOf(actionType.id());
		
		if (classType == null){
			throw new MappingException("invalid class type: "
					+ classType);
		}

		if (actionType == null) {
			throw new MappingException("action type is required");
		}
		
		if (StringUtil.isEmpty(actionId))
			actionId = BrutosConstants.DEFAULT_ACTION_ID;

        if(resolvedView && view != null)
            WebUtil.checkURI(view, true);
		
    	if(!actionType.isValidControllerId(id))
    		throw new MappingException("invalid controller id: " + id);

		Controller controller = new WebController(this.applicationContext);
		controller.setClassType(classType);
		controller.setId(id);
		
		// Action
		ActionListener ac = new ActionListener();
		ac.setPreAction(this.getMethodAction("preAction", controller.getClassType()));
		ac.setPostAction(this.getMethodAction("postAction", controller.getClassType()));
		controller.setActionListener(ac);

		controller.setDefaultInterceptorList(interceptorManager
				.getDefaultInterceptors());

		this.current = new WebControllerBuilder(controller, this,
				interceptorManager, validatorFactory, applicationContext,
				internalUpdate);

		this.current
			.setName(name)
			.setView(view, resolvedView)
			.setActionId(actionId)
			.setDispatcherType(dispatcherType)
				.setActionType(actionType);

		addController(controller.getId(), controller);

		this.getLogger().info(
				String.format("added controller %s",
						new Object[] { classType.getSimpleName() }));
		
		return this.getCurrent();
	}    
}
