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

package org.brandao.brutos.mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.brandao.brutos.BrutosException;

/**
 * 
 * @author Brandao
 */
public class InterceptorStack extends Interceptor {

	private List<Interceptor> interceptors;

	public InterceptorStack(InterceptorStack parent) {
		super(parent);
		this.interceptors = new ArrayList<Interceptor>(parent.interceptors);
	}

	public InterceptorStack() {
		this.interceptors = new ArrayList<Interceptor>();
	}

	public List<Interceptor> getInterceptors() {
		return interceptors;
	}

	public void setInterceptors(List<Interceptor> interceptors) {
		this.interceptors = interceptors;
	}

	public void addInterceptor(Interceptor interceptor) {
		if (interceptors.contains(interceptor))
			throw new BrutosException("interceptor already added: "
					+ interceptor.getName());
		else
			interceptors.add(interceptor);
	}

	public Interceptor getInterceptor(String name) {
		for (Interceptor interceptor : this.interceptors) {
			if (interceptor.getName().equals(name))
				return interceptor;
		}
		return null;
	}

	public boolean containsInterceptor(String name) {
		return this.getInterceptor(name) != null;
	}

	public boolean containsInterceptor(Interceptor interceptor) {
		return interceptors.contains(interceptor);
	}

	public void removeInterceptor(Interceptor interceptor) {
		if (this.containsInterceptor(interceptor))
			interceptors.remove(interceptor);
		else
			throw new BrutosException("interceptor not found: "
					+ interceptor.getName());
	}

	@Override
	protected void checkProperty(String name, Interceptor stack) {

		if (name == null)
			throw new BrutosException("parameter name must be informed");

		if (name.indexOf(".") == -1)
			throw new BrutosException(
					"interceptor must be informed on parameter: " + name);

		if (!name.matches("([a-zA-Z0-9_]+)(\\.[a-zA-Z0-9_]+)+"))
			throw new BrutosException("invalid parameter name: " + name);

		String[] parts = name.split("\\.");

		String[] route = Arrays.copyOf(parts, parts.length - 1);

		this.checkProperty(route, 0, (InterceptorStack) stack);
	}

	private void checkProperty(String[] route, int indexRoute,
			InterceptorStack stack) {

		if (indexRoute < route.length - 1) {
			Interceptor interceptor = stack.getInterceptor(route[indexRoute]);

			if (!(interceptor instanceof InterceptorStack))
				throw new BrutosException("is not an interceptor stack: "
						+ route[indexRoute]);

			this.checkProperty(route, indexRoute++,
					(InterceptorStack) interceptor);
		} else {
			if (!stack.containsInterceptor(route[indexRoute]))
				throw new BrutosException("interceptor not found: "
						+ route[indexRoute]);
		}
	}

}
