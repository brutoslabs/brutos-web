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

import org.brandao.brutos.mapping.Controller;

/**
 * 
 * @author Brandao
 */
public interface ControllerRegistry {

	ControllerBuilder registerController(Class<?> classtype);

	ControllerBuilder registerController(String id, Class<?> classType);

	ControllerBuilder registerController(String id, String view,
			boolean resolvedView, Class<?> classType);

	ControllerBuilder registerController(String id, String view,
			boolean resolvedView, String name, Class<?> classType,
			String actionId);

	ControllerBuilder registerController(String id, String view,
			boolean resolvedView, DispatcherType dispatcherType, String name,
			Class<?> classType, String actionId);

	ControllerBuilder registerController(String id, String view,
			boolean resolvedView, DispatcherType dispatcherType, String name,
			Class<?> classType, String actionId, ActionType actionType);

	ControllerBuilder registerController(String id, String view,
			DispatcherType dispatcherType, boolean resolvedView, String name,
			Class<?> classType, String actionId, ActionType actionType);

	Controller getRegisteredController(Class<?> clazz);

	Controller getRegisteredController(String name);

}
