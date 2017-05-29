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

import java.util.*;

import org.brandao.brutos.ControllerManager.InternalUpdate;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.*;
import org.brandao.brutos.type.NullType;
import org.brandao.brutos.type.ObjectType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeUtil;
import org.brandao.brutos.type.UnknownTypeException;

/**
 * 
 * @author Brandao
 */
public class ControllerBuilder {

	protected final Controller controller;

	protected ControllerManager controllerManager;

	protected InterceptorManager interceptorManager;

	protected ValidatorFactory validatorFactory;

	protected ConfigurableApplicationContext applicationContext;

	protected InternalUpdate internalUpdate;

	public ControllerBuilder(ControllerBuilder builder,
			InternalUpdate internalUpdate) {
		this(builder.controller, builder.controllerManager,
				builder.interceptorManager, builder.validatorFactory,
				builder.applicationContext, internalUpdate);
	}

	public ControllerBuilder(Controller controller,
			ControllerManager controllerManager,
			InterceptorManager interceptorManager,
			ValidatorFactory validatorFactory,
			ConfigurableApplicationContext applicationContext,
			InternalUpdate internalUpdate) {
		this.controller = controller;
		this.controllerManager = controllerManager;
		this.interceptorManager = interceptorManager;
		this.validatorFactory = validatorFactory;
		this.applicationContext = applicationContext;
		this.internalUpdate = internalUpdate;
	}

	public ControllerBuilder addAlias(String id) {

    	if(!this.controller.getActionType().isValidControllerId(id))
    		throw new MappingException("invalid controller alias: " + id);
		
		id = StringUtil.adjust(id);

		if (StringUtil.isEmpty(id))
			throw new MappingException("invalid alias");

		getLogger().info(
				String.format("adding alias %s on controller %s", new Object[] {
						id, controller.getClassType().getSimpleName() }));

		controller.addAlias(id);
		internalUpdate.addControllerAlias(controller, id);
		return this;
	}

	public ControllerBuilder removeAlias(String id) {

		id = StringUtil.adjust(id);

		if (StringUtil.isEmpty(id))
			throw new MappingException("invalid alias");

		getLogger().info(
				String.format("removing alias %s on controller %s",
						new Object[] { id,
								controller.getClassType().getSimpleName() }));

		internalUpdate.removeControllerAlias(controller, id);
		return this;
	}

	public ControllerBuilder addThrowable(Class<?> target, String id) {
		return addThrowable(target, null, !"true".equals(applicationContext
				.getConfiguration().getProperty(
						BrutosConstants.VIEW_RESOLVER_AUTO)), id,
				DispatcherType.FORWARD);
	}

	public ControllerBuilder addThrowable(Class<?> target, String view,
			boolean resolvedView, String id, DispatcherType dispatcher) {
		return this.addThrowable(target, view, id, dispatcher, resolvedView);
	}

	public ControllerBuilder addThrowable(Class<?> target, String view,
			String id, DispatcherType dispatcher, boolean resolvedView) {

		view = StringUtil.adjust(view);

		view = resolvedView ? view : applicationContext.getViewResolver()
				.getView(this, null, target, view);

		id = StringUtil.adjust(id);

		if (target == null)
			throw new MappingException("target is required: "
					+ controller.getClassType().getSimpleName());

		if (!Throwable.class.isAssignableFrom(target))
			throw new MappingException("target is not allowed: "
					+ target.getSimpleName());

		if (this.controller.getThrowsSafe(target) != null)
			throw new MappingException(
					"the exception has been added on controller: "
							+ target.getSimpleName());

		if (dispatcher == null)
			dispatcher = BrutosConstants.DEFAULT_DISPATCHERTYPE;

		getLogger().info(
				String.format("adding exception %s on controller %s",
						new Object[] { target.getSimpleName(),
								controller.getClassType().getSimpleName() }));

		ThrowableSafeData thr = this.createThrowableSafeData();
		thr.setParameterName(id);
		thr.setTarget(target);
		thr.setView(view);
		thr.setRedirect(false);
		thr.setDispatcher(dispatcher);
		this.controller.setThrowsSafe(thr);
		return this;
	}

	protected ThrowableSafeData createThrowableSafeData(){
		return new ThrowableSafeData();
	}
	
