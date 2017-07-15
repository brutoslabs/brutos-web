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

package org.brandao.brutos.annotation.configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DataType;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.ParametersBuilder;
import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.DefaultThrowSafe;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.ThrowSafe;
import org.brandao.brutos.annotation.ThrowSafeList;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;

/**
 *
 * @author Brandao
 */
@Stereotype(target = Action.class, executeAfter = Controller.class)
public class ActionAnnotationConfig extends AbstractAnnotationConfig {

	public Object applyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		try {
			return innerApplyConfiguration(source, builder, componentRegistry);
		} catch (Exception e) {
			throw new BrutosException("can't create action: "
					+ ((ActionEntry) source).getName(), e);
		}

	}

	protected Object innerApplyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		//vars
		ActionEntry actionEntry                = (ActionEntry) source;
		ActionConfig actionConfig              = new ActionConfig(actionEntry);
		ControllerBuilder controllerBuilder    = (ControllerBuilder)builder;
		String actionID                        = actionConfig.getActionId();
		String result                          = actionConfig.getResultActionName();
		String view                            = actionConfig.getActionView();
		boolean resultRendered                 = actionConfig.isResultRenderable();
		boolean rendered                       = actionConfig.isRenderable();
		boolean resolved                       = actionConfig.isResolvedView();
		String executor                        = actionConfig.getActionExecutor();
		DataType[] requestTypes                = actionConfig.getRequestTypes();
		DataType[] responseTypes               = actionConfig.getResponseTypes();
		DispatcherType dispatcher              = actionConfig.getDispatcherType();

		//validtion
		if (!StringUtil.isEmpty(view) && StringUtil.isEmpty(executor)
				&& !rendered){
			throw new MappingException(
					"view must be rendered in abstract actions: " + actionID);
		}

		//registry
		ActionBuilder actionBuilder = 
				controllerBuilder.addAction(
						actionID, 
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
		
		String[] actionAlias = actionConfig.getAliasName();
		
		for(String actionName: actionAlias){
			actionBuilder.addAlias(actionName);
		}

		throwsSafe(actionBuilder, actionEntry, componentRegistry);

		addParameters(actionBuilder, actionEntry, componentRegistry);

		addResultAction(actionBuilder, actionEntry.getResultAction(), componentRegistry);
		
		return actionBuilder;
	}

	protected void addResultAction(ActionBuilder builder, 
			ResultActionEntry method, ComponentRegistry componentRegistry){
		
		if(method != null && method.getType() != void.class){
			super.applyInternalConfiguration(method,
					builder, componentRegistry);
		}
		
	}
	
	protected String getView(ActionEntry actionEntry, View viewAnnotation,
			ComponentRegistry componentRegistry) {
		String view = viewAnnotation == null
				|| StringUtil.isEmpty(viewAnnotation.value()) ? null
				: StringUtil.adjust(viewAnnotation.value());

		return view;
	}

	protected String createActionView(ActionBuilder action,
			ComponentRegistry componentRegistry, String view) {

		return applicationContext.getViewResolver().getView(
				action.getControllerBuilder(), action, null, view);
	}

	protected void addParameters(ActionBuilder builder, ActionEntry method,
			ComponentRegistry componentRegistry) {

		Type[] genericTypes = method.getGenericParameterTypes();
		Class<?>[] types = method.getParameterTypes();
		Annotation[][] annotations = method.getParameterAnnotations();

		if (types == null)
			return;

		ParametersBuilder parametersBuilder = builder.buildParameters();

		for (int i = 0; i < types.length; i++) {
			ActionParamEntry actionParamEntry = new ActionParamEntry(null,
					types != null ? types[i] : null,
					genericTypes != null ? genericTypes[i] : null,
					annotations != null ? annotations[i] : null, i);

			super.applyInternalConfiguration(actionParamEntry,
					parametersBuilder, componentRegistry);
		}
	}

	@SuppressWarnings("unchecked")
	protected void throwsSafe(ActionBuilder builder, ActionEntry method,
			ComponentRegistry componentRegistry) {

		List<ThrowableEntry> list = new ArrayList<ThrowableEntry>();
		ThrowSafeList throwSafeList = method.getAnnotation(ThrowSafeList.class);
		ThrowSafe throwSafe = method.getAnnotation(ThrowSafe.class);
		DefaultThrowSafe defualtThrowSafe = 
				method.getAnnotation(DefaultThrowSafe.class);

		if (throwSafeList != null) {
			if (throwSafeList.value().length == 0)
				throw new MappingException("exception not informed");

			list.addAll(AnnotationUtil.toList(AnnotationUtil
					.toList(throwSafeList)));
		}

		if (throwSafe != null)
			list.add(AnnotationUtil.toEntry(throwSafe));

		Class<?>[] exs = method.getExceptionTypes();

		if (exs != null) {
			for (Class<?> ex : exs) {
				ThrowableEntry entry = defualtThrowSafe == null ? new ThrowableEntry(
						(Class<? extends Throwable>) ex) : new ThrowableEntry(
						defualtThrowSafe, (Class<? extends Throwable>) ex);

				if (!list.contains(entry)) {
					list.add(entry);
				}
			}
		}

		if(defualtThrowSafe != null){
			ThrowableEntry entry = 
					new ThrowableEntry(defualtThrowSafe, Throwable.class);

			if (!list.contains(entry)) {
				list.add(entry);
			}
		}
		
		for (ThrowableEntry entry : list){
			super.applyInternalConfiguration(entry, builder, componentRegistry);
		}
			

	}

	public boolean isApplicable(Object source) {
		return source instanceof ActionEntry
				&& (((ActionEntry) source).isAnnotationPresent(Action.class)
						|| ((ActionEntry) source).getName().endsWith("Action") || ((ActionEntry) source)
							.isAbstractAction())
				&& !((ActionEntry) source).isAnnotationPresent(Transient.class);
	}

}
