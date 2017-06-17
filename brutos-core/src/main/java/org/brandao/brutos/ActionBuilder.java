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

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.mapping.ParameterAction;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.type.ObjectType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeUtil;
import org.brandao.brutos.type.UnknownTypeException;

/**
 * 
 * @author Brandao
 */
public class ActionBuilder extends RestrictionBuilder {

	protected Controller controller;

	protected Action action;

	protected ValidatorFactory validatorFactory;

	protected ControllerBuilder controllerBuilder;

	protected ConfigurableApplicationContext applicationContext;

	protected ParametersBuilder parametersBuilder;

	public ActionBuilder(ActionBuilder actionBuilder) {
		this(actionBuilder.action, actionBuilder.controller,
				actionBuilder.validatorFactory,
				actionBuilder.controllerBuilder,
				actionBuilder.applicationContext);
	}

	public ActionBuilder(Action action, Controller controller,
			ValidatorFactory validatorFactory,
			ControllerBuilder controllerBuilder,
			ConfigurableApplicationContext applicationContext) {
		super(action.getResultValidator().getConfiguration());
		this.controller = controller;
		this.action = action;
		this.validatorFactory = validatorFactory;
		this.controllerBuilder = controllerBuilder;
		this.applicationContext = applicationContext;
		this.parametersBuilder = new ParametersBuilder(controller, action,
				validatorFactory, controllerBuilder, applicationContext);
	}

	public ActionBuilder addAlias(String id) {

		id = StringUtil.adjust(id);

		if (StringUtil.isEmpty(id))
			throw new MappingException("action id cannot be empty");

		ActionID actionId = new ActionID(id);
		
		return this.addAlias(actionId);
	}

	protected ActionBuilder addAlias(ActionID id) {

		if (controller.getActionById(id) != null)
			throw new MappingException("duplicate action: " + id);

		action.getAlias().add(id);
		controller.addAction(id, this.action);

		return this;
	}
	
	public ActionBuilder removeAlias(String id) {
		id = StringUtil.adjust(id);

		if (StringUtil.isEmpty(id))
			throw new MappingException("action id cannot be empty");

		
		ActionID actionId = new ActionID(id);
		
		if (controller.getActionById(actionId) != null)
			throw new MappingException("duplicate action: " + id);
		
		return this.removeAlias(actionId);
	}

	public ActionBuilder removeAlias(ActionID id) {
		
		if (controller.getAction(id) != null)
			throw new MappingException("action alias not found: " + id);
		
		action.getAlias().remove(id);
		controller.removeAction(id);
		
		return this;
	}
	
	public ParametersBuilder buildParameters() {
		return this.parametersBuilder;
	}

	public ActionBuilder addThrowable(Class<?> target, String id) {
		return addThrowable(target, null, false, id,
				null);
	}

	public ActionBuilder addThrowable(Class<?> target, String view,
			boolean resolvedView, String id, DispatcherType dispatcher) {
		return this.addThrowable(target, view, id, dispatcher, resolvedView);
	}

	public ActionBuilder addThrowable(Class<?> target, String view, String id,
			DispatcherType dispatcher, boolean resolvedView) {
		
		view = StringUtil.adjust(view);

		id = StringUtil.isEmpty(id)? BrutosConstants.DEFAULT_EXCEPTION_NAME : StringUtil.adjust(id);
		
		String originalView = view;

		view = resolvedView ? 
				view : 
				applicationContext.getViewResolver().getView(this.controllerBuilder, this, target, view);

		dispatcher = dispatcher == null? 
				this.applicationContext.getDispatcherType() :
				dispatcher;

		if (target == null){
			throw new MappingException("target is required: "
					+ controller.getClassType().getName());
		}

		if (!Throwable.class.isAssignableFrom(target)){
			throw new MappingException("target is not allowed: "
					+ target.getName());
		}

		if (this.action.getThrowsSafeOnAction(target) != null){
			throw new MappingException(
					"the exception has been added on action: "
							+ target.getSimpleName());
		}

		ThrowableSafeData thr = new ThrowableSafeData();
		thr.setParameterName(id);
		thr.setTarget(target);
		thr.setView(view);
		thr.setOriginalView(originalView);
		thr.setResolvedView(resolvedView);
		thr.setRedirect(false);
		thr.setDispatcher(dispatcher);
		this.action.setThrowsSafe(thr);
		return this;
	}

	public ControllerBuilder getControllerBuilder() {
		return this.controllerBuilder;
	}

	public String getName() {
		return this.action.getName();
	}

