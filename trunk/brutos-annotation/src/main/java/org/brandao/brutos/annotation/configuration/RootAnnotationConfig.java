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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.configuration.converters.InterceptorStackConverter;

/**
 *
 * @author Brandao
 */
@Stereotype(target = Configuration.class, sourceConverter = InterceptorStackConverter.class)
public class RootAnnotationConfig extends AbstractAnnotationConfig {

	public boolean isApplicable(Object source) {
		return true;
	}

	public Object applyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		try {
			return applyConfiguration0(source, builder, componentRegistry);
		} catch (Throwable e) {
			throw new BrutosException("failed to load configuration", e);
		}

	}

	public Object applyConfiguration0(Object source, Object builder,
			ComponentRegistry componentRegistry) throws InstantiationException,
			IllegalAccessException {

		List<Object> entityList = (List<Object>) source;

		Map<Class, AnnotationConfigEntry> map = new HashMap<Class, AnnotationConfigEntry>();

		for (AnnotationConfigEntry ace : super.annotation
				.getNextAnnotationConfig())
			map.put(ace.getStereotype().target(), ace);

		for (Class target : getExecutionOrder()) {

			AnnotationConfigEntry ace = map.get(target);

			if (ace == null)
				throw new BrutosException("configuration not found: " + target);

			AnnotationConfig ac = ace.getAnnotationConfig();

			for (Object item : entityList) {
				if (ac.isApplicable(item))
					ac.applyConfiguration(item, null, componentRegistry);
			}
		}

		return source;
	}

	@Override
	public Class<? extends Annotation>[] getExecutionOrder() {
		return new Class[] { TypeDef.class, ExtendedScope.class,
				Intercepts.class, InterceptsStack.class, Controller.class };
	}

}
