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

package org.brandao.brutos.annotation.web;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.ThrowSafe;
import org.brandao.brutos.annotation.configuration.ThrowSafeAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ThrowableEntry;
import org.brandao.brutos.annotation.configuration.web.WebThrowableEntry;
import org.brandao.brutos.web.WebActionBuilder;
import org.brandao.brutos.web.WebControllerBuilder;

/**
 * 
 * @author Brandao
 *
 */
@Stereotype(
	target=
		ThrowSafe.class, 
	minorVersion=
		1,
	executeAfter= { 
		Action.class,
		Controller.class 
	}
)
public class WebThrowSafeAnnotationConfig 
	extends ThrowSafeAnnotationConfig{

	protected void addThrowSafe(ActionBuilder actionBuilder,
			ComponentRegistry componentRegistry, ThrowableEntry source) {

		WebActionBuilder builder    = (WebActionBuilder)actionBuilder;
		WebThrowableEntry throwSafe = (WebThrowableEntry)source;
		
		if (throwSafe.isEnabled()) {
			builder.addThrowable(
					throwSafe.getResponseError(), 
					throwSafe.getReason(),
					throwSafe.getTarget(),
					throwSafe.isRendered() ? throwSafe.getView() : null,
					throwSafe.getName(), 
					throwSafe.getDispatcher(),
					throwSafe.isRendered() ? throwSafe.isResolved() : true
			);
		}
	}

	protected void addThrowSafe(ControllerBuilder controllerBuilder,
			ComponentRegistry componentRegistry, ThrowableEntry source) {

		WebControllerBuilder builder = (WebControllerBuilder)controllerBuilder;
		WebThrowableEntry throwSafe  = (WebThrowableEntry)source;
		
		if (throwSafe.isEnabled()) {
			builder.addThrowable(
					throwSafe.getResponseError(), 
					throwSafe.getReason(),
					throwSafe.getTarget(),
					throwSafe.isRendered() ? throwSafe.getView() : null,
					throwSafe.getName(), 
					throwSafe.getDispatcher(),
					throwSafe.isRendered() ? throwSafe.isResolved() : true
			);
		}

	}
	
}
