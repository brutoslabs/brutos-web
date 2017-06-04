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

/**
 * 
 * @author Brandao
 */
public abstract class AbstractViewResolver implements ViewResolver {

	private ApplicationContext context;

	public String getView(ControllerBuilder controllerBuilder,
			ActionBuilder actionBuilder, Class<?> exception, String view) {

		if (!this.context.isAutomaticViewResolver())
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
		return this.context.getViewPrefix();
	}

	public String getSuffix() {
		return this.context.getViewSuffix();
	}

	public String getIndexName() {
		return this.context.getViewIndex();
	}

	public String getSeparator() {
		return this.context.getSeparator();
	}

}
