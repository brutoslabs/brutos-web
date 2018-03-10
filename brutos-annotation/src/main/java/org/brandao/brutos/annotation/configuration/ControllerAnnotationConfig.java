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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.brandao.brutos.*;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotationImp;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.bean.BeanProperty;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;

/**
 *
 * @author Brandao
 */
@Stereotype(target = Controller.class)
public class ControllerAnnotationConfig extends AbstractAnnotationConfig {

	public Object applyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		try {
			return applyConfiguration0(source, builder, componentRegistry);
		} catch (Exception e) {
			throw new BrutosException("can't create controller: "
					+ ((Class<?>) source).getName(), e);
		}

	}

	public Object applyConfiguration0(Object arg0, Object arg1,
			ComponentRegistry componentRegistry) {

		Class<?> source                 = (Class<?>) arg0;
		Controller annotationController = (Controller) source.getAnnotation(Controller.class);
		View viewAnnotation             = (View) source.getAnnotation(View.class);
		ActionStrategy strategy         = (ActionStrategy)source.getAnnotation(ActionStrategy.class);
		String name                     = null;
		String actionID                 = null;
		String defaultActionName        = null;
		ActionType actionType           = strategy == null ? null : ActionType.valueOf(strategy.value());
		boolean resolved                = viewAnnotation == null ? false : viewAnnotation.resolved();
		boolean rendered                = viewAnnotation == null ? true : viewAnnotation.rendered();
		
		String controllerID                 = 
				annotationController == null || annotationController.value().length == 0? 
					null : 
					annotationController.value()[0];

		org.brandao.brutos.DispatcherType dispatcher = 
			viewAnnotation == null? 
				null : 
				org.brandao.brutos.DispatcherType.valueOf(StringUtil.adjust(viewAnnotation.dispatcher()));
		
		if (annotationController != null) {
			name = annotationController.name();
			actionID = annotationController.actionId();
			defaultActionName = annotationController.defaultActionName();
		}

		ControllerBuilder builder = 
			componentRegistry
			.registerController(
					controllerID, 
					rendered ? getView((View) source.getAnnotation(View.class), componentRegistry) : null, 
					rendered ? resolved : true,	
					dispatcher, 
					name, 
					source, 
					actionID, 
					actionType);
		
		if (annotationController != null && annotationController.value().length > 1) {
			String[] ids = annotationController.value();
			for (int i = 1; i < ids.length; i++) {
				if (!StringUtil.isEmpty(ids[i]))
					this.addAlias(builder, StringUtil.adjust(ids[i]));
				else
					throw new BrutosException("invalid controller id: "
							+ source.getName());
			}
		}

		super.applyInternalConfiguration(source, builder, componentRegistry);

		importBeans(builder, componentRegistry, builder.getClassType());
		
		throwsSafe(builder, source, componentRegistry);
		
		addProperties(builder, componentRegistry, source);
		
		addActions(builder, componentRegistry, source);

		if (!StringUtil.isEmpty(defaultActionName)){
			builder.setDefaultAction(defaultActionName);
		}
		
		return builder;
	}

	protected ControllerBuilder registerController(
			ComponentRegistry componentRegistry, String id, String view,
			boolean resolvedView, DispatcherType dispatcherType, String name,
			Class<?> classType, String actionId, ActionType actionType){
		
		return componentRegistry.registerController(
				id, view, resolvedView,
				dispatcherType, name, classType, actionId, actionType);		
	}
	
	protected ControllerBuilder addAlias(ControllerBuilder builder, String id) {
		return builder.addAlias(id);
	}
	
	protected void throwsSafe(ControllerBuilder builder, Class<?> clazz,
			ComponentRegistry componentRegistry) {

		List<ThrowableEntry> list = new ArrayList<ThrowableEntry>();
		ThrowSafeList throwSafeList = (ThrowSafeList) clazz
				.getAnnotation(ThrowSafeList.class);
		ThrowSafe throwSafe = (ThrowSafe) clazz.getAnnotation(ThrowSafe.class);

		DefaultThrowSafe defualtThrowSafe = 
				clazz.getAnnotation(DefaultThrowSafe.class);
		
		if (throwSafeList != null) {
			if (throwSafeList.value().length == 0)
				throw new MappingException("exception not informed");

			list.addAll(AnnotationUtil.toList(AnnotationUtil
					.toList(throwSafeList)));
		}

		if(defualtThrowSafe != null){
			ThrowableEntry entry = 
					new ThrowableEntry(defualtThrowSafe, Throwable.class);

			if (!list.contains(entry)) {
				list.add(entry);
			}
			
		}
		
		if (throwSafe != null)
			list.add(AnnotationUtil.toEntry(throwSafe));

		for (ThrowableEntry entry : list){
			super.applyInternalConfiguration(entry, builder, componentRegistry);
		}

	}

	protected String getView(View viewAnnotation, /* ControllerBuilder controller, */
			ComponentRegistry componentRegistry) {

		// boolean rendered = viewAnnotation == null? true :
		// viewAnnotation.rendered();
		// boolean resolved = viewAnnotation == null? false :
		// viewAnnotation.resolved();

		String view = viewAnnotation == null
				|| StringUtil.isEmpty(viewAnnotation.value()) ? null
				: StringUtil.adjust(viewAnnotation.value());

		return view;
		/*
		 * if(rendered){ return view; if(resolved) return view; else return
		 * createControllerView(controller, componentRegistry, view); } else
		 * return null;
		 */
	}

	protected String createControllerView(ControllerBuilder controller,
			ComponentRegistry componentRegistry, String view) {

		return applicationContext.getViewResolver().getView(controller, null,
				null, view);
	}

	protected void addProperties(ControllerBuilder controllerBuilder,
			ComponentRegistry componentRegistry, Class<?> clazz) {

		BeanInstance instance = new BeanInstance(null, clazz);
		List<BeanProperty> props = instance.getProperties();
		for(BeanProperty prop: props){
			BeanPropertyAnnotationImp annotationProp = new BeanPropertyAnnotationImp(
					prop);
			
			super.applyInternalConfiguration(annotationProp, controllerBuilder,
					componentRegistry);
		}
	}

	@SuppressWarnings("deprecation")
	private void addOldAbstractActions(ControllerBuilder controllerBuilder,
			ComponentRegistry componentRegistry, Class<?> clazz,
			List<ActionEntry> actionList) {

		AbstractActions abstractActions = (AbstractActions) clazz
				.getAnnotation(AbstractActions.class);

		AbstractAction abstractAction = (AbstractAction) clazz
				.getAnnotation(AbstractAction.class);

		if (abstractActions != null)
			throw new BrutosException(
					"@AbstractActions has been deprecated. Use @Actions");

		if (abstractAction != null)
			throw new BrutosException(
					"@AbstractAction has been deprecated. Use @Action");

	}

	protected void addActions(ControllerBuilder controllerBuilder,
			ComponentRegistry componentRegistry, Class<?> clazz,
			List<ActionEntry> actionList) {

		Actions actions = (Actions) clazz.getAnnotation(Actions.class);

		Action action = (Action) clazz.getAnnotation(Action.class);

		List<Action> all = new ArrayList<Action>();

		if (actions != null)
			all.addAll(Arrays.asList(actions.value()));

		if (action != null)
			all.add(action);

		List<Annotation> commonsAnnotations = new ArrayList<Annotation>();
				
		for(Annotation a: clazz.getAnnotations()){
			commonsAnnotations.add(a);
		}
		
		for (Action act : all) {
			if (act.value().length == 0)
				throw new BrutosException("action id cannot be empty");

			List<Annotation> annotations = new ArrayList<Annotation>();
			annotations.addAll(commonsAnnotations);
			annotations.add(act);
			annotations.add(act.view());
			
			ActionEntry entry = new ActionEntry(null,
					annotations.toArray(new Annotation[0]),
					controllerBuilder.getClassType(), null, null, null,
					null, null, null, null, null, true);
			
			actionList.add(entry);
		}

	}

	protected void addActions(ControllerBuilder controllerBuilder,
			ComponentRegistry componentRegistry, Class<?> clazz) {

		List<ActionEntry> actionList = new ArrayList<ActionEntry>();

		addOldAbstractActions(controllerBuilder, componentRegistry, clazz,
				actionList);

		addActions(controllerBuilder, componentRegistry, clazz, actionList);

		Method[] methods = clazz.getMethods();

		for (Method m : methods) {
			ActionEntry entry = new ActionEntry(m);
			actionList.add(entry);
		}

		for (ActionEntry m : actionList) {
			super.applyInternalConfiguration(m, controllerBuilder,
					componentRegistry);
		}
	}

	protected void importBeans(ControllerBuilder controllerBuilder,
			ComponentRegistry componentRegistry, Class<?> clazz) {

		ImportBeans beans = (ImportBeans) clazz
				.getAnnotation(ImportBeans.class);

		if (beans != null) {
			for (Class<?> bean : beans.value()) {
				ImportBeanEntry beanEntry = new ImportBeanEntry(bean);
				super.applyInternalConfiguration(beanEntry, controllerBuilder,
						componentRegistry);
			}
		}
	}

	public boolean isApplicable(Object source) {
		return source instanceof Class
				&& AnnotationUtil.isController((Class<?>) source);
	}

}
