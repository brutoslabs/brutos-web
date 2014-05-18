/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

import java.util.List;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.brandao.brutos.*;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.web.io.ServletContextResource;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.scope.IOCScope;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.view.JSPViewProvider;
import org.brandao.brutos.web.http.DefaultUploadListenerFactory;
import org.brandao.brutos.web.ioc.SpringIOCProvider;
import org.brandao.brutos.web.scope.ApplicationScope;
import org.brandao.brutos.web.scope.FlashScope;
import org.brandao.brutos.web.scope.ParamScope;
import org.brandao.brutos.web.scope.RequestScope;
import org.brandao.brutos.web.scope.SessionScope;

/**
 *
 * @author Afonso Brandao
 */
public abstract class AbstractWebApplicationContext
        extends AbstractApplicationContext
        implements ConfigurableWebApplicationContext {

    protected ServletContext servletContext;
    
    protected String[] locations;
    
    protected Resource[] resources;
    
    public AbstractWebApplicationContext(){
    }

    public AbstractWebApplicationContext( ApplicationContext parent ) {
        super(parent);
    }
    
    public void setLocations(String[] locations) {
        this.locations = locations;
    }

    public void setResources(Resource[] resources) {
        this.resources = resources;
    }

    public String[] getLocations() {
        return this.locations;
    }

    public Resource[] getResources() {
        return this.resources;
    }

    protected void initUploadListener(){
        try{
            Properties config = this.getConfiguration();
            String uploadListenerFactoryName =
                config.getProperty( BrutosConstants.UPLOAD_LISTENER_CLASS,
                    DefaultUploadListenerFactory.class.getName() );

            Class ulfClass = Class.forName(
                uploadListenerFactoryName,
                true,
                Thread.currentThread().getContextClassLoader() );

            Scope contextScope = getScopes()
                    .get( WebScopeType.APPLICATION );

            contextScope.put(
                BrutosConstants.UPLOAD_LISTENER_FACTORY,
                ClassUtil.getInstance(ulfClass) );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    protected void initRequestParser(){
        try{
            Properties config = this.getConfiguration();
            
            String requestParserName =
                config.getProperty( BrutosConstants.REQUEST_PARSER_CLASS,
                    BrutosConstants.DEFAULT_REQUEST_PARSER );

            Class rpClass = Class.forName(
                requestParserName,
                true,
                Thread.currentThread().getContextClassLoader() );

            Scope contextScope = getScopes()
                    .get( WebScopeType.APPLICATION );

            contextScope.put(
                BrutosConstants.HTTP_REQUEST_PARSER,
                ClassUtil.getInstance(rpClass) );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    protected void initScopes(){
        getScopes().register( WebScopeType.APPLICATION.toString(),
                new ApplicationScope( getContext() ) );
        getScopes().register( WebScopeType.FLASH.toString(),
                new FlashScope() );
        getScopes().register( WebScopeType.IOC.toString(),
                new IOCScope() );
        getScopes().register( WebScopeType.REQUEST.toString(),
                new RequestScope() );
        getScopes().register( WebScopeType.SESSION.toString(),
                new SessionScope() );
        getScopes().register( WebScopeType.PARAM.toString(),
                new ParamScope() );
    }
    
    protected void overrideConfig(){
        
        String tmp;
        Properties config = this.getConfiguration();
        
        tmp = config
                .getProperty( "org.brandao.brutos.controller.class", 
                              WebControllerResolver.class.getName() );

        config.put( "org.brandao.brutos.controller.class" ,
                    tmp );

        tmp = config
                .getProperty( "org.brandao.brutos.controller.response_factory",
                              WebMvcResponseFactory.class.getName() );

        config.put( "org.brandao.brutos.controller.response_factory",
                    tmp );

        tmp = config
                .getProperty( "org.brandao.brutos.controller.request_factory",
                              WebMvcRequestFactory.class.getName() );

        config.put( "org.brandao.brutos.controller.request_factory",
                    tmp );

        tmp = config
                .getProperty( "org.brandao.brutos.controller.action_resolver",
                              WebActionResolver.class.getName() );

        config.put( "org.brandao.brutos.controller.action_resolver",
                    tmp );

        tmp = config
                .getProperty( "org.brandao.brutos.ioc.provider",
                              SpringIOCProvider.class.getName() );

        config.put( "org.brandao.brutos.ioc.provider",
                    tmp );

        tmp = config
                .getProperty( "org.brandao.brutos.view.provider",
                              JSPViewProvider.class.getName() );

        config.put( "org.brandao.brutos.view.provider",
                    tmp );

        tmp = config
                .getProperty( "org.brandao.brutos.view.prefix",
                              "/WEB-INF" );
        
        config.put( "org.brandao.brutos.view.prefix",
                    tmp );

        tmp = config
                .getProperty( "org.brandao.brutos.view.suffix",
                              ".jsp" );
        
        config.put( "org.brandao.brutos.view.suffix",
                    tmp );

        tmp = config
                .getProperty( "org.brandao.brutos.view.index",
                              "index" );
        
        config.put( "org.brandao.brutos.view.index",
                    tmp );

        tmp = config
                .getProperty( "org.brandao.brutos.view.separator",
                              "/" );
        
        config.put( "org.brandao.brutos.view.separator",
                    tmp );

        tmp = config
                .getProperty( BrutosConstants.INVOKER_CLASS,
                              WebInvoker.class.getName() );
        
        config.put( BrutosConstants.INVOKER_CLASS,
                    tmp );
        
        tmp = config
                .getProperty( BrutosConstants.ACTION_TYPE,
                              ActionType.HIERARCHY.name() );
        
        config.put( BrutosConstants.ACTION_TYPE,
                    tmp );

        tmp = config
                .getProperty( BrutosConstants.CONTROLLER_MANAGER_CLASS,
                              WebControllerManager.class.getName() );
        
        config.put( BrutosConstants.CONTROLLER_MANAGER_CLASS,
                    tmp );

    }

    public ServletContext getContext(){
        return this.servletContext;
    }

    public Controller getController(){
        
        return (Controller) getScopes()
                .get(WebScopeType.REQUEST)
                    .get( BrutosConstants.CONTROLLER );
    }

    protected Resource getContextResource( String path ){
        return new ServletContextResource(this.servletContext, path);
    }

    public void destroy() {
        this.servletContext = null;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ControllerBuilder registerController( Class classtype ){
        return this.controllerManager.addController(classtype);
    }

    public ControllerBuilder registerController( String id, Class classType ){
        return this.controllerManager.addController(id, classType);
    }
    
    public ControllerBuilder registerController( String id, String view, Class classType ){
        return this.controllerManager.addController(id, view, classType);
    }
    
    public ControllerBuilder registerController( String id, String view,
           String name, Class classType, String actionId ){
        return this.controllerManager.addController( id, view,
           name, classType, actionId );
    }

    public ControllerBuilder registerController( String id, String view, DispatcherType dispatcherType,
            String name, Class classType, String actionId ){
        return this.controllerManager.addController( id, view, dispatcherType,
            name, classType, actionId );
    }
    
    public ControllerBuilder registerController( String id, String view, DispatcherType dispatcherType,
            String name, Class classType, String actionId, ActionType actionType ){
        return this.controllerManager.addController( id, view, dispatcherType,
            name, classType, actionId, actionType );
    }
    
    public Controller getRegisteredController(Class clazz){
        return super.controllerManager.getController(clazz);
    }
    
    public Controller getRegisteredController(String name){
        return super.controllerManager.getController(name);
    }

    public InterceptorStackBuilder registerInterceptorStack( String name, boolean isDefault ){
        return this.interceptorManager.addInterceptorStack(name, isDefault);
    }
    
    public InterceptorBuilder registerInterceptor( String name, Class interceptor, boolean isDefault ){
        return this.interceptorManager.addInterceptor(name, interceptor, isDefault);
    }
    
    public Interceptor getRegisteredInterceptor(Class clazz){
        return this.interceptorManager.getInterceptor(clazz);
    }
    
    public Interceptor getRegisteredInterceptor(String name){
        return this.interceptorManager.getInterceptor(name);
    }

    public void flush(){

        this.initLogger();
        
        this.overrideConfig();
        
        this.initInstances();
        
        this.initScopes();
        
        this.invoker.flush();
        
        this.loadDefinitions(new ComponentRegistryAdapter(this));

        this.initComponents();

        this.initUploadListener();
        
        this.initRequestParser();
    }
    
}
