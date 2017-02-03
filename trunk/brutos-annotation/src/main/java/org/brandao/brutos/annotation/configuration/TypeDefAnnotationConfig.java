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

import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.TypeDef;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.type.TypeFactory;

/**
 *
 * @author Brandao
 */
@Stereotype(target = TypeDef.class)
public class TypeDefAnnotationConfig extends AbstractAnnotationConfig {

	public boolean isApplicable(Object source) {
		return source instanceof Class
				&& (((Class<?>) source).isAnnotationPresent(TypeDef.class) || ((Class<?>) source)
						.getSimpleName().endsWith("TypeFactory"));
	}

	public Object applyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		try {
			return applyConfiguration0(source, builder, componentRegistry);
		} catch (Exception e) {
			throw new MappingException("can't create new type", e);
		}

	}

	public Object applyConfiguration0(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		Object factory = null;
		Class<?> classType = (Class<?>) source;
		try {
			factory = ClassUtil.getInstance(classType);
		} catch (Exception e) {
			throw new MappingException(e);
		}

		if (!(factory instanceof TypeFactory))
			throw new MappingException("must implement TypeFactory: "
					+ classType.getSimpleName());

		componentRegistry.registerType((TypeFactory) factory);
		return factory;

	}

}