	public ControllerBuilder setDefaultAction(String id) {

		id = StringUtil.adjust(id);

		ActionID actionID = new ActionID(id);
		
		if (this.controller.getActionById(actionID) == null)
			throw new MappingException("action not found: \"" + id + "\"");

		if (id != null) {
			getLogger()
					.info(String
							.format("adding default action %s on controller %s",
									new Object[] {
											id,
											controller.getClassType()
													.getSimpleName() }));

			controller.setDefaultAction(actionID);
		}
		return this;
	}

	public BeanBuilder buildMappingBean(String name, Class<?> target) {
		return this.buildMappingBean(name, null, target);
	}

	public BeanBuilder buildMappingBean(String name, String parentBeanName,
			Class<?> target) {

		if (target == null)
			throw new MappingException("invalid target class");

		name = StringUtil.adjust(name);

		if (name == null || !name.matches("[a-zA-Z0-9_#]+"))
			throw new MappingException("invalid bean name: \"" + name + "\"");

		if (controller.getBean(name) != null)
			throw new MappingException("duplicate bean name: \"" + name + "\"");

		getLogger().info(
				String.format("adding bean %s[%s]",
						new Object[] { name, target.getSimpleName() }));

		Bean parentBean = parentBeanName == null ? null : this.controller
				.getBean(parentBeanName);

		Bean mappingBean;

		if (Map.class.isAssignableFrom(target))
			mappingBean = new MapBean(controller, parentBean);
		else if (Collection.class.isAssignableFrom(target))
			mappingBean = new CollectionBean(controller, parentBean);
		else
			mappingBean = new Bean(controller, parentBean);

		ConstructorBean constructor = mappingBean.getConstructor();

		constructor.setValidator(this.validatorFactory
				.getValidator(new Configuration()));

		mappingBean.setClassType(target);
		mappingBean.setName(name);
		controller.addBean(name, mappingBean);
		BeanBuilder mb = new BeanBuilder(mappingBean, controller, this,
				validatorFactory, applicationContext);
		return mb;
	}

	public ActionBuilder addAction(String id) {
		return addAction(id, null, null, !"true".equals(applicationContext
				.getConfiguration().getProperty(
						BrutosConstants.VIEW_RESOLVER_AUTO)),
				DispatcherType.FORWARD, null);
	}

	public ActionBuilder addAction(String id, String executor) {
		return addAction(id, null, null, !"true".equals(applicationContext
				.getConfiguration().getProperty(
						BrutosConstants.VIEW_RESOLVER_AUTO)),
				DispatcherType.FORWARD, executor);
	}

	public ActionBuilder addAction(String id, String executor, String view,
			boolean resolvedView) {
		return addAction(id, null, view, resolvedView, DispatcherType.FORWARD,
				executor);
	}

	public ActionBuilder addAction(String id, String resultId, String view,
			boolean resolvedView, String executor) {
		return addAction(id, resultId, view, resolvedView,
				DispatcherType.FORWARD, executor);
	}

	public ActionBuilder addAction(String id, String resultId, String view,
			boolean resolvedView, DispatcherType dispatcher, String executor) {
		return addAction(id, resultId, false, view, resolvedView, dispatcher,
				executor);
	}

	public ActionBuilder addAction(String id, String resultId,
			boolean resultRendered, String view, boolean resolvedView,
			DispatcherType dispatcher, String executor) {
		return this.addAction(id, resultId, resultRendered, view, dispatcher,
				resolvedView, executor);
	}

