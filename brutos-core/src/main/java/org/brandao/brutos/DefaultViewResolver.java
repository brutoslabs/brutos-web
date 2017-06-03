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

import org.brandao.brutos.mapping.StringUtil;

/**
 * 
 * @author Brandao
 */
public class DefaultViewResolver extends AbstractViewResolver {

	private String getPrefix(Class<?> controllerType) {
		String controllerName = controllerType.getSimpleName();
		// controllerName = controllerName.replaceAll("Controller$", "");
		controllerName = controllerName.toLowerCase();

		/*
		String resolvedView = this.getSeparator();
		resolvedView += controllerName;
		return resolvedView;
		*/
		return controllerName;
	}

	private String getPrefix(Class<?> controllerType, String actionExecutor) {
		String resolvedView = this.getPrefix(controllerType);
		
		if (!StringUtil.isEmpty(actionExecutor)) {
			resolvedView += this.getSeparator();
			resolvedView += actionExecutor.toLowerCase();
		}
		
		return resolvedView;
	}

	public String getControllerView(Class<?> controllerType, String view) {
		String resolvedView = this.getPrefix();
		
		if(StringUtil.isEmpty(view)){
			resolvedView += this.getPrefix(controllerType);
			resolvedView += this.getSeparator();
			resolvedView += this.getIndexName();
		}
		else{
			resolvedView += view;
		}
		
		resolvedView += this.getSuffix();
		return resolvedView;
	}

	public String getActionView(Class<?> controllerType, String actionExecutor,
			String view) {
		String resolvedView = this.getPrefix();

		if(StringUtil.isEmpty(view)){
			resolvedView += this.getPrefix(controllerType, actionExecutor);
			resolvedView += this.getSeparator();
			resolvedView += this.getIndexName();
		}
		else{
			resolvedView += view;
		}
		
		resolvedView += this.getSuffix();
		return resolvedView;
	}

	public String getExceptionView(Class<?> controllerType, String actionExecutor,
			Class<?> exceptionType, String view) {
		String resolvedView = this.getPrefix();
		
		if(StringUtil.isEmpty(view)){
			resolvedView += this.getPrefix(controllerType, actionExecutor);
			resolvedView += this.getSeparator();
			resolvedView += exceptionType.getSimpleName().toLowerCase();
		}
		else{
			resolvedView += view;
		}
		
		resolvedView += this.getSuffix();
		return resolvedView;
	}

	public String getExceptionView(Class<?> controllerType, Class<?> exceptionType,
			String view) {
		String resolvedView = this.getPrefix();
		
		if(StringUtil.isEmpty(view)){
			resolvedView += this.getPrefix(controllerType);
			resolvedView += this.getSeparator();
			resolvedView += exceptionType.getSimpleName().toLowerCase();
		}
		else{
			resolvedView += view;
		}
		
		resolvedView += this.getSuffix();
		return resolvedView;
	}

}
