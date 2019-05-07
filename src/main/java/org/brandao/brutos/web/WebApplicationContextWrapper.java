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

package org.brandao.brutos.web;

import java.util.Properties;

import javax.servlet.ServletContext;

import org.brandao.brutos.ActionResolver;
import org.brandao.brutos.ActionType;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.CodeGenerator;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ConfigurableRenderView;
import org.brandao.brutos.ConfigurableRequestParser;
import org.brandao.brutos.ConfigurableViewResolver;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.ControllerResolver;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.InterceptorBuilder;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.InterceptorStackBuilder;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.MvcRequestFactory;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.MvcResponseFactory;
import org.brandao.brutos.ObjectFactory;
import org.brandao.brutos.RenderView;
import org.brandao.brutos.RequestParser;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.TypeManager;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.ViewResolver;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.TypeFactory;

/**
 * 
 * @author Brandao
 */
@SuppressWarnings("deprecation")
public class WebApplicationContextWrapper 
        extends AbstractWebApplicationContext{

    protected ConfigurableWebApplicationContext applicationContext;

    public WebApplicationContextWrapper(ConfigurableWebApplicationContext app){
        this.applicationContext = app;
    }

    public void setServletContext(ServletContext servletContext){
        if( applicationContext instanceof ConfigurableWebApplicationContext )
            ((ConfigurableWebApplicationContext)this.applicationContext).
                    setServletContext(servletContext);
    }

	@Override
	public void setLocations(String[] locations) {
		applicationContext.setLocations(locations);
	}

	@Override
	public void setResources(Resource[] resources) {
		applicationContext.setResources(resources);
	}

	@Override
	public String[] getLocations() {
		return applicationContext.getLocations();
	}

	@Override
	public Resource[] getResources() {
		return applicationContext.getResources();
	}

	@Override
	public ServletContext getContext() {
		return applicationContext.getContext();
	}

	@Override
	public void destroy() {
		applicationContext.destroy();
	}

	@Override
	public ControllerBuilder registerController(Class<?> classtype) {
		return applicationContext.registerController(classtype);
	}

	@Override
	public ControllerBuilder registerController(String id, Class<?> classType) {
		return applicationContext.registerController(id, classType);
	}

	@Override
	public ControllerBuilder registerController(String id, String view,
			boolean resolvedView, Class<?> classType) {
		return applicationContext.registerController(id, view, resolvedView, classType);
	}

	@Override
	public ControllerBuilder registerController(String id, String view,
			boolean resolvedView, String name, Class<?> classType,
			String actionId) {
		return applicationContext.registerController(id, view, resolvedView, name, classType,
				actionId);
	}

	@Override
	public ControllerBuilder registerController(String id, String view,
			boolean resolvedView, DispatcherType dispatcherType, String name,
			Class<?> classType, String actionId) {
		return applicationContext.registerController(id, view, resolvedView, dispatcherType, name,
				classType, actionId);
	}

	@Override
	public ControllerBuilder registerController(String id, String view,
			boolean resolvedView, DispatcherType dispatcherType, String name,
			Class<?> classType, String actionId, ActionType actionType) {
		return applicationContext.registerController(id, view, resolvedView, dispatcherType, name,
				classType, actionId, actionType);
	}

	@Override
	public ControllerBuilder registerController(String id, String view,
			DispatcherType dispatcherType, boolean resolvedView, String name,
			Class<?> classType, String actionId, ActionType actionType) {
		return applicationContext.registerController(id, view, dispatcherType, resolvedView, name,
				classType, actionId, actionType);
	}

	@Override
	public Controller getRegisteredController(Class<?> clazz) {
		return applicationContext.getRegisteredController(clazz);
	}

	@Override
	public Controller getRegisteredController(String name) {
		return applicationContext.getRegisteredController(name);
	}

	@Override
	public InterceptorStackBuilder registerInterceptorStack(String name,
			boolean isDefault) {
		return applicationContext.registerInterceptorStack(name, isDefault);
	}

	@Override
	public InterceptorBuilder registerInterceptor(String name,
			Class<?> interceptor, boolean isDefault) {
		return applicationContext.registerInterceptor(name, interceptor, isDefault);
	}

	@Override
	public Interceptor getRegisteredInterceptor(Class<?> clazz) {
		return applicationContext.getRegisteredInterceptor(clazz);
	}

	@Override
	public Interceptor getRegisteredInterceptor(String name) {
		return applicationContext.getRegisteredInterceptor(name);
	}

	@Override
	public void registerScope(String name, Scope scope) {
		applicationContext.registerScope(name, scope);
	}

	@Override
	public Scope getRegistredScope(String name) {
		return applicationContext.getRegistredScope(name);
	}

	@Override
	public Scope getRegistredScope(ScopeType scopeType) {
		return applicationContext.getRegistredScope(scopeType);
	}

	@Override
	public void registerType(TypeFactory factory) {
		applicationContext.registerType(factory);
	}

	@Override
	public void registerType(Class<?> classType, Class<?> type) {
		applicationContext.registerType(classType, type);
	}

	@Override
	public TypeFactory getRegistredType(Class<?> classType) {
		return applicationContext.getRegistredType(classType);
	}

	@Override
	public void registerProperty(String name, String value) {
		applicationContext.registerProperty(name, value);
	}

	@Override
	public String getProperty(String name) {
		return applicationContext.getProperty(name);
	}

	@Override
	public Properties getProperties() {
		return applicationContext.getProperties();
	}

	@Override
	public void flush() {
		applicationContext.flush();
	}

	@Override
	protected void loadDefinitions(ComponentRegistry registry) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Properties getConfiguration() {
		return applicationContext.getConfiguration();
	}

	@Override
	public LoggerProvider getLoggerProvider() {
		return applicationContext.getLoggerProvider();
	}

	@Override
	public MvcResponse getMvcResponse() {
		return applicationContext.getMvcResponse();
	}

	@Override
	public MvcRequest getMvcRequest() {
		return applicationContext.getMvcRequest();
	}

	@Override
	public Scopes getScopes() {
		return applicationContext.getScopes();
	}

	@Override
	public MvcRequestFactory getRequestFactory() {
		return applicationContext.getRequestFactory();
	}

	@Override
	public MvcResponseFactory getResponseFactory() {
		return applicationContext.getResponseFactory();
	}

	@Override
	public void setInterceptorManager(InterceptorManager interceptorManager) {
		applicationContext.setInterceptorManager(interceptorManager);
	}

	@Override
	public void setRenderView(ConfigurableRenderView renderView) {
		applicationContext.setRenderView(renderView);
	}

	@Override
	public RenderView getRenderView() {
		return applicationContext.getRenderView();
	}

	@Override
	public ValidatorFactory getValidatorFactory() {
		return applicationContext.getValidatorFactory();
	}

	@Override
	public Invoker getInvoker() {
		return applicationContext.getInvoker();
	}

	@Override
	public void setInvoker(Invoker value) {
		applicationContext.setInvoker(value);
	}

	@Override
	public void setConfiguration(Properties config) {
		applicationContext.setConfiguration(config);
	}

	@Override
	public void setObjectFactory(ObjectFactory objectFactory) {
		applicationContext.setObjectFactory(objectFactory);
	}

	@Override
	public InterceptorManager getInterceptorManager() {
		return applicationContext.getInterceptorManager();
	}

	@Override
	public ControllerManager getControllerManager() {
		return applicationContext.getControllerManager();
	}

	@Override
	public ObjectFactory getObjectFactory() {
		return applicationContext.getObjectFactory();
	}

	@Override
	public ControllerResolver getControllerResolver() {
		return applicationContext.getControllerResolver();
	}

	@Override
	public ActionResolver getActionResolver() {
		return applicationContext.getActionResolver();
	}

	@Override
	public CodeGenerator getCodeGenerator() {
		return applicationContext.getCodeGenerator();
	}

	@Override
	public void setCodeGenerator(CodeGenerator codeGenerator) {
		applicationContext.setCodeGenerator(codeGenerator);
	}

	@Override
	public ViewResolver getViewResolver() {
		return applicationContext.getViewResolver();
	}

	@Override
	public void setViewResolver(ConfigurableViewResolver viewResolver) {
		applicationContext.setViewResolver(viewResolver);
	}

	@Override
	public void setRequestParser(ConfigurableRequestParser value) {
		applicationContext.setRequestParser(value);
	}

	@Override
	public RequestParser getRequestParser() {
		return applicationContext.getRequestParser();
	}

	@Override
	public Object getController(Class<?> clazz) {
		return applicationContext.getController(clazz);
	}

	@Override
	public void setParent(ApplicationContext applicationContext) {
		this.applicationContext.setParent(applicationContext);
	}

	@Override
	public ApplicationContext getParent() {
		return applicationContext.getParent();
	}

	@Override
	public Object getBean(Class<?> clazz) {
		return applicationContext.getBean(clazz);
	}

	@Override
	public Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	@Override
	public TypeManager getTypeManager() {
		return applicationContext.getTypeManager();
	}

	@Override
	public boolean isStandardType(Class<?> clazz) {
		return applicationContext.isStandardType(clazz);
	}
    
}
