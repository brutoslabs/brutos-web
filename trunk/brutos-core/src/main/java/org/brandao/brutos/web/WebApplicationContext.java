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
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.programatic.ControllerManager;
import org.brandao.brutos.programatic.InterceptorManager;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.scope.IOCScope;
import org.brandao.brutos.scope.Scopes;
import org.brandao.brutos.web.scope.ApplicationScope;
import org.brandao.brutos.web.scope.FlashScope;
import org.brandao.brutos.web.scope.ParamScope;
import org.brandao.brutos.web.scope.RequestScope;
import org.brandao.brutos.web.scope.SessionScope;

/**
 *
 * @author Afonso Brandao
 */
public class WebApplicationContext extends ApplicationContext{

    private Logger logger;
    Configuration config;

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
            loadParameters( sce );
            loadLogger( sce.getServletContext() );
            logger.info( "Initializing Brutos root WebApplicationContext" );
            logger.info( "Configuration: " + config.toString() );
            overrideConfig( sce );
            //this.appContext = new XMLApplicationContext();
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

    private void overrideConfig( ServletContextEvent sce ){

        IOCProvider iocProvider = getIocProvider();
        Scopes.register( ScopeType.APPLICATION.toString(), new ApplicationScope( sce.getServletContext() ) );
        Scopes.register( ScopeType.FLASH.toString() , new FlashScope() );
        Scopes.register( ScopeType.IOC.toString() , new IOCScope( iocProvider ) );
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

}
