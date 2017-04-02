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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.InterceptorStack;
import org.brandao.brutos.mapping.MappingException;

/**
 * 
 * @author Brandao
 */
public class InterceptorStackBuilder {

	private Interceptor interceptor;
	private InterceptorManager manager;
	private Interceptor current;

	public InterceptorStackBuilder(Interceptor interceptor,
			InterceptorManager manager) {
		this.interceptor = interceptor;
		this.manager = manager;
		this.current = interceptor;
	}

	public InterceptorStackBuilder addInterceptor(String interceptorName) {
		Interceptor in = manager.getInterceptor(interceptorName);

		if (in == null)
			throw new MappingException("interceptor not found: "
					+ interceptorName);

		if (in.isDefault())
			throw new MappingException(
					"interceptor can't be added in the stack: " + in.getName());

		current = new Interceptor(in);
		current.setProperties(new HashMap<String, Object>());

		Set<String> keys = in.getProperties().keySet();
		Iterator<String> iKeys = keys.iterator();

		while (iKeys.hasNext()) {
			String key = iKeys.next();
			Object value = in.getProperties().get(key);
			current.getProperties().put(key, value);
		}

		((InterceptorStack) interceptor).addInterceptor(current);

		return this;
	}

	public InterceptorStackBuilder addParameter(String name, String value) {

		if (current == null)
			throw new MappingException("addInterceptor() is not invoked!");

		if (name == null || !name.matches("([a-zA-Z0-9_]+)(\\.[a-zA-Z0-9_]+)+"))
			throw new MappingException("invalid parameter name: " + name);

		// current.setProperty(name, value);
		interceptor.setProperty(name, value);
		return this;
	}

}
