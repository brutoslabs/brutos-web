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

import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.InterceptorBuilder;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.interceptor.InterceptorController;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;

/**
 *
 * @author Brandao
 */
@Stereotype(target = InterceptedBy.class, executeAfter = Controller.class)
public class InterceptedByAnnotationConfig extends AbstractAnnotationConfig {

	public boolean isApplicable(Object source) {
		return source instanceof Class
				&& ((Class<?>) source).isAnnotationPresent(InterceptedBy.class);
	}

	public Object applyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		try {
			return applyConfiguration0(source, builder, componentRegistry);
		} catch (Exception e) {
			throw new MappingException(
					"can't create interception on controller "
							+ ((Class<?>) source).getName(), e);
		}

	}

	public Object applyConfiguration0(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		ControllerBuilder controllerBuilder = (ControllerBuilder) builder;
		Class<?> clazz = (Class<?>) source;
		InterceptedBy interceptedBy = (InterceptedBy) clazz
				.getAnnotation(InterceptedBy.class);

		for (Intercept i : interceptedBy.value()) {
			String name;

			InterceptorBuilder ib;

			if (i.interceptor() != InterceptorController.class) {
				Class<? extends InterceptorController> iClass = i.interceptor();
				Interceptor interceptor = componentRegistry
						.getRegisteredInterceptor(iClass);

				if (interceptor == null)
					throw new MappingException("interceptor not found: "
							+ iClass.getName());

				name = interceptor.getName();
			} else
				name = StringUtil.isEmpty(i.name()) ? null : StringUtil
						.adjust(i.name());

			if (StringUtil.isEmpty(name))
				throw new MappingException("invalid interceptor name");

			ib = controllerBuilder.addInterceptor(name);

			for (Param p : i.params())
				ib.addParameter(p.name(), p.value());

		}
		return builder;

	}

}
