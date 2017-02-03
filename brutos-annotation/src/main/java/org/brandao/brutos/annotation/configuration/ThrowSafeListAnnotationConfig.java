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

import java.lang.reflect.Method;
import org.brandao.brutos.*;
import org.brandao.brutos.annotation.ThrowSafe;
import org.brandao.brutos.annotation.ThrowSafeList;

/**
 *
 * @author Brandao
 */
public class ThrowSafeListAnnotationConfig extends AbstractAnnotationConfig {

	public boolean isApplicable(Object source) {
		return (source instanceof Method && ((Method) source)
				.isAnnotationPresent(ThrowSafeList.class))
				|| (source instanceof Class && ((Class) source)
						.isAnnotationPresent(ThrowSafeList.class));
	}

	public Object applyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		try {
			return applyConfiguration0(source, builder, componentRegistry);
		} catch (Exception e) {
			throw new BrutosException("can't create mapping exception", e);
		}

	}

	public Object applyConfiguration0(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		if (builder instanceof ActionBuilder)
			addThrowSafeList((ActionBuilder) builder, (Method) source);
		else
			addThrowSafeList((ControllerBuilder) builder, (Class) source);

		return builder;
	}

	protected void addThrowSafeList(ActionBuilder actionBuilder, Method method) {
		ThrowSafeList throwSafeList = method.getAnnotation(ThrowSafeList.class);
		for (ThrowSafe throwSafe : throwSafeList.value())
			addThrowSafe(actionBuilder, throwSafe);
	}

	protected void addThrowSafeList(ControllerBuilder controllerBuilder,
			Class clazz) {
		ThrowSafeList throwSafeList = (ThrowSafeList) clazz
				.getAnnotation(ThrowSafeList.class);
		for (ThrowSafe throwSafe : throwSafeList.value())
			addThrowSafe(controllerBuilder, throwSafe);
	}

	protected void addThrowSafe(ActionBuilder actionBuilder, ThrowSafe throwSafe) {
		DispatcherType dispatcher = "".equals(throwSafe.dispatcher()) ? null
				: DispatcherType.valueOf(throwSafe.dispatcher());

		String name = throwSafe.name();
		Class<? extends Throwable> target = throwSafe.target();
		String view = throwSafe.view();
		// actionBuilder.addThrowable(target, view, name, dispatcher);
	}

	protected void addThrowSafe(ControllerBuilder controllerBuilder,
			ThrowSafe throwSafe) {
		DispatcherType dispatcher = "".equals(throwSafe.dispatcher()) ? null
				: DispatcherType.valueOf(throwSafe.dispatcher());

		String name = throwSafe.name();
		Class<? extends Throwable> target = throwSafe.target();
		String view = throwSafe.view();
		// controllerBuilder.addThrowable(target, view, name, dispatcher);
	}

}