	public ActionBuilder addAction(String id, String resultId,
			boolean resultRendered, String view, DispatcherType dispatcher,
			boolean resolvedView, String executor) {

		id = StringUtil.adjust(id);

		if (StringUtil.isEmpty(id))
			throw new MappingException("action id cannot be empty");

		resultId = StringUtil.adjust(resultId);

		view = StringUtil.adjust(view);

		executor = StringUtil.adjust(executor);

		ActionID actionId = new ActionID(id);
		
		if (StringUtil.isEmpty(view) && StringUtil.isEmpty(executor))
			throw new MappingException(
					"view must be informed in abstract actions: " + id);

		if (controller.getAction(actionId) != null)
			throw new MappingException("duplicate action: " + id);

		Action action = this.createAction();
		action.setCode(Action.getNextId());
		action.setController(controller);
		action.setResolvedView(resolvedView);
		action.setResultValidator(validatorFactory
				.getValidator(new Configuration()));
		action.setParametersValidator(validatorFactory
				.getValidator(new Configuration()));
		
		controller.addAction(actionId, action);

		ActionBuilder actionBuilder = new ActionBuilder(action, controller,
				validatorFactory, this, this.applicationContext);

		actionBuilder.setName(id).setDispatcherType(dispatcher)
				.setExecutor(executor).setResult(resultId)
				.setResultRendered(resultRendered).setView(view, resolvedView);

		getLogger()
				.info(String
						.format("adding action %s on controller %s",
								new Object[] {
										action.getId(),
										this.controller.getClassType()
												.getSimpleName() }));

		return actionBuilder;
	}

	protected Action createAction(){
		return new Action();
	}
	
	public ControllerBuilder addActionAlias(String id, ActionBuilder parent) {

		id = StringUtil.adjust(id);

		if (StringUtil.isEmpty(id))
			throw new MappingException("action id cannot be empty");

		ActionID actionId = new ActionID(id);
		
		if (controller.getAction(actionId) != null)
			throw new MappingException("duplicate action: " + id);

		Action action = parent.action;
		action.addAlias(id);
		controller.addAction(actionId, parent.action);

		return this;
	}

	public ControllerBuilder removeActionAlias(String id, ActionBuilder parent) {

		id = StringUtil.adjust(id);

		if (StringUtil.isEmpty(id))
			throw new MappingException("invalid alias");

		ActionID actionId = new ActionID(id);
		
		if (controller.getAction(actionId) == null
				|| !controller.getAction(actionId).equals(parent.action))
			throw new MappingException("invalid action " + id + ": "
					+ controller.getClassType().getName());

		controller.removeAction(actionId);
		
		return this;
	}

	public InterceptorBuilder addInterceptor(String name) {

		name = StringUtil.adjust(name);

		if (StringUtil.isEmpty(name))
			throw new MappingException("interceptor name must be informed");

		if (!this.interceptorManager.containsInterceptor(name))
			throw new MappingException("interceptor not found: " + name);

		Interceptor parent = interceptorManager.getInterceptor(name);

		if (parent.isDefault())
			throw new MappingException(
					"interceptor already intercept this controller: " + name);

		if (this.controller.isInterceptedBy(parent))
			throw new MappingException(
					"interceptor already intercept this controller: " + name);

		Interceptor it;

		if (parent instanceof InterceptorStack)
			it = new InterceptorStack((InterceptorStack) parent);
		else
			it = new Interceptor(parent);

		it.setProperties(new HashMap<String, Object>());

		Set<String> keys = parent.getProperties().keySet();
		Iterator<String> iKeys = keys.iterator();

		while (iKeys.hasNext()) {
			String key = iKeys.next();
			Object value = parent.getProperties().get(key);
			it.getProperties().put(key, value);
		}

		getLogger()
				.info(String
						.format("adding interceptor %s on controller %s",
								new Object[] {
										name,
										this.controller.getClassType()
												.getSimpleName() }));

		controller.addInterceptor(it);

		return new InterceptorBuilder(it, interceptorManager);
	}

	public PropertyBuilder addProperty(String propertyName, String id,
			ScopeType scope, EnumerationType enumProperty) {
		return addProperty(propertyName, id, scope, enumProperty, null, null,
				null, false, null);
	}

	public PropertyBuilder addNullProperty(String propertyName) {
		return addProperty(propertyName, null, null, null, null, null, null,
				true, null);
	}

	public PropertyBuilder addProperty(String propertyName, String id,
			ScopeType scope, String temporalProperty) {
		return addProperty(propertyName, id, scope, EnumerationType.ORDINAL,
				temporalProperty, null, null, false, null);
	}

	public PropertyBuilder addProperty(String propertyName, String id,
			ScopeType scope, Type type) {
		return addProperty(propertyName, id, scope, EnumerationType.ORDINAL,
				"dd/MM/yyyy", null, null, false, type);
	}

	public PropertyBuilder addProperty(String propertyName, String id,
			EnumerationType enumProperty) {
		return addProperty(propertyName, id, ScopeType.PARAM, enumProperty,
				null, null, null, false, null);
	}

