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

import org.brandao.brutos.DispatcherType;

/**
 * 
 * @author Brandao
 */
public class ThrowableSafeData {

	private Class<?> target;

	private String view;

	private String originalView;

	private boolean resolvedView;

	private String parameterName;

	private boolean redirect;

	private DispatcherType dispatcher;

	public ThrowableSafeData() {
	}

	public Class<?> getTarget() {
		return target;
	}

	public void setTarget(Class<?> target) {
		this.target = target;
	}

	public String getView() {
		return view;
	}

	public void setView(String uri) {
		this.view = uri;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	public DispatcherType getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(DispatcherType dispatcher) {
		this.dispatcher = dispatcher;
	}

	public boolean isResolvedView() {
		return resolvedView;
	}

	public void setResolvedView(boolean resolvedView) {
		this.resolvedView = resolvedView;
	}

	public String getOriginalView() {
		return originalView;
	}

	public void setOriginalView(String originalView) {
		this.originalView = originalView;
	}

}
