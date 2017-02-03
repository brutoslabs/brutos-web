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

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.InterceptorBuilder;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.Param;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.mapping.StringUtil;

/**
 *
 * @author Brandao
 */
@Stereotype(target = Intercepts.class)
public class InterceptsAnnotationConfig extends AbstractAnnotationConfig {

	public boolean isApplicable(Object source) {
		return source instanceof Class
				&& AnnotationUtil.isInterceptor((Class) source);
	}

	public Object applyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		try {
			return applyConfiguration0(source, builder, componentRegistry);
		} catch (Exception e) {
			throw new BrutosException("can't create interceptor: "
					+ ((Class) source).getName(), e);
		}

	}

	public Object applyConfiguration0(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		Class clazz = (Class) source;
		Intercepts intercepts = (Intercepts) clazz
				.getAnnotation(Intercepts.class);

		String name = intercepts == null
				|| StringUtil.adjust(intercepts.name()) == null ? clazz
				.getSimpleName().replaceAll("InterceptorController$", "")
				: StringUtil.adjust(intercepts.name());

		name = StringUtil.toVariableFormat(name);

		boolean isDefault = intercepts == null || intercepts.isDefault();
		InterceptorBuilder newBuilder = componentRegistry.registerInterceptor(
				name, clazz, isDefault);

		if (intercepts != null) {
			for (Param p : intercepts.params())
				newBuilder.addParameter(p.name(), p.value());
		}

		super.applyInternalConfiguration(source, newBuilder, componentRegistry);
		return builder;
	}

}
