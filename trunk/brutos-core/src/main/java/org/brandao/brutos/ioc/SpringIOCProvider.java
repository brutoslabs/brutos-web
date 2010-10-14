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

package org.brandao.brutos.ioc;

import java.io.FileOutputStream;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSessionEvent;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.ioc.spring.XMLParser;
import org.brandao.brutos.old.programatic.Bean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;

/**
 *
 * @author Afonso Brandao
 */
public class SpringIOCProvider extends IOCProvider{
    
    private WebApplicationContext context;
    private ServletContextListener springServletContex;
    private ServletContextEvent sce;
    private RequestContextListener rcl;
    private Configuration properties;

    public SpringIOCProvider() {
        this.rcl = new RequestContextListener();
    }

    public void configure( Configuration properties, ServletContextEvent sce ){
        this.sce = sce;
        this.properties = properties;
    }

    private synchronized void loadConfig(){
        if( this.context != null )
            return;
        
        boolean userApplicationXML =
                "true".equals( properties.getProperty( "org.brandao.brutos.ioc.spring.auto" ) );

        if( userApplicationXML )
            loadBeans( properties );

        contextClassLoader( properties, sce );
    }

    private void contextClassLoader( Configuration properties, ServletContextEvent sce ){
        String contextClassName = 
                properties.getProperty( 
                    "org.brandao.brutos.ioc.spring.context_loader_listener",
                    "org.springframework.web.context.ContextLoaderListener"
                    );
        try{
            Class<?> appManager = Class.forName( contextClassName, true, Thread.currentThread().getContextClassLoader() );
            springServletContex = (ServletContextListener)appManager.newInstance();
            springServletContex.contextInitialized( sce );
            this.context = org.springframework.web.context.ContextLoader.getCurrentWebApplicationContext();
        }
        catch( ClassNotFoundException e ){
            throw new BrutosException( e );
        }
        catch( InstantiationException e ){
            throw new BrutosException( e );
        }
        catch( IllegalAccessException e ){
            throw new BrutosException( e );
        }
        
    }
    
    public void requestInitialized(ServletRequestEvent sre) {
        rcl.requestInitialized( sre );
    }
    
    public void requestDestroyed(ServletRequestEvent sre) {
        rcl.requestDestroyed( sre );
    }
    
    public void destroy(){
        if( springServletContex != null )
            springServletContex.contextDestroyed( sce );
    }
    
    public void loadBeans( Configuration properties ){
        XMLParser parser = new XMLParser();
        parser.setConfig( properties );
        StringBuffer xml = parser.getBeans( getBeansDefinition() );
        FileOutputStream f = null;
        try{
            String fileName = sce.getServletContext().getRealPath( "/" ) + "WEB-INF/applicationContext.xml";
            f = new FileOutputStream( fileName );
            f.write( xml.toString().getBytes() );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
        finally{
            try{
                if( f != null ){
                    f.flush();
                    f.close();
                }
            }
            catch( Exception e ){}
        }
    }
    
    public void sessionCreated(HttpSessionEvent se) {
        //not-implemented
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        //not-implemented
    }
    
    public boolean containsBeanDefinition( String name ){
        if( this.context == null )
            return super.containsBeanDefinition( name );
        else
            return this.context.containsBeanDefinition(name);
    }
    
    public void addBeanDefinition( Bean bean ){
        bean.getInjectable().setName( bean.getInjectable().getName() );
        super.addBeanDefinition( bean );
    }
    
    public Bean getBeanDefinition( String name ){
        return super.getBeanDefinition( name );
    }

    public Object getBean(String name) {
         if( this.context == null )
            loadConfig();

        return context.getBean( name );
    }
    
}