	public PropertyBuilder addProperty(String propertyName, String id,
			ScopeType scope) {
		return addProperty(propertyName, id, scope, EnumerationType.ORDINAL,
				"dd/MM/yyyy", null, null, false, null);
	}

	public PropertyBuilder addProperty(String propertyName, String id,
			String temporalProperty) {
		return addProperty(propertyName, id, ScopeType.PARAM,
				EnumerationType.ORDINAL, temporalProperty, null, null, false,
				null);
	}

	public PropertyBuilder addProperty(String propertyName, String id, Type type) {
		return addProperty(propertyName, id, ScopeType.PARAM,
				EnumerationType.ORDINAL, "dd/MM/yyyy", null, null, false, type);
	}

	public PropertyBuilder addPropertyMapping(String propertyName,
			String mapping) {
		return addProperty(propertyName, null, ScopeType.PARAM,
				EnumerationType.ORDINAL, "dd/MM/yyyy", mapping, null, false,
				null);
	}

	public PropertyBuilder addPropertyMapping(String propertyName, String id,
			String mapping) {
		return addProperty(propertyName, id, ScopeType.PARAM,
				EnumerationType.ORDINAL, "dd/MM/yyyy", mapping, null, false,
				null);
	}

	public PropertyBuilder addProperty(String propertyName, String id) {
		return addProperty(propertyName, id, ScopeType.PARAM,
				EnumerationType.ORDINAL, "dd/MM/yyyy", null, null, false, null);
	}

	public PropertyBuilder addStaticProperty(String propertyName, Object value) {
		return addProperty(propertyName, null, ScopeType.PARAM,
				EnumerationType.ORDINAL, "dd/MM/yyyy", null, value, false, null);
	}

	public PropertyBuilder addProperty(String propertyName, String id,
			ScopeType scope, EnumerationType enumProperty,
			String temporalProperty, String mapping, Object value,
			boolean nullable, Type type) {
		return addProperty(propertyName, id, scope, enumProperty,
				temporalProperty, mapping, value, nullable, null, type);
	}

	public PropertyBuilder addGenericProperty(String propertyName, String id,
			Class<?> classType) {
		return this.addProperty(propertyName, id,
				BrutosConstants.DEFAULT_SCOPETYPE,
				BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null, null, false,
				true, classType, null);
	}

	public PropertyBuilder addGenericProperty(String propertyName, String id) {
		return this.addProperty(propertyName, id,
				BrutosConstants.DEFAULT_SCOPETYPE,
				BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null, null, false,
				true, null, null);
	}

	public PropertyBuilder addProperty(String propertyName, String id,
			ScopeType scope, EnumerationType enumProperty,
			String temporalProperty, String mapping, Object value,
			boolean nullable, Object classType, Type type) {
		return this.addProperty(propertyName, id, scope, enumProperty,
				temporalProperty, mapping, value, nullable, false, classType,
				type);
	}

