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

package org.brandao.brutos;

import java.lang.reflect.Method;
import java.util.*;

import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.ActionListener;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ControllerID;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.ControllerManager.InternalUpdate;
/**
 * 
 * @author Brandao
 */
public class ControllerManagerImp implements ControllerManager, InternalUpdate {

	protected Map<ControllerID, Controller> mappedControllers;
	
	protected Map<Class<?>, Controller> classMappedControllers;
	
	protected ValidatorFactory validatorFactory;
	
	protected ControllerBuilder current;
	
	protected ConfigurableApplicationContext applicationContext;
	
	protected InterceptorManager interceptorManager;
	
	protected ControllerManager parent;
	
	public ControllerManagerImp() {
		this.mappedControllers      = new HashMap<ControllerID, Controller>();
		this.classMappedControllers = new HashMap<Class<?>, Controller>();
	}

	public ControllerBuilder addController(Class<?> classtype) {
		return addController(null, null, false, null, classtype,
				null);
	}

	public ControllerBuilder addController(String id, Class<?> classType) {
		return addController(id, null, false, null, classType,
				null);
	}

	public ControllerBuilder addController(String id, String view,
			boolean resolvedView, Class<?> classType) {
		return addController(id, view, resolvedView, null, classType, null);
	}

	public ControllerBuilder addController(String id, String view,
			boolean resolvedView, String name, Class<?> classType,
			String actionId) {
		return addController(id, view, resolvedView, null,
				name, classType, actionId);
	}

	public ControllerBuilder addController(String id, String view,
			boolean resolvedView, DispatcherType dispatcherType, String name,
			Class<?> classType, String actionId) {
		return addController(id, view, resolvedView, dispatcherType, name,
				classType, actionId, null);
	}

	public ControllerBuilder addController(String id, String view,
			boolean resolvedView, DispatcherType dispatcherType, String name,
			Class<?> classType, String actionId, ActionType actionType) {
		return this.addController(id, view, dispatcherType, resolvedView, name,
				classType, actionId, actionType);
	}

