/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it 
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later 
 * version.
 * You may obtain a copy of the License at
 * 
 * http://www.gnu.org/licenses/gpl.html 
 * 
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied.
 *
 */

package org.brandao.brutos;

import java.util.Map;
import org.brandao.brutos.programatic.InterceptorManager;
import java.util.Properties;
import java.util.Set;
import javax.servlet.ServletContextEvent;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.old.programatic.*;
import org.brandao.brutos.programatic.ControllerManager;
import org.brandao.brutos.scope.CustomScopeConfigurer;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.scope.Scopes;
import org.brandao.brutos.validator.ValidatorProvider;
import org.brandao.brutos.view.ViewProvider;

/**
 *
 * @author Afonso Brandao
 */
public abstract class ApplicationContext {

    private static Logger logger = LoggerProvider.getCurrentLoggerProvider().getLogger(ApplicationContext.class.getName());

    private IOCManager iocManager;
    private IOCProvider iocProvider;
    private WebFrameManager webFrameManager;
    private InterceptorManager interceptorManager;
    private ControllerManager controllerManager;
    private ViewProvider viewProvider;
    private ValidatorProvider validatorProvider;
    private Invoker invoker;
    private Properties configuration;
    private LoggerProvider loggerProvider;
    private ControllerResolver controllerResolver;
    private MethodResolver MethodResolver;
    private MvcResponseFactory responseFactory;
    private MvcRequestFactory requestFactory;

    public ApplicationContext() {
        this.configuration = new Configuration();
    }

    /**
     * @deprecated 
     * @param config
     * @param sce
     */
    public void configure( Configuration config, ServletContextEvent sce ){
    }

    public void configure(){
        configure( this.configuration );
    }
    
    public void configure( Properties config ){
        
        this.configuration = config;
        this.iocManager = new IOCManager();
        this.setIocProvider(IOCProvider.getProvider(config));
        this.getIocProvider().configure(config);
        this.interceptorManager = new InterceptorManager();
        this.webFrameManager = new WebFrameManager( this.interceptorManager, this.iocManager );
        this.controllerResolver = getNewControllerResolver();
        this.MethodResolver = getNewMethodResolver();
        this.responseFactory = getMvcResponseFactory();
        this.responseFactory = getMvcResponseFactory();
        this.validatorProvider = ValidatorProvider.getValidatorProvider(this.getConfiguration());
        this.controllerManager = new ControllerManager(this.interceptorManager, this.getValidatorProvider());
        this.invoker = new Invoker(  this.getControllerResolver(), getIocProvider(), controllerManager, this.getMethodResolver(), this );
        this.viewProvider = ViewProvider.getProvider(this.getConfiguration());

        if( iocProvider.containsBeanDefinition("customScopes") ){
            CustomScopeConfigurer customScopes =
                    (CustomScopeConfigurer)iocProvider.getInstance("customScopes");
            Map scopes = customScopes.getCustomScopes();
            Set i = scopes.keySet();
            for( Object key: i )
                Scopes.register( (String)key,(Scope)scopes.get(key) );
        }

        this.loadIOCManager(iocManager);
        this.loadInterceptorManager(interceptorManager);
        this.loadController(getControllerManager());
        this.loadWebFrameManager(webFrameManager);
    }

    protected ControllerResolver getNewControllerResolver(){
        try{
            ControllerResolver instance = (ControllerResolver) Class.forName(
                    configuration.getProperty(
                    "org.brandao.brutos.controller.class",
                    "org.brandao.brutos.DefaultResolveController"
                ),
                    true,
                    Thread.currentThread().getContextClassLoader()

            ).newInstance();

            return instance;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    protected MvcResponseFactory getMvcResponseFactory(){
        try{
            MvcResponseFactory instance = (MvcResponseFactory) Class.forName(
                    configuration.getProperty(
                    "org.brandao.brutos.controller.response_factory",
                    "org.brandao.brutos.DefaultMvcResponseFactory"
                ),
                    true,
                    Thread.currentThread().getContextClassLoader()

            ).newInstance();

            return instance;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    protected MvcRequestFactory getMvcRequestFactory(){
        try{
            MvcRequestFactory instance = (MvcRequestFactory) Class.forName(
                    configuration.getProperty(
                    "org.brandao.brutos.controller.request_factory",
                    "org.brandao.brutos.DefaultMvcRequestFactory"
                ),
                    true,
                    Thread.currentThread().getContextClassLoader()

            ).newInstance();

            return instance;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    protected MethodResolver getNewMethodResolver(){
        try{
            MethodResolver instance = (MethodResolver) Class.forName(
                    configuration.getProperty(
                    "org.brandao.brutos.controller.method_resolver",
                    "org.brandao.brutos.DefaultMethodResolver"
                ),
                    true,
                    Thread.currentThread().getContextClassLoader()

            ).newInstance();
            return instance;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    public abstract void destroy();

    /**
     * @deprecated 
     * @return
     */
    public IOCManager getIocManager() {
        return iocManager;
    }

    /**
     * @deprecated 
     * @param iocManager
     */
    public void setIocManager(IOCManager iocManager) {
        this.iocManager = iocManager;
    }

    /**
     * @deprecated 
     * @return
     */
    public WebFrameManager getWebFrameManager() {
        return webFrameManager;
    }

    /**
     * @deprecated 
     * @param webFrameManager
     */
    public void setWebFrameManager(WebFrameManager webFrameManager) {
        this.webFrameManager = webFrameManager;
    }

    public InterceptorManager getInterceptorManager() {
        return interceptorManager;
    }

    public void setInterceptorManager(InterceptorManager interceptorManager) {
        this.interceptorManager = interceptorManager;
    }

    /**
     * @deprecated 
     * @param iocManager
     */
    protected abstract void loadIOCManager( IOCManager iocManager );

    /**
     * @deprecated 
     * @param webFrameManager
     */
    protected abstract void loadWebFrameManager( WebFrameManager webFrameManager );

    protected abstract void loadInterceptorManager( InterceptorManager interceptorManager );
    
    protected abstract void loadController( ControllerManager controllerManager );

    public static ApplicationContext getCurrentApplicationContext(){
        Scope requestScope = Scopes.get(ScopeType.REQUEST);

        if( requestScope == null )
            throw new BrutosException( "scope not configured: " + ScopeType.REQUEST.toString() );

        ApplicationContext app = (ApplicationContext)
                        requestScope.get( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE );

        return app;
    }

    public Object getController( Class controllerClass ){
        return null;
    }
    
    public ControllerManager getControllerManager() {
        return controllerManager;
    }

    public ViewProvider getViewProvider() {
        return viewProvider;
    }

    public ValidatorProvider getValidatorProvider() {
        return validatorProvider;
    }

    public Invoker getInvoker() {
        return invoker;
    }

    public Properties getConfiguration() {
        return configuration;
    }

    public LoggerProvider getLoggerProvider() {
        return loggerProvider;
    }

    public IOCProvider getIocProvider() {
        return iocProvider;
    }

    public void setIocProvider(IOCProvider iocProvider) {
        this.iocProvider = iocProvider;
    }

    public ControllerResolver getControllerResolver() {
        return controllerResolver;
    }

    public MethodResolver getMethodResolver() {
        return MethodResolver;
    }

    public MvcResponse getMvcResponse() {
        return this.responseFactory.getCurrentResponse();
    }

    public MvcRequest getMvcRequest() {
        return this.requestFactory.getCurrentRequest();
    }

}