	public ActionBuilder setView(String view, boolean resolvedView) {

		view = 
			resolvedView ? 
				view : 
				applicationContext.getViewResolver().getView(this.controllerBuilder, this, null, view);

		this.action.setView(view);
		this.action.setResolvedView(resolvedView);
		
		return this;
	}

	public String getView() {
		return this.action.getView();
	}

	public ActionBuilder setDispatcherType(String value) {
		value = StringUtil.adjust(value);

		if (StringUtil.isEmpty(value))
			throw new BrutosException("invalid dispatcher type");

		this.setDispatcherType(DispatcherType.valueOf(value));

		return this;
	}

	public ActionBuilder setDispatcherType(DispatcherType value) {
		this.action.setDispatcherType(value);
		return this;
	}

	public DispatcherType getDispatcherType() {
		return this.action.getDispatcherType();
	}

	public ActionBuilder setExecutor(String value) {
		value = StringUtil.adjust(value);
		this.action.setExecutor(value);
		return this;
	}

	public String getExecutor() {
		return this.action.getExecutor();
	}

	public ActionBuilder setResult(String value) {
		value = StringUtil.adjust(value);
		this.action.getResultAction().setName(value);
		/*
		this.action
				.setReturnIn(StringUtil.isEmpty(value) ? BrutosConstants.DEFAULT_RETURN_NAME
						: value);
		 */
		return this;
	}

	public String getResult() {
		return this.action.getResultAction().getName();
		//return this.action.getReturnIn();
	}

	public ActionBuilder setResultRendered(boolean value) {
		this.action.setReturnRendered(value);
		return this;
	}

	public boolean isResultRendered() {
		return this.action.isReturnRendered();
	}

	public int getParametersSize() {
		return this.action.getParamterSize();
	}

	public ParameterBuilder getParameter(int index) {
		ParameterAction param = this.action.getParameter(index);
		return new ParameterBuilder(param, this.parametersBuilder,
				this.validatorFactory);
	}

	public ActionBuilder addRequestType(DataType value){
		this.action.getRequestTypes().add(value);
		return this;
	}
	
	public ActionBuilder removeRequestType(DataType value){
		this.action.getRequestTypes().remove(value);
		return this;
	}

	public ActionBuilder addResponseType(DataType value){
		this.action.getResponseTypes().add(value);
		return this;
	}
	
	public ActionBuilder removeResponseType(DataType value){
		this.action.getResponseTypes().remove(value);
		return this;
	}

	/* setResultAction */
	
	public ResultActionBuilder setResultAction(String name,
			EnumerationType enumProperty, Class<?> classType) {
		return setResultAction(name, enumProperty, null, null, null, null,
				false, classType);
	}

	public ResultActionBuilder setNullresultAction() {
		return setResultAction(null, null, null, null, null, null, false,
				null);
	}

	public ResultActionBuilder setResultAction(String name,
			String temporalProperty, Class<?> classType) {
		return setResultAction(name, null,
				temporalProperty, null, null, null, false, classType);
	}

	public ResultActionBuilder setResultAction(String name, Type typeDef) {
		return setResultAction(name, null, null,
				null, typeDef, null, false, typeDef.getClassType());
	}

	public ResultActionBuilder setResultAction(String name,
			Class<?> classType) {
		return setResultAction(name, null, null,
				null, null, null, false, classType);
	}

	public ResultActionBuilder setResultActionMapping(String mapping,
			Class<?> classType) {
		return setResultAction(null, null, null, mapping, null, null, false, classType);
	}

	public ResultActionBuilder setResultActionMapping(String name, String mapping,
			Class<?> classType) {
		return setResultAction(name, null, null, mapping, null, null, false, classType);
	}

	public ResultActionBuilder setResultActionMapping(String name, String mapping,
			ScopeType scope, Class<?> classType) {
		return setResultAction(name, null, null,
				mapping, null, null, false, classType);
	}

	public BeanBuilder buildParameter(Class<?> classType) {
		String beanName = this.action.getCode() + "#"
				+ this.action.getParamterSize();
		BeanBuilder bb = this.controllerBuilder.buildMappingBean(beanName,
				null, classType);

		this.setResultActionMapping(beanName, classType);
		return bb;
	}

	public BeanBuilder buildParameter(String name, Class<?> classType) {
		String beanName = this.action.getCode() + "#"
				+ this.action.getParamterSize();
		BeanBuilder bb = this.controllerBuilder.buildMappingBean(beanName,
				null, classType);

		this.setResultActionMapping(name, beanName, classType);
		return bb;
	}

