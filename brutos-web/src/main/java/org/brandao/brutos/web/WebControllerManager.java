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
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManagerImp;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.mapping.ActionListener;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.mapping.WebController;
import org.brandao.brutos.web.util.WebUtil;

/**
 * 
 * @author Brandao
 */
public class WebControllerManager extends ControllerManagerImp{
 
	private ConfigurableWebApplicationContext webApplicationContext;
	
    public WebControllerManager(){
        super();
    }
    
	public void setApplicationContext(
			ConfigurableApplicationContext applicationContext) {
		super.setApplicationContext(applicationContext);
		this.webApplicationContext = (ConfigurableWebApplicationContext) applicationContext;
	}
    
    public ControllerBuilder addController(String id, String view, 
            boolean resolvedView, DispatcherType dispatcherType,
            String name, Class<?> classType, String actionId ){
            return addController( id, view, resolvedView,
                    dispatcherType, name, classType, actionId, 
                    null);
    }

	public ControllerBuilder addController(String id, String view,
			DispatcherType dispatcherType, boolean resolvedView, String name,
			Class<?> classType, String actionId, ActionType actionType) {
		return this.addController(id, null, view, dispatcherType, 
				resolvedView, name, classType, actionId, actionType);
	}
    
	public ControllerBuilder addController(String id, 
			RequestMethodType requestMethodType, String view, 
			DispatcherType dispatcherType, boolean resolvedView, String name,
			Class<?> classType, String actionId, ActionType actionType) {

		id = StringUtil.adjust(id);
		
        id = StringUtil.isEmpty(id) && (actionType.isDelegate() || actionType.isComposite())?
    		actionType.getControllerID(classType.getSimpleName().toLowerCase()) :
    		id;
		
		view     = StringUtil.adjust(view);
		actionId = StringUtil.adjust(actionId);
		name     = StringUtil.adjust(name);
		
		actionId = actionId == null?
				this.webApplicationContext.getActionParameterName() :
				actionId;

		requestMethodType = requestMethodType == null?
				this.webApplicationContext.getRequestMethod() :
				requestMethodType;
				
		dispatcherType = dispatcherType == null? 
				this.webApplicationContext.getDispatcherType() :
					dispatcherType;

		actionType = actionType == null? 
				this.webApplicationContext.getActionType() :
					actionType;
		
		if (classType == null){
			throw new MappingException("invalid class type: "
					+ classType);
		}

		if (actionType == null) {
			throw new MappingException("action type is required");
		}
		
        if(resolvedView && view != null)
            WebUtil.checkURI(view, true);
		
    	if(!actionType.isValidControllerId(id)){
    		throw new MappingException("invalid controller id: " + id);
    	}
        
    	WebController controller = new WebController(this.webApplicationContext);
		controller.setClassType(classType);
		controller.setId(id);
		
		// Action
		ActionListener ac = new ActionListener();
		ac.setPreAction(this.getMethodAction("preAction", controller.getClassType()));
		ac.setPostAction(this.getMethodAction("postAction", controller.getClassType()));
		controller.setActionListener(ac);
		controller.setRequestMethod(requestMethodType);
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
