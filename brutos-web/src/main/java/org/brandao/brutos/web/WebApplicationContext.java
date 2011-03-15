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

package org.brandao.brutos.web;

import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.ActionResolver;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.MvcRequestFactory;
import org.brandao.brutos.MvcResponseFactory;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.ControllerResolver;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ServletContextResource;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.scope.IOCScope;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.scope.Scopes;
import org.brandao.brutos.validator.ValidatorProvider;
import org.brandao.brutos.view.ViewProvider;
import org.brandao.brutos.web.http.DefaultUploadListenerFactory;
import org.brandao.brutos.web.scope.ApplicationScope;
import org.brandao.brutos.web.scope.FlashScope;
import org.brandao.brutos.web.scope.ParamScope;
import org.brandao.brutos.web.scope.RequestScope;
import org.brandao.brutos.web.scope.SessionScope;

/**
 *
 * @author Afonso Brandao
 */
public class WebApplicationContext extends ApplicationContext
        implements ConfigurableApplicationContext{

    private Logger logger;
    private Configuration config;
    private ServletContext servletContext;
    private ServletContextResource resource;

    public WebApplicationContext(){
        this.config = new Configuration();
    }

    public synchronized void start( ServletContextEvent sce ){
        
        if( sce.getServletContext().getAttribute( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE ) != null ){
            throw new IllegalStateException(
                            "Cannot initialize context because there is already a root application context present - " +
                            "check whether you have multiple ContextLoader definitions in your web.xml!");
        }
        
        sce.getServletContext().setAttribute( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE, this );
        loadContext( sce );
    }

    private void loadContext( ServletContextEvent sce ){
        long time = System.currentTimeMillis();
        try{
            this.servletContext = sce.getServletContext();
            this.resource =
                new ServletContextResource(
                    servletContext,
                    "/" );
            
            loadParameters( sce );
            loadLogger( sce.getServletContext() );
            logger.info( "Initializing Brutos root WebApplicationContext" );
            logger.info( "Configuration: " + config.toString() );
            overrideConfig( sce );
            configure(config);
            loadInvoker( sce.getServletContext() );
        }
        catch( Throwable e ){
            sce.getServletContext().setAttribute( BrutosConstants.EXCEPTION, e );
        }
        finally{
            if( logger != null )
                logger.info( String.format( "Initialization processed in %d ms", (System.currentTimeMillis()-time) ) );
        }
    }

    public void configure( Properties config ){
        
        try{
            String uploadListenerFactoryName = 
                config.getProperty( "org.brandao.brutos.upload_listener_factory",
                    DefaultUploadListenerFactory.class.getName() );

            Class ulfClass = Class.forName(
                uploadListenerFactoryName,
                true,
                Thread.currentThread().getContextClassLoader() );

            Scope contextScope = Scopes.get( ScopeType.APPLICATION );
            contextScope.put(
                BrutosConstants.UPLOAD_LISTENER_FACTORY,
                ulfClass.newInstance() );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }

        super.configure(config);
        
    }
    
    private void overrideConfig( ServletContextEvent sce ){

        IOCProvider iocProvider = getIocProvider();
        Scopes.register( ScopeType.APPLICATION.toString(), new ApplicationScope( sce.getServletContext() ) );
        Scopes.register( ScopeType.FLASH.toString() , new FlashScope() );
        Scopes.register( ScopeType.IOC.toString() , new IOCScope( this ) );
        Scopes.register( ScopeType.REQUEST.toString() , new RequestScope() );
        Scopes.register( ScopeType.SESSION.toString() , new SessionScope() );
        Scopes.register( ScopeType.PARAM.toString() , new ParamScope() );


        String controllerResolverName = config
                .getProperty( "org.brandao.brutos.controller.class", 
                              WebControllerResolver.class.getName() );

        config.put( "org.brandao.brutos.controller.class" ,
                    controllerResolverName );

        String responseFactory = config
                .getProperty( "org.brandao.brutos.controller.response_factory",
                              WebMvcResponseFactory.class.getName() );

        config.put( "org.brandao.brutos.controller.response_factory",
                    responseFactory );

        String requestFactory = config
                .getProperty( "org.brandao.brutos.controller.request_factory",
                              WebMvcRequestFactory.class.getName() );

        config.put( "org.brandao.brutos.controller.request_factory",
                    requestFactory );

        String actionResolverName = config
                .getProperty( "org.brandao.brutos.controller.action_resolver",
                              WebActionResolver.class.getName() );

        config.put( "org.brandao.brutos.controller.method_resolver",
                    actionResolverName );

    }

    private void loadInvoker( ServletContext sc ){
        sc.setAttribute( BrutosConstants.INVOKER,this.getInvoker());
    }

    private void loadLogger( ServletContext sc ){
        LoggerProvider loggerProvider = LoggerProvider.getProvider(config);
        LoggerProvider.setCurrentLoggerProvider(loggerProvider);
        sc.setAttribute( BrutosConstants.LOGGER,
                                            loggerProvider );
        this.logger = loggerProvider.getLogger( WebApplicationContext.class.getName() );
    }

    private void loadParameters( ServletContextEvent sce ){
        ServletContext context = sce.getServletContext();
        Enumeration initParameters = context.getInitParameterNames();
        
        while( initParameters.hasMoreElements() ){
            String name = (String) initParameters.nextElement();
            config.setProperty( name, context.getInitParameter( name ) );
        }
    }
    
    public synchronized void stop( ServletContextEvent sce ){
        destroy();
    }

    public static ApplicationContext getCurrentApplicationContext(){
        /*
        Scope contextScope = Scopes.get(ScopeType.APPLICATION);
*/
        ApplicationContext app = /*
            (ApplicationContext) contextScope
                .get( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE );
            */
        
            (ApplicationContext)ContextLoaderListener
                .currentContext
                    .getAttribute(
                        BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE );
        

        if( app == null ){
            throw new IllegalStateException(
                    "Unable to initialize the servlet was not configured for the application context root - " +
                    "make sure you have defined in your web.xml ContextLoader!"
            );
        }

        Throwable ex = (Throwable)ContextLoaderListener
                .currentContext
                .getAttribute( BrutosConstants.EXCEPTION );

        if( ex != null )
            throw new BrutosException( ex );

        return app;
    }

    /*
    public static WebApplicationContext getCurrentWebApplicationContext(){
        WebApplicationContext brutosCore =
            (WebApplicationContext)ContextLoaderListener
                .currentContext
                    .getAttribute(
                        BrutosConstants.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE );

        if( brutosCore == null ){
            throw new IllegalStateException(
                    "Unable to initialize the servlet was not configured for the application context root - " +
                    "make sure you have defined in your web.xml ContextLoader!"
            );
        }

        Throwable ex =
            (Throwable)ContextLoaderListener
                .currentContext
                    .getAttribute( BrutosConstants.EXCEPTION );

        if( ex != null )
            throw new BrutosException( ex );

        return brutosCore;
    }
    */
    
    public ServletContext getContext(){
        return ContextLoaderListener.currentContext;
    }

    public HttpServletRequest getRequest(){
        return (HttpServletRequest) ContextLoaderListener.currentRequest.get();
    }
    
    public Form getController(){
        WebApplicationContext brutosInstance = (WebApplicationContext) ApplicationContext.getCurrentApplicationContext();
        return (Form) brutosInstance.getRequest()
                .getAttribute( BrutosConstants.CONTROLLER );
    }

    /*
    public ControllerResolver getResolveController() {
        WebApplicationContext brutosInstance = (WebApplicationContext) ApplicationContext.getCurrentApplicationContext();
        return (ControllerResolver) brutosInstance
                .getContext()
                    .getAttribute( BrutosConstants.CONTROLLER_RESOLVER );
    }
    */

    public void destroy() {
    }

    protected void loadIOCManager(IOCManager iocManager) {
    }

    protected void loadWebFrameManager(WebFrameManager webFrameManager) {
    }

    protected void loadInterceptorManager(InterceptorManager interceptorManager) {
    }

    protected void loadController(ControllerManager controllerManager) {
    }


    public void setIocManager(IOCManager iocManager) {
        this.iocManager = iocManager;
    }

    public WebFrameManager getWebFrameManager() {
        return webFrameManager;
    }

    public void setWebFrameManager(WebFrameManager webFrameManager) {
        this.webFrameManager = webFrameManager;
    }

    public InterceptorManager getInterceptorManager() {
        return interceptorManager;
    }

    public void setInterceptorManager(InterceptorManager interceptorManager) {
        this.interceptorManager = interceptorManager;
    }

    public ControllerManager getControllerManager() {
        return controllerManager;
    }

    public void setConfiguration( Properties config ){
        this.configuration = config;
    }

    public void setIocProvider(IOCProvider iocProvider) {
        this.iocProvider = iocProvider;
    }

    public MvcRequestFactory getRequestFactory() {
        return this.requestFactory;
    }

    public MvcResponseFactory getResponseFactory() {
        return this.responseFactory;
    }

    public ViewProvider getViewProvider() {
        return this.viewProvider;
    }

    public ValidatorProvider getValidatorProvider() {
        return this.validatorProvider;
    }

    public Invoker getInvoker() {
        return this.invoker;
    }

    public IOCManager getIocManager() {
        return this.iocManager;
    }

    public IOCProvider getIocProvider() {
        return this.iocProvider;
    }

    public ControllerResolver getControllerResolver() {
        return this.controllerResolver;
    }

    public ActionResolver getActionResolver() {
        return this.actionResolver;
    }

    protected Resource getContextResource( String path ){
        try{
            return this.resource.getRelativeResource(path);
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }
    }

}
