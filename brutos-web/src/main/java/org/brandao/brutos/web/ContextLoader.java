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

        ConfigurableWebApplicationContext app =
                createApplicationContext(servletContext);

        app.setServletContext(servletContext);

        Properties config = getConfiguration(servletContext);
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

    private ConfigurableWebApplicationContext createApplicationContext(
            ServletContext servletContext){

        Class clazz = getApplicationContextClass(servletContext);

        if(ConfigurableWebApplicationContext.class.isAssignableFrom(clazz)){
            try{
                ConfigurableWebApplicationContext app =
                        (ConfigurableWebApplicationContext) clazz.newInstance();


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
