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

import java.util.Properties;
import javax.servlet.ServletContext;
import org.brandao.brutos.*;
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

    public AbstractWebApplicationContext( ApplicationContext parent ) {
        super(parent);
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

    private void initRequestParser(Properties config){
        try{
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

    protected void loadScopes(){
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

}