	public ControllerBuilder addController(String id, String view,
			DispatcherType dispatcherType, boolean resolvedView, String name,
			Class<?> classType, String actionId, ActionType actionType) {

		id       = StringUtil.adjust(id);
		view     = StringUtil.adjust(view);
		actionId = StringUtil.adjust(actionId);
		name     = StringUtil.adjust(name);

		ControllerID controllerID = new ControllerID(id);
		
		actionId = actionId == null?
				this.applicationContext.getActionParameterName() :
				actionId;
				
		dispatcherType = dispatcherType == null? 
				this.applicationContext.getDispatcherType() :
					dispatcherType;

		actionType = actionType == null? 
				this.applicationContext.getActionType() :
					actionType;
				
		if (classType == null){
			throw new MappingException("invalid class type: "
					+ classType);
		}

		if (actionType == null) {
			throw new MappingException("action type is required");
		}
		
    	if(!actionType.isValidControllerId(id))
    		throw new MappingException("invalid controller id: " + id);

		Controller controller = new Controller(this.applicationContext);
		controller.setClassType(classType);
		controller.setId(controllerID);
		
		// Action
		ActionListener ac = new ActionListener();
		ac.setPreAction(getMethodAction("preAction", controller.getClassType()));
		ac.setPostAction(getMethodAction("postAction", controller.getClassType()));
		controller.setActionListener(ac);

		controller.setDefaultInterceptorList(interceptorManager
				.getDefaultInterceptors());

		this.current = new ControllerBuilder(controller, this,
				interceptorManager, validatorFactory, applicationContext,
				this);

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

	protected Method getMethodAction(String methodName, Class<?> classe) {
		try {
			Method method = classe
					.getDeclaredMethod(methodName, new Class[] {StackRequestElement.class});
			return method;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean contains(String id) {
		boolean result = this.mappedControllers.containsKey(id);
		return !result && parent != null ? parent.contains(id) : result;
	}

	public Controller getController(String id) {
		Controller controller = (Controller) mappedControllers.get(id);

		if (controller == null && parent != null)
			return parent.getController(id);
		else
			return controller;

	}

	public Controller getController(Class<?> controllerClass) {
		Controller controller = (Controller) classMappedControllers
				.get(controllerClass);

		if (controller == null && parent != null)
			return parent.getController(controllerClass);
		else
			return controller;
	}

	public List<Controller> getControllers() {
		List<Controller> tmp;

		tmp = new LinkedList<Controller>(classMappedControllers.values());

		if (parent != null)
			tmp.addAll(parent.getControllers());

		return Collections.unmodifiableList(tmp);
	}

	public Iterator<Controller> getAllControllers() {
		return new Iterator<Controller>() {

			private Iterator<Controller> currentIterator;
			private Iterator<Controller> parentIterator;
			private int index;
			private int maxSize;
			{
				index = 0;
				currentIterator = classMappedControllers.values().iterator();
				parentIterator = parent != null ? parent.getControllers()
						.iterator() : null;
				maxSize = classMappedControllers.size();

			}

			public boolean hasNext() {
				if (index < maxSize)
					return currentIterator.hasNext();
				else
					return parentIterator != null ? parentIterator.hasNext()
							: false;
			}

			public Controller next() {
				try {
					if (index < maxSize)
						return currentIterator.next();
					else
						return parentIterator != null ? parentIterator.next()
								: null;
				} finally {
					index++;
				}
			}

			public void remove() {
				if (index < maxSize)
					currentIterator.remove();
				else if (parentIterator != null)
					parentIterator.remove();

				index--;
			}

		};
	}

	protected synchronized void addController(ControllerID id, Controller controller) {

		if (mappedControllers.containsKey(id)){
			throw new BrutosException(String.format(
					"duplicate controller: %s", new Object[] { id.getName() }));
		}
		
		mappedControllers.put(id, controller);

		if(controller.getId().equals(id)){
			this.classMappedControllers.put(controller.getClassType(), controller);
		}
		
		this.applicationContext.getActionResolver().registry(id, controller, null, null);
	}

	protected synchronized void removeController(ControllerID id, Controller controller) {

		if (!mappedControllers.containsKey(id))
			throw new BrutosException(String.format(
					"controller not found: %s", new Object[] { id.getName() }));
		else
			mappedControllers.remove(id);

		if (controller.getId().equals(id)){
			
			if(controller.getId() != null){
				mappedControllers.remove(controller.getId());
			}
			
			for(ControllerID alias: controller.getAlias()){
				this.removeController(alias, controller);
			}
			
			this.classMappedControllers.remove(controller.getClassType());
		}
		
		this.applicationContext.getActionResolver().registry(id, controller, null, null);
	}

	public ControllerBuilder getCurrent() {
		return current;
	}

	public void setParent(ControllerManager parent) {
		this.parent = parent;
	}

	public ControllerManager getParent() {
		return this.parent;
	}

	public Logger getLogger() {
		return LoggerProvider.getCurrentLoggerProvider().getLogger(
				ControllerBuilder.class);
	}

	public InterceptorManager getInterceptorManager() {
		return this.interceptorManager;
	}

	public void setInterceptorManager(InterceptorManager interceptorManager) {
		this.interceptorManager = interceptorManager;
	}

	public ValidatorFactory getValidatorFactory() {
		return this.validatorFactory;
	}

	public void setValidatorFactory(ValidatorFactory validatorFactory) {
		this.validatorFactory = validatorFactory;
	}

	public ConfigurableApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public void setApplicationContext(
			ConfigurableApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public synchronized void removeController(Class<?> clazz) {
		Controller controller = (Controller) classMappedControllers.get(clazz);

		if (controller != null) {
			this.removeController(controller.getId(), controller);
		}
		
	}

	public synchronized void removeController(String name) {
		ControllerID id = new ControllerID(name);
		
		Controller controller = (Controller) mappedControllers.get(id);

		if (controller != null) {
			this.removeController(id, controller);
		}

	}

	public void addControllerAlias(Controller controller, ControllerID alias) {
		this.addController(alias, controller);
	}

	public void removeControllerAlias(Controller controller, ControllerID alias) {
		this.removeController(alias, controller);
	}
	
}
