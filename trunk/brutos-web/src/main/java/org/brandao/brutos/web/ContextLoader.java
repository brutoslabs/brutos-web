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

import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;

/**
 *
 * @author Brandao
 */
public class ContextLoader {

    public static final String CONTEXT_CLASS = "context_class";
    private Logger logger;

    private static final ConcurrentHashMap<ClassLoader,WebApplicationContext>
            currentWebApplicationContext;

    static{
        currentWebApplicationContext =
                new ConcurrentHashMap<ClassLoader,WebApplicationContext>();
    }

    public void init( ServletContext servletContext ){

        if( servletContext.getAttribute( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE ) != null ){
            throw new IllegalStateException(
                            "Cannot initialize context because there is already "
                            + "a root application context present - " +
                            "check whether you have multiple ContextLoader "
                            + "definitions in your web.xml!");
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        WebApplicationContext app =
                    createApplicationContext(servletContext);
        
        ((ConfigurableWebApplicationContext)app)
                .setServletContext(servletContext);

        Properties config = getConfiguration(servletContext);
        config.setProperty(BrutosConstants.WEB_APPLICATION_CLASS, app.getClass().getName());
        
        initLogger(config);

        logger.info( "Initializing Brutos root WebApplicationContext" );
        logger.info( "Configuration: " + config.toString() );
        app.configure(config);
        
        currentWebApplicationContext
                    .put(classLoader, app);

        
    }

    private void initLogger(Properties config){
        LoggerProvider loggerProvider = LoggerProvider.getProvider(config);
        LoggerProvider.setCurrentLoggerProvider(loggerProvider);
        this.logger = loggerProvider.getLogger( ContextLoader.class.getName() );
    }

    private Properties getConfiguration( ServletContext servletContext ){
        Configuration config = new Configuration();

        Enumeration initParameters = servletContext.getInitParameterNames();

        while( initParameters.hasMoreElements() ){
            String name = (String) initParameters.nextElement();
            config.setProperty( name, servletContext.getInitParameter( name ) );
        }

        return config;
    }

    private WebApplicationContext createApplicationContext(
            ServletContext servletContext){

        Class clazz = getApplicationContextClass(servletContext);

        if(ConfigurableWebApplicationContext.class.isAssignableFrom(clazz)){
            try{
                WebApplicationContext app =
                        (WebApplicationContext) clazz.newInstance();


                return app;
            }
            catch( Exception e ){
                throw new BrutosException("unable to create instance: " +
                        clazz.getName(),e);
            }
        }
        else
            throw new BrutosException("web application is not valid:"+
                    clazz.getName());
    }

    private Class getApplicationContextClass(ServletContext servletContext){
        String contextClassName = servletContext.getInitParameter(CONTEXT_CLASS);

        if( contextClassName != null )
            return this.getContextClass(contextClassName);
        else
            return this.getContextClass(XMLWebApplicationContext.class.getName());
    }

    private Class getContextClass( String contextClassName ){
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(contextClassName);
        } catch (ClassNotFoundException ex) {
            throw new BrutosException( "Failed to load: " + contextClassName, ex );
        }
    }

    public static WebApplicationContext getCurrentWebApplicationContext(){
        
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();

        if( currentWebApplicationContext.containsKey(classLoader) )
            return currentWebApplicationContext.get(classLoader);
        else
            return null;
    }

    public void destroy( ServletContext servletContext ){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if( currentWebApplicationContext.containsKey(classLoader) ){
            try{
                currentWebApplicationContext.get(classLoader).destroy();
            }
            finally{
                currentWebApplicationContext.remove(classLoader);
            }
        }
    }
}
