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

import java.util.Properties;
import javax.servlet.ServletContext;
import org.brandao.brutos.AbstractApplicationContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ServletContextResource;
import org.brandao.brutos.ioc.SpringIOCProvider;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.IOCScope;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.http.DefaultUploadListenerFactory;
import org.brandao.brutos.web.http.HttpRequestParserImp;
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
        implements ConfigurableWebApplicationContext{

    public static final String defaultConfigContext = "WEB-INF/brutos-config.xml";

    public static final String  contextConfigName   = "contextConfig";

    private Logger logger;
    protected ServletContext servletContext;
    
    public AbstractWebApplicationContext(){
    }

    public void configure( Properties config ){
        overrideConfig(config);
        initUploadListener(config);
        initRequestParser(config);
        super.configure(config);
    }

    private void initUploadListener(Properties config){
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
    }

    private void initRequestParser(Properties config){
        try{
            String requestParserName =
                config.getProperty( "org.brandao.brutos.request_parser",
                    HttpRequestParserImp.class.getName() );

            Class rpClass = Class.forName(
                requestParserName,
                true,
                Thread.currentThread().getContextClassLoader() );

            Scope contextScope = getScopes()
                    .get( ScopeType.APPLICATION );

            contextScope.put(
                BrutosConstants.HTTP_REQUEST_PARSER,
                rpClass.newInstance() );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    private void overrideConfig(Properties config){

        getScopes().register( ScopeType.APPLICATION.toString(),
                new ApplicationScope( getContext() ) );
        getScopes().register( ScopeType.FLASH.toString(),
                new FlashScope() );
        getScopes().register( ScopeType.IOC.toString(),
                new IOCScope( this ) );
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

        config.put( "org.brandao.brutos.controller.action_resolver",
                    actionResolverName );

        String iocProvider = config
                .getProperty( "org.brandao.brutos.ioc.provider",
                              SpringIOCProvider.class.getName() );

        config.put( "org.brandao.brutos.ioc.provider",
                    iocProvider );

        String viewProvider = config
                .getProperty( "org.brandao.brutos.ioc.provider",
                              SpringIOCProvider.class.getName() );

        config.put( "org.brandao.brutos.ioc.provider",
                    viewProvider );

    }

    public ServletContext getContext(){
        return this.servletContext;
    }

    public Controller getController(){
        
        return (Controller) getScopes()
                .get(ScopeType.REQUEST)
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

}
