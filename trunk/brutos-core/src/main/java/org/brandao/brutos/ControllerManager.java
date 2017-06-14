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

import java.util.Iterator;
import java.util.List;

import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ControllerID;

/**
 * 
 * @author Brandao
 */
public interface ControllerManager {

	ControllerBuilder addController(Class<?> classtype);

	ControllerBuilder addController(String id, Class<?> classType);

	ControllerBuilder addController(String id, String view,
			boolean resolvedView, Class<?> classType);

	ControllerBuilder addController(String id, String view,
			boolean resolvedView, String name, Class<?> classType,
			String actionId);

	ControllerBuilder addController(String id, String view,
			boolean resolvedView, DispatcherType dispatcherType, String name,
			Class<?> classType, String actionId);

	ControllerBuilder addController(String id, String view,
			boolean resolvedView, DispatcherType dispatcherType, String name,
			Class<?> classType, String actionId, ActionType actionType);

	ControllerBuilder addController(String id, String view,
			DispatcherType dispatcherType, boolean resolvedView, String name,
			Class<?> classType, String actionId, ActionType actionType);

	boolean contains(String id);

	Controller getController(String id);

	Controller getController(Class<?> controllerClass);

	List<Controller> getControllers();

	Iterator<Controller> getAllControllers();

	ControllerBuilder getCurrent();

	void setParent(ControllerManager parent);

	ControllerManager getParent();

	Logger getLogger();

	InterceptorManager getInterceptorManager();

	void setInterceptorManager(InterceptorManager interceptorManager);

	ValidatorFactory getValidatorFactory();

	void setValidatorFactory(ValidatorFactory validatorFactory);

	ConfigurableApplicationContext getApplicationContext();

	void setApplicationContext(ConfigurableApplicationContext applicationContext);

	void removeController(Class<?> clazz);

	void removeController(String name);

	public static interface InternalUpdate {

		void addControllerAlias(Controller controller, ControllerID alias);

		void removeControllerAlias(Controller controller, ControllerID alias);

	}

}
