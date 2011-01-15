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

import java.util.HashMap;
import java.util.Map;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;

/**
 * 
 * @author Afonso Brandao
 */
public class ContextLoaderListener implements ServletContextListener,
        HttpSessionListener, ServletRequestListener{
    
    private WebApplicationContext brutosInstance;
    public static ThreadLocal<ServletRequest> currentRequest;
    public static ServletContext currentContext;
    
    public ContextLoaderListener() {
        currentRequest = new ThreadLocal<ServletRequest>();
        brutosInstance = new WebApplicationContext();
    }

    public void contextInitialized(ServletContextEvent sce) {
        currentContext = sce.getServletContext();
        brutosInstance.start( sce );
    }

    public void contextDestroyed(ServletContextEvent sce) {
        currentRequest = null;
        
        if( brutosInstance != null )
            brutosInstance.stop( sce );
    }

    public void sessionCreated(HttpSessionEvent se) {

        Map mappedUploadStats = new HashMap();

        se.getSession()
            .setAttribute(
                BrutosConstants.SESSION_UPLOAD_STATS,
                mappedUploadStats );
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        se.getSession()
            .removeAttribute(
                BrutosConstants.SESSION_UPLOAD_STATS );
    }

    public void requestDestroyed(ServletRequestEvent sre) {
        if( currentRequest != null )
            currentRequest.remove();
    }

    public HttpServletRequest getRequest( ServletRequest request ){
        try{
            HttpServletRequest brutosRequest = (HttpServletRequest) Class.forName( 
                    brutosInstance.getConfiguration().getProperty( 
                    "org.brandao.brutos.request", 
                    "org.brandao.brutos.http.DefaultBrutosRequest"
                ), 
                    true, 
                    Thread.currentThread().getContextClassLoader() 
             
            ).getConstructor( HttpServletRequest.class ).newInstance( request );
            
            return brutosRequest;
        }
        catch( Exception e ){
            throw new BrutosException( "problem getting the request: " + e.getMessage(), e );
        }
    }
    
    public void requestInitialized(ServletRequestEvent sre) {
        currentRequest.set( getRequest( sre.getServletRequest() ) );
    }
    
    public void servletInitialized( Servlet servlet ){
    }
    
    public void servletDestroyed( Servlet servlet ){
    }
    
}
