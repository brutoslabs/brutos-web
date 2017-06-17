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

package org.brandao.brutos.annotation.configuration.web;

import java.util.HashSet;
import java.util.Set;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.DataType;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.configuration.ActionAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ActionEntry;
import org.brandao.brutos.annotation.configuration.ThrowableEntry;
import org.brandao.brutos.annotation.web.ResponseError;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebActionBuilder;
import org.brandao.brutos.web.WebControllerBuilder;

@Stereotype(
	target = Action.class, 
	executeAfter = Controller.class,
	minorVersion = 1
)
public class WebActionAnnotationConfig 
	extends ActionAnnotationConfig{
	
	protected Object innerApplyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		//vars
		ActionEntry actionEntry                = (ActionEntry) source;
		WebActionConfig actionConfig           = new WebActionConfig(actionEntry);
		WebControllerBuilder controllerBuilder = (WebControllerBuilder) builder;
		String actionID                        = actionConfig.getActionId();
		RequestMethodType requestMethodType    = actionConfig.getRequestMethodType();
		String result                          = actionConfig.getResultActionName();
		String view                            = actionConfig.getActionView();
		boolean resultRendered                 = actionConfig.isResultRenderable();
		boolean rendered                       = actionConfig.isRenderable();
		boolean resolved                       = actionConfig.isResolvedView();
		String executor                        = actionConfig.getActionExecutor();
		DataType[] requestTypes                = actionConfig.getRequestTypes();
		DataType[] responseTypes               = actionConfig.getResponseTypes();
		int responseStatus                     = actionConfig.getResponseStatus();
		DispatcherType dispatcher              = actionConfig.getDispatcherType();

		//validtion
		if (!StringUtil.isEmpty(view) && StringUtil.isEmpty(executor)
				&& !rendered){
			throw new MappingException(
					"view must be rendered in abstract actions: " + actionID);
		}

		//registry
		WebActionBuilder actionBuilder = 
				(WebActionBuilder)
				controllerBuilder.addAction(
						actionID, requestMethodType, 
					result, resultRendered, view, dispatcher, resolved, executor);

		if(requestTypes != null){
			for(DataType type: requestTypes){
				actionBuilder.addRequestType(type);
			}
		}

		if(responseTypes != null){
			for(DataType type: responseTypes){
				actionBuilder.addResponseType(type);
			}
		}
		
		if(responseStatus > 0){
			actionBuilder.setResponseStatus(responseStatus);
		}
		
		String[] actionAlias = actionConfig.getAliasName();
		RequestMethodType[] requestMethodTypeAlias = actionConfig.getRequestMethodTypeAlias();
		
		if(requestMethodTypeAlias != null){
			for(RequestMethodType requestMethod: requestMethodTypeAlias){
				actionBuilder.addAlias(StringUtil.adjust(actionID), requestMethod);
			}
		}
		
		if(actionAlias != null){
			for(String actionName: actionAlias){
				
				if(requestMethodTypeAlias == null){
					actionBuilder.addAlias(actionName);
				}
				else{
					for(RequestMethodType requestMethod: requestMethodTypeAlias){
						actionBuilder.addAlias(StringUtil.adjust(actionName), requestMethod);
					}
				}
			}
		}
		
		throwsSafe(actionBuilder, actionEntry, componentRegistry);

		addParameters(actionBuilder, actionEntry, componentRegistry);

		addResultAction(actionBuilder, actionEntry.getResultAction(), componentRegistry);
		
		return actionBuilder;
	}
	
	@SuppressWarnings("unchecked")
	protected void throwsSafe(ActionBuilder builder, ActionEntry method,
			ComponentRegistry componentRegistry) {

		Set<ThrowableEntry> list = new HashSet<ThrowableEntry>();

		ResponseErrors throwSafeList = 
				method.getAnnotation(ResponseErrors.class);

		throwSafeList =
				throwSafeList == null?
					method.getControllerClass().getAnnotation(ResponseErrors.class) :
					method.getAnnotation(ResponseErrors.class);
		
		ResponseError throwSafe = method.getAnnotation(ResponseError.class);
		
		if (throwSafeList != null && throwSafeList.exceptions().length != 0) {
			list.addAll(
				WebAnnotationUtil.toList(
					WebAnnotationUtil.toList(throwSafeList)));
		}

		if (throwSafe != null)
			list.add(WebAnnotationUtil.toEntry(throwSafe));

		Class<?>[] exs = method.getExceptionTypes();

		if (exs != null) {
			for (Class<?> ex : exs) {
				ThrowableEntry entry = 
					new WebThrowableEntry(
						throwSafeList, 
						(Class<? extends Throwable>) ex);

				if (!list.contains(entry)) {
					list.add(entry);
				}
			}
		}

		if(throwSafeList != null){
			ThrowableEntry entry = 
					new WebThrowableEntry(
						throwSafeList, 
						Throwable.class);
			
			if (!list.contains(entry)) {
				list.add(entry);
			}
			
		}
		
		for (ThrowableEntry entry : list){
			super.applyInternalConfiguration(entry, builder, componentRegistry);
		}
		
	}
	
}
