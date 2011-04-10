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
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContextImp;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.scope.IOCScope;
import org.brandao.brutos.scope.Scope;
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
public abstract class WebApplicationContext extends ApplicationContext{

    public static final String defaultConfigContext = "brutos-config.xml";

    public static final String  contextConfigName   = "contextConfig";

    private Logger logger;
    private Configuration config;

    public WebApplicationContext(){
        this.config = new Configuration();
    }

    public void initApplicationContext( ServletContext servletContext ){
        long time = System.currentTimeMillis();
        try{
            loadParameters( servletContext );
            loadLogger( servletContext );
            logger.info( "Initializing Brutos root WebApplicationContext" );
            logger.info( "Configuration: " + config.toString() );
            overrideConfig( servletContext );
            configure(config);
            loadInvoker( servletContext );
        }
        catch( Throwable e ){
            servletContext.setAttribute( BrutosConstants.EXCEPTION, e );
        }
        finally{
            if( logger != null )
                logger.info( String.format( "Initialization processed in %d ms",
                        (System.currentTimeMillis()-time) ) );
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

            Scope contextScope = getScopes()
                    .get( ScopeType.APPLICATION );
            
            contextScope.put(
                BrutosConstants.UPLOAD_LISTENER_FACTORY,
                ulfClass.newInstance() );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }

        super.configure(config);
        
    }
    
    private void overrideConfig( ServletContext sce ){

        getScopes().register( ScopeType.APPLICATION.toString(),
                new ApplicationScope( sce ) );
        getScopes().register( ScopeType.FLASH.toString(),
                new FlashScope() );
        getScopes().register( ScopeType.IOC.toString(),
                new IOCScope( new ConfigurableApplicationContextImp(this) ) );
        getScopes().register( ScopeType.REQUEST.toString(),
                new RequestScope() );
        getScopes().register( ScopeType.SESSION.toString(),
                new SessionScope() );
        getScopes().register( ScopeType.PARAM.toString(),
                new ParamScope() );


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
        sc.setAttribute( BrutosConstants.INVOKER,this.invoker);
    }

    private void loadLogger( ServletContext sc ){
        LoggerProvider loggerProvider = LoggerProvider.getProvider(config);
        LoggerProvider.setCurrentLoggerProvider(loggerProvider);
        sc.setAttribute( BrutosConstants.LOGGER,
                                            loggerProvider );
        this.logger = loggerProvider.getLogger( WebApplicationContext.class.getName() );
    }

    private void loadParameters( ServletContext sce ){
        Enumeration initParameters = sce.getInitParameterNames();
        
        while( initParameters.hasMoreElements() ){
            String name = (String) initParameters.nextElement();
            config.setProperty( name, sce.getInitParameter( name ) );
        }
    }
    
    public ServletContext getContext(){
        return null;
    }

    public Form getController(){
        
        return (Form) getScopes()
                .get(ScopeType.REQUEST)
                    .get( BrutosConstants.CONTROLLER );
    }

    public void destroy() {
    }

}
