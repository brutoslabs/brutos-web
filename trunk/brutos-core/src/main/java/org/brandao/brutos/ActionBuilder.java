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

import org.brandao.brutos.mapping.*;

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

	public ActionBuilder addAlias(String value) {
		this.controllerBuilder.addActionAlias(value, this);
		return this;
	}

	public ActionBuilder removeAlias(String value) {
		this.controllerBuilder.removeActionAlias(value, this);
		return this;
	}

	public ParametersBuilder buildParameters() {
		return this.parametersBuilder;
	}

	public ActionBuilder addThrowable(Class<?> target, String id) {
		return addThrowable(target, null, !"true".equals(applicationContext
				.getConfiguration().getProperty(
						BrutosConstants.VIEW_RESOLVER_AUTO)), id,
				DispatcherType.FORWARD);
	}

	public ActionBuilder addThrowable(Class<?> target, String view,
			boolean resolvedView, String id, DispatcherType dispatcher) {
		return this.addThrowable(target, view, id, dispatcher, resolvedView);
	}

	public ActionBuilder addThrowable(Class<?> target, String view, String id,
			DispatcherType dispatcher, boolean resolvedView) {
		view = StringUtil.adjust(view);

		String originalView = view;

		view = resolvedView ? view : applicationContext.getViewResolver()
				.getView(this.controllerBuilder, this, target, view);

		id = StringUtil.adjust(id);

		if (target == null)
			throw new BrutosException("target is required: "
					+ controller.getClassType().getName());

		if (!Throwable.class.isAssignableFrom(target))
			throw new BrutosException("target is not allowed: "
					+ target.getName());

		if (this.action.getThrowsSafeOnAction(target) != null)
			throw new MappingException(
					"the exception has been added on action: "
							+ target.getSimpleName());

		if (dispatcher == null)
			dispatcher = BrutosConstants.DEFAULT_DISPATCHERTYPE;

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

		view = resolvedView ? view : applicationContext.getViewResolver()
				.getView(this.controllerBuilder, this, null, view);

		this.action.setView(view);
		this.action.setResolvedView(resolvedView);
		return this;
	}

	public String getView() {
		return this.action.getView();
	}

	public ActionBuilder setName(String value) {

		value = StringUtil.adjust(value);

		if (StringUtil.isEmpty(value))
			throw new BrutosException("invalid action name");

		this.action.setName(value);
		return this;
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
		this.action
				.setReturnIn(StringUtil.isEmpty(value) ? BrutosConstants.DEFAULT_RETURN_NAME
						: value);

		return this;
	}

	public String getResult() {
		return this.action.getReturnIn();
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

}
