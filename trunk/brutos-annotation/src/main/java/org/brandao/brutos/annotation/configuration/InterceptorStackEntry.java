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

import java.util.List;
import org.brandao.brutos.annotation.configuration.converters.InterceptorStackConverter.InterceptorStackItem;

/**
 *
 * @author Brandao
 */
public class InterceptorStackEntry {

	private String name;

	private List<InterceptorStackItem> interceptors;

	private boolean isDefault;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<InterceptorStackItem> getInterceptors() {
		return interceptors;
	}

	public void setInterceptors(List<InterceptorStackItem> interceptors) {
		this.interceptors = interceptors;
	}

	public void setDefault(boolean value) {
		this.isDefault = value;
	}

	public boolean isDefault() {
		return this.isDefault;
	}
}
