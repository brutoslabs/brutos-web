^/*
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

public abstract class AbstractViewResolver implements ViewResolver {

	private ApplicationContext context;

	public String getView(ControllerBuilder controllerBuilder,
			ActionBuilder actionBuilder, Class<?> exception, String view) {

		String autoResolver = this.context.getConfiguration().getProperty(
				BrutosConstants.VIEW_RESOLVER_AUTO,
				BrutosConstants.DEFAULT_VIEW_RESOLVER);

		if (!autoResolver.toLowerCase().equals("true"))
			return view;

		if (exception != null) {
			if (actionBuilder != null) {
				return this.getExceptionView(controllerBuilder.getClassType(),
						actionBuilder.getExecutor(), exception, view);
			} else {
				return this.getExceptionView(controllerBuilder.getClassType(),
						exception, view);
			}
		} else if (actionBuilder != null) {
			return this.getActionView(controllerBuilder.getClassType(),
					actionBuilder.getExecutor(), view);
		} else {
			return this.getControllerView(controllerBuilder.getClassType(),
					view);
		}
	}

	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}

	public String getPrefix() {
		return this.context.getConfiguration().getProperty(
				BrutosConstants.VIEW_RESOLVER_PREFIX,
				BrutosConstants.DEFAULT_PREFIX_VIEW);
	}

	public String getSuffix() {
		return this.context.getConfiguration().getProperty(
				BrutosConstants.VIEW_RESOLVER_SUFFIX,
				BrutosConstants.DEFAULT_SUFFIX_VIEW);
	}

	public String getIndexName() {
		return this.context.getConfiguration().getProperty(
				BrutosConstants.VIEW_RESOLVER_INDEX,
				BrutosConstants.DEFAULT_INDEX_VIEW);
	}

	public String getSeparator() {
		return this.context.getConfiguration().getProperty(
				BrutosConstants.VIEW_RESOLVER_SEPARATOR,
				BrutosConstants.DEFAULT_SEPARATOR_VIEW);
	}

}
