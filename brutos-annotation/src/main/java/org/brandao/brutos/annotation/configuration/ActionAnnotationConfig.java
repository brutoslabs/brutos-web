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

import org.brandao.brutos.*;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.*;
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

		ActionEntry method                  = (ActionEntry) source;
		ControllerBuilder controllerBuilder = (ControllerBuilder) builder;
		
		Action action         = (Action) method.getAnnotation(Action.class);
		View viewAnnotation   = method.getAnnotation(View.class);
		ResultView resultView = method.getAnnotation(ResultView.class);

		
		String id               = action == null? null : action.value()[0];
		Result resultAnnotation = method.getAnnotation(Result.class);
		String result           = resultAnnotation == null ? null : resultAnnotation.value();

		org.brandao.brutos.DispatcherType dispatcher = 
				viewAnnotation == null? 
					null : 
					org.brandao.brutos.DispatcherType.valueOf(StringUtil.adjust(viewAnnotation.dispatcher()));

		boolean resultRendered = resultView == null ? false : resultView.rendered();
		boolean rendered = viewAnnotation == null ? true : viewAnnotation.rendered();
		boolean resolved = viewAnnotation == null ? false : viewAnnotation.resolved();
		resolved = rendered ? resolved : true;

		String executor = method.isAbstractAction() ? null : method.getName();
		String view = getView(method, viewAnnotation, componentRegistry);

		if (!StringUtil.isEmpty(view) && StringUtil.isEmpty(executor)
				&& !rendered){
			throw new MappingException(
					"view must be rendered in abstract actions: " + id);
		}

		if (method.getReturnType() == void.class) {
			if (resultAnnotation != null || resultView != null)
				throw new MappingException("the action not return any value: "
						+ method.getName());
		}

		ActionBuilder actionBuilder = this.addAction(method, controllerBuilder, 
				id, result, resultRendered, view, resolved, dispatcher, executor);

		if (action != null && action.value().length > 1) {
			String[] ids = action.value();
			for (int i = 1; i < ids.length; i++) {
				if (!StringUtil.isEmpty(ids[i]))
					this.addAlias(method, actionBuilder, StringUtil.adjust(ids[i]));
				else {
					throw new BrutosException("invalid action id: "
							+ method.getControllerClass().getName() + "."
							+ method.getName());
				}
			}
		}

		throwsSafe(actionBuilder, method, componentRegistry);

		addParameters(actionBuilder, method, componentRegistry);

		addResultAction(actionBuilder, method.getResultAction(), componentRegistry);
		
		return actionBuilder;
	}

	protected void addResultAction(ActionBuilder builder, 
			ResultActionEntry method, ComponentRegistry componentRegistry){
		
		if(method != null && method.getBeanType() != void.class){
			super.applyInternalConfiguration(method,
					builder, componentRegistry);
		}
		
	}
	
	protected ActionBuilder addAction(ActionEntry actionEntry, 
			ControllerBuilder controllerBuilder, String id, String result,
			boolean resultRendered, String view, boolean resolved,
			DispatcherType dispatcher, String executor){
		return controllerBuilder.addAction(id, result,
				resultRendered, view, resolved, dispatcher, executor);
	}

	protected ActionBuilder addAlias(ActionEntry actionEntry, 
			ActionBuilder actionBuilder, String id){
		return actionBuilder.addAlias(id);
	}
	
	/*
	protected org.brandao.brutos.DispatcherType getDispatcherType(
			ActionEntry actionEntry, View viewAnnotation) {

		if (actionEntry.isAbstractAction()) {
			return org.brandao.brutos.DispatcherType.valueOf(actionEntry
					.getDispatcher());
		} else if (viewAnnotation != null
				&& !StringUtil.isEmpty(viewAnnotation.dispatcher())) {
			return org.brandao.brutos.DispatcherType.valueOf(viewAnnotation
					.dispatcher());
		} else
			return BrutosConstants.DEFAULT_DISPATCHERTYPE;

	}
    */
	
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

	private void addParameters(ActionBuilder builder, ActionEntry method,
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
