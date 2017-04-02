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

package org.brandao.brutos.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Interceptor;

/**
 * 
 * @author Brandao
 */
public class InterceptorProcessConfigurationBuilder {

	private Controller controller;

	public InterceptorProcessConfigurationBuilder(Controller controller) {
		this.controller = controller;
	}

	public InterceptorEntry getStack() {
		List<Interceptor> stack = new ArrayList<Interceptor>();

		for (Interceptor i : controller.getDefaultInterceptorList()) {
			this.buildStack(i, stack);
		}

		for (Interceptor i : controller.getInterceptors()) {
			this.buildStack(i, stack);
		}

		InterceptorEntry flow = new InterceptorEntry(null);
		InterceptorEntry root = flow;

		for (Interceptor i : stack) {
			InterceptorEntry next = new InterceptorEntry(i);
			flow.setNext(next);
			flow = next;
		}

		return root;
	}

	private void buildStack(Interceptor navigableInterceptor,
			List<Interceptor> interceptorStack) {
		this.buildStack(navigableInterceptor, navigableInterceptor,
				interceptorStack);
	}

	private void buildStack(Interceptor root, Interceptor navigableInterceptor,
			List<Interceptor> interceptorStack) {

		if (navigableInterceptor instanceof org.brandao.brutos.mapping.InterceptorStack) {
			org.brandao.brutos.mapping.InterceptorStack stack = (org.brandao.brutos.mapping.InterceptorStack) navigableInterceptor;

			for (Interceptor i : stack.getInterceptors())
				this.buildStack(root, i, interceptorStack);
		} else {
			Interceptor configuredInterceptor = new Interceptor(
					navigableInterceptor);
			Map<String, Object> configuration = this.buildConfiguration(root,
					navigableInterceptor);
			configuredInterceptor.setProperties(configuration);
			interceptorStack.add(configuredInterceptor);
		}
	}

	private Map<String, Object> buildConfiguration(
			Interceptor navigableInterceptor, Interceptor interceptor) {

		Map<String, Object> configuration = new HashMap<String, Object>();
		this.buildConfiguration(navigableInterceptor, interceptor,
				configuration);
		return configuration;
	}

	private StringBuilder buildConfiguration(Interceptor navigableInterceptor,
			Interceptor interceptor, Map<String, Object> configuration) {

		if (navigableInterceptor instanceof org.brandao.brutos.mapping.InterceptorStack) {
			org.brandao.brutos.mapping.InterceptorStack stack = (org.brandao.brutos.mapping.InterceptorStack) navigableInterceptor;

			for (Interceptor i : stack.getInterceptors()) {
				StringBuilder reversePath = this.buildConfiguration(i,
						interceptor, configuration);

				if (reversePath != null) {
					reversePath = reversePath.insert(0, ".").insert(0,
							i.getName());
					transferConfig(reversePath.toString(),
							stack.getProperties(), configuration);
					return reversePath;
				}

			}

			return null;
		} else {
			if (interceptor.equals(navigableInterceptor)) {
				this.transferConfig(null, interceptor.getProperties(),
						configuration);
				return new StringBuilder();
			} else
				return null;
		}
	}

	private void transferConfig(String prefix, Map<String, Object> origin,
			Map<String, Object> dest) {

		for (String key : origin.keySet()) {
			if (prefix == null || key.startsWith(prefix))
				dest.put(prefix == null ? key : key.substring(prefix.length()),
						origin.get(key));
		}

	}

}
