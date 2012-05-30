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
import org.brandao.brutos.WebScopeType;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ServletContextResource;
import org.brandao.brutos.ioc.SpringIOCProvider;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.IOCScope;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.view.JSPViewProvider;
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
        super.configure(config);
        initUploadListener(config);
        initRequestParser(config);
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
                    .get( WebScopeType.APPLICATION );

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
                    .get( WebScopeType.APPLICATION );

            contextScope.put(
                BrutosConstants.HTTP_REQUEST_PARSER,
                rpClass.newInstance() );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    protected void loadScopes(){
        getScopes().register( WebScopeType.APPLICATION.toString(),
                new ApplicationScope( getContext() ) );
        getScopes().register( WebScopeType.FLASH.toString(),
                new FlashScope() );
        getScopes().register( WebScopeType.IOC.toString(),
                new IOCScope( this ) );
        getScopes().register( WebScopeType.REQUEST.toString(),
                new RequestScope() );
        getScopes().register( WebScopeType.SESSION.toString(),
                new SessionScope() );
        getScopes().register( WebScopeType.PARAM.toString(),
                new ParamScope() );
    }
    
    private void overrideConfig(Properties config){
        String tmp;
        
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
                              "WEB-INF/" );
        
        config.put( "org.brandao.brutos.view.prefix",
                    tmp );

        tmp = config
                .getProperty( "org.brandao.brutos.view.suffix",
                              ".jsp" );
        
        config.put( "org.brandao.brutos.view.suffix",
                    tmp );

        tmp = config
                .getProperty( "org.brandao.brutos.view.controller",
                              "index" );
        
        config.put( "org.brandao.brutos.view.controller",
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

}