	public PropertyBuilder addProperty(String propertyName, String id,
			ScopeType scope, EnumerationType enumProperty,
			String temporalProperty, String mapping, Object value,
			boolean nullable, boolean generic, Object classType, Type type) {

		propertyName = StringUtil.adjust(propertyName);
		id = StringUtil.isEmpty(id) ? propertyName : StringUtil.adjust(id);
		temporalProperty = StringUtil.adjust(temporalProperty);
		mapping = StringUtil.adjust(mapping);
		BeanInstance bean = this.controller.getBeanInstance();
		Object genericType = classType == null ? bean
				.getGenericType(propertyName) : classType;
		Class<?> rawType = TypeUtil.getRawType(genericType);

		if (propertyName == null)
			throw new MappingException("property name is required: "
					+ controller.getClassType().getName());

		if (controller.containsProperty(propertyName))
			throw new MappingException("property already added: "
					+ controller.getClassType().getName() + "." + propertyName);

		if (scope == null)
			throw new MappingException("invalid scope");

		PropertyController property = new PropertyController();
		property.setName(id);
		property.setScopeType(scope);
		property.setValidate(this.validatorFactory
				.getValidator(new Configuration()));
		property.setStaticValue(value);
		property.setNullable(nullable);
		property.setPropertyName(propertyName);
		property.setController(this.controller);

		try {
			property.setBeanProperty(bean.getProperty(propertyName));
		} catch (Throwable e) {
			throw new MappingException("no such property: "
					+ controller.getClassType().getName() + "." + propertyName);
		}

		if (type == null) {
			if (nullable) {
				if (classType == null)
					throw new MappingException("type must be informed");

				type = new NullType((Class<?>) classType);
			} else {
				try {
					type = this.applicationContext.getTypeManager().getType(
							genericType, enumProperty, temporalProperty);
				} catch (UnknownTypeException e) {
					throw new MappingException(e);
				}
			}

			if (type == null)
				type = new ObjectType(rawType);

		}

		property.setType(type);

		if(generic){
			MetaBean metaBean = new MetaBean(controller);
			metaBean.setClassType(rawType);
			property.setMetaBean(metaBean);
		}
		else
		if(mapping != null){
			if (controller.getBean(mapping) != null)
				property.setMapping(controller.getBean(mapping));
			else
				throw new MappingException("mapping not found: " + mapping);
		}

		controller.addProperty(property);

		getLogger()
				.info(String
						.format("adding property %s on controller %s",
								new Object[] {
										propertyName,
										this.controller.getClassType()
												.getSimpleName() }));

		return new PropertyBuilder(property, this, this.validatorFactory);
	}

	public BeanBuilder buildProperty(String propertyName, Class<?> clazz) {
		String beanName = this.controller.getName() + "Controller#"
				+ propertyName;

		BeanBuilder beanBuilder = buildMappingBean(beanName, clazz);

		this.addPropertyMapping(propertyName, beanName);

		return beanBuilder;
	}

	public Class<?> getClassType() {
		return controller.getClassType();
	}

	public Bean getBean(String name) {
		return controller.getBean(name);
	}

	public ControllerBuilder setId(String value) {

		value = StringUtil.adjust(value);

		this.controller.setId(value);
		return this;
	}

	public String getId() {
		return controller.getId();
	}

	public ControllerBuilder setName(String value) {
		value = StringUtil.adjust(value);

		if (value == null)
			value = controller.getClassType().getSimpleName();

		controller.setName(value);

		return this;
	}

	public String getName() {
		return controller.getName();
	}

	public ControllerBuilder setView(String view, boolean resolvedView) {
		view = StringUtil.adjust(view);

		view = resolvedView ? view : applicationContext.getViewResolver()
				.getView(this, null, null, view);

		controller.setView(view);

		return this;
	}

	public String getView() {
		return controller.getView();
	}

	public ControllerBuilder setActionId(String value) {

		if (StringUtil.isEmpty(value))
			value = BrutosConstants.DEFAULT_ACTION_ID;
		else if (!value.matches("[a-zA-Z0-9_#]+"))
			throw new MappingException("invalid action id: " + value);

		controller.setActionId(value);

		getLogger()
				.info(String
						.format("override the action id to %s on controller %s",
								new Object[] {
										value,
										this.controller.getClassType()
												.getSimpleName() }));

		return this;
	}

	public String getActionId() {
		return controller.getActionId();
	}

	public ControllerBuilder setDispatcherType(String value) {
		value = StringUtil.adjust(value);

		if (StringUtil.isEmpty(value))
			throw new MappingException("invalid dispatcher type");

		this.setDispatcherType(DispatcherType.valueOf(value));

		return this;
	}

	public ControllerBuilder setDispatcherType(DispatcherType value) {
		this.controller.setDispatcherType(value);
		return this;
	}

	public DispatcherType getDispatcherType() {
		return this.controller.getDispatcherType();
	}

	protected Logger getLogger() {
		return LoggerProvider.getCurrentLoggerProvider().getLogger(
				ControllerBuilder.class);
	}

	public ControllerBuilder setActionType(ActionType actionType) {
		this.controller.setActionType(actionType);
		return this;
	}

	public ActionType getActionType() {
		return this.controller.getActionType();
	}

	public PropertyBuilder getProperty(String name) {
		PropertyController property = (PropertyController) controller
				.getProperty(name);
		return property == null ? null : new PropertyBuilder(property, this,
				this.validatorFactory);
	}

	public boolean isResolvedView() {
		return this.controller.isResolvedView();
	}

}