	public BeanBuilder buildResultAction(Class<?> classType, Class<?> beanType) {
		String beanName = this.action.getCode() + "#Result";
		BeanBuilder bb = this.controllerBuilder.buildMappingBean(beanName,
				null, beanType);

		this.setResultActionMapping(beanName, classType);
		return bb;
	}

	public BeanBuilder buildResultAction(String name, Class<?> classType,
			Class<?> beanType) {
		String beanName = this.action.getCode() + "#Result";
		BeanBuilder bb = this.controllerBuilder.buildMappingBean(beanName,
				null, beanType);

		this.setResultAction(name, beanName, classType);
		return bb;
	}

	public ResultActionBuilder setStaticResultAction(Class<?> classType, Object value) {
		return setResultAction(null, null, null, null, null, value, false, classType);
	}

	public ResultActionBuilder setResultAction(String name,
			EnumerationType enumProperty, String temporalProperty,
			String mapping, Type typeDef, Object value, boolean nullable,
			Class<?> classType) {
		return setResultAction(name, enumProperty, temporalProperty,
				mapping, typeDef, value, nullable, (Object) classType);
	}

	public ResultActionBuilder setGenericResultAction(String name, Class<?> classType) {
		return this.setResultAction(name,
				null, null, null, null, null,
				false, true, classType);
	}

	public ResultActionBuilder setGenericResultAction(String name) {
		return this.setResultAction(name,
				null, null, null, null, null,
				false, true, null);
	}

	public ResultActionBuilder setResultAction(String name,
			EnumerationType enumProperty, String temporalProperty,
			String mapping, Type typeDef, Object value, boolean nullable,
			Object classType) {
		return this.setResultAction(name, enumProperty, temporalProperty,
				mapping, typeDef, value, nullable, false, classType);
	}

	public ResultActionBuilder setResultAction(String name,
			EnumerationType enumProperty, String temporalProperty,
			String mapping, Type typeDef, Object value, boolean nullable,
			boolean generic, Object classType) {

		name = StringUtil.adjust(name);
		
		temporalProperty = StringUtil.adjust(temporalProperty);
		
		mapping = StringUtil.adjust(mapping);
		
		Class<?> rawType = TypeUtil.getRawType(classType);

		
		name = name == null? 
			BrutosConstants.DEFAULT_RETURN_NAME : 
			name;
        
		
		temporalProperty = StringUtil.isEmpty(temporalProperty)?
				this.applicationContext.getTemporalProperty() :
				StringUtil.adjust(temporalProperty);
		
		enumProperty = enumProperty == null?
				this.applicationContext.getEnumerationType() :
				enumProperty;
		
		Configuration validatorConfig = new Configuration();

		org.brandao.brutos.mapping.ResultAction resultAction = 
				new org.brandao.brutos.mapping.ResultAction(this.action);

		resultAction.setName(name);
		resultAction.setScopeType(ScopeType.PARAM);
		resultAction.setValidate(this.validatorFactory.getValidator(validatorConfig));
		resultAction.setStaticValue(value);
		resultAction.setNullable(nullable);

		if (typeDef == null) {
			if (classType != null) {
				try {
					typeDef = this.applicationContext.getTypeManager().getType(
							classType, enumProperty, temporalProperty);

				} catch (UnknownTypeException e) {
					throw new MappingException(String.format(
							"<invalid> %s.%s(...): %s",
							new Object[] {
									this.controller.getClassType().getName(),
									action.getExecutor(),
									e.getMessage() }), e);
				}
			}

			if(typeDef == null){
				typeDef = new ObjectType(rawType);
			}
		}
		else
		if(classType != null){
			if (!typeDef.getClassType().isAssignableFrom(rawType)) {
				throw new MappingException(String.format(
						"expected %s found %s",
						new Object[] { rawType.getName(),
								typeDef.getClassType().getName() }));
			}
		}

		resultAction.setType(typeDef);

		if(generic){
			MetaBean metaBean = new MetaBean(controller);
			metaBean.setClassType(rawType);
			resultAction.setMetaBean(metaBean);
		}
		else
		if(!StringUtil.isEmpty(mapping)) {
			if (controller.getBean(mapping) != null)
				resultAction.setMapping(controller.getBean(mapping));
			else
				throw new MappingException("mapping not found: " + mapping);
		}

		action.setResultAction(resultAction);
		return new ResultActionBuilder(this, resultAction, this.validatorFactory);
	}
	
	public ResultActionBuilder getResultAction(){
		return new ResultActionBuilder(this, this.action.getResultAction(), this.validatorFactory);		
	}
	
	/* setResultAction */
}
