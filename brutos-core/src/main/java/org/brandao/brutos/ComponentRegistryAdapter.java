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

import java.util.Properties;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ResourceLoader;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.DefaultTypeFactory;
import org.brandao.brutos.type.TypeFactory;

/**
 * 
 * @author Brandao
 */
public class ComponentRegistryAdapter implements ComponentRegistry {

	private final ConfigurableApplicationContext configurableApplicationContext;

	public ComponentRegistryAdapter(
			ConfigurableApplicationContext configurableApplicationContext) {
		this.configurableApplicationContext = configurableApplicationContext;
	}

	public ControllerBuilder registerController(Class<?> classtype) {
		return this.configurableApplicationContext.getControllerManager()
				.addController(classtype);
	}

	public ControllerBuilder registerController(String id, Class<?> classType) {
		return this.configurableApplicationContext.getControllerManager()
				.addController(id, classType);
	}

	public ControllerBuilder registerController(String id, String view,
			boolean resolvedView, Class<?> classType) {
		return this.configurableApplicationContext.getControllerManager()
				.addController(id, view, resolvedView, classType);
	}

	public ControllerBuilder registerController(String id, String view,
			boolean resolvedView, String name, Class<?> classType, String actionId) {
		return this.configurableApplicationContext.getControllerManager()
				.addController(id, view, resolvedView, name, classType,
						actionId);
	}

	public ControllerBuilder registerController(String id, String view,
			boolean resolvedView, DispatcherType dispatcherType, String name,
			Class<?> classType, String actionId) {
		return this.configurableApplicationContext.getControllerManager()
				.addController(id, view, resolvedView, dispatcherType, name,
						classType, actionId);
	}

	public ControllerBuilder registerController(String id, String view,
			boolean resolvedView, DispatcherType dispatcherType, String name,
			Class<?> classType, String actionId, ActionType actionType) {
		return this.configurableApplicationContext.getControllerManager()
				.addController(id, view, resolvedView, dispatcherType, name,
						classType, actionId, actionType);
	}

	public ControllerBuilder registerController(String id, String view,
			DispatcherType dispatcherType, boolean resolvedView, String name,
			Class<?> classType, String actionId, ActionType actionType) {
		return this.configurableApplicationContext.getControllerManager()
				.addController(id, view, dispatcherType, resolvedView, name,
						classType, actionId, actionType);
	}

	public Controller getRegisteredController(Class<?> clazz) {
		return this.configurableApplicationContext.getControllerManager()
				.getController(clazz);
	}

	public Controller getRegisteredController(String name) {
		return this.configurableApplicationContext.getControllerManager()
				.getController(name);
	}

	public InterceptorStackBuilder registerInterceptorStack(String name,
			boolean isDefault) {
		return this.configurableApplicationContext.getInterceptorManager()
				.addInterceptorStack(name, isDefault);
	}

	public InterceptorBuilder registerInterceptor(String name,
			Class<?> interceptor, boolean isDefault) {
		return this.configurableApplicationContext.getInterceptorManager()
				.addInterceptor(name, interceptor, isDefault);
	}

	public Interceptor getRegisteredInterceptor(Class<?> clazz) {
		return this.configurableApplicationContext.getInterceptorManager()
				.getInterceptor(clazz);
	}

	public Interceptor getRegisteredInterceptor(String name) {
		return this.configurableApplicationContext.getInterceptorManager()
				.getInterceptor(name);
	}

	public Resource getResource(String path) {
		return ((ResourceLoader) this.configurableApplicationContext)
				.getResource(path);
	}

	public ClassLoader getClassloader() {
		return ((ResourceLoader) this.configurableApplicationContext)
				.getClassloader();
	}

	public void registerScope(String name, Scope scope) {
		this.configurableApplicationContext.getScopes().register(name, scope);
	}

	public Scope getRegistredScope(String name) {
		return this.configurableApplicationContext.getScopes().get(name);
	}

	public Scope getRegistredScope(ScopeType scopeType) {
		return this.configurableApplicationContext.getScopes().get(scopeType);
	}

	public void registerType(TypeFactory factory) {
		this.configurableApplicationContext.getTypeManager().register(factory);
	}

	public void registerType(Class<?> classType, Class<?> type) {
		this.configurableApplicationContext.getTypeManager().register(
				new DefaultTypeFactory(type, classType));
	}

	public TypeFactory getRegistredType(Class<?> classType) {
		return this.configurableApplicationContext.getTypeManager()
				.getTypeFactory(classType);
	}

	public void registerProperty(String name, String value) {
		this.configurableApplicationContext.getConfiguration().setProperty(
				name, value);
	}

	public String getProperty(String name) {
		return this.configurableApplicationContext.getConfiguration()
				.getProperty(name);
	}

	public Properties getProperties() {
		return this.configurableApplicationContext.getConfiguration();
	}

	public boolean isStandardType(Class<?> clazz) {
		return this.configurableApplicationContext.getTypeManager()
				.isStandardType(clazz);
	}

}
