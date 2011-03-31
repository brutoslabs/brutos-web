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

package org.brandao.brutos;

import javax.servlet.Servlet;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import org.brandao.brutos.old.programatic.IOCManager;

/**
 * @deprecated 
 * @author Afonso Brandao
 */
public class ContextLoaderListener extends org.brandao.brutos.web.ContextLoaderListener/*implements ServletContextListener, HttpSessionListener, ServletRequestListener*/{
    
    private BrutosContext brutosInstance;
    //public static ThreadLocal<ServletRequest> currentRequest;
    //public static ServletContext currentContext;
    
    public ContextLoaderListener() {
        currentRequest = new ThreadLocal<ServletRequest>();
        brutosInstance = new BrutosContext();
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
        /*if( brutosInstance.getIocManager().getProvider() != null )
            brutosInstance.getIocManager().getProvider()
                .sessionCreated( se );
         */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /*if( brutosInstance.getIocManager().getProvider() != null )
            brutosInstance.getIocManager().getProvider()
                .sessionDestroyed( se );
         */
    }

    public void requestDestroyed(ServletRequestEvent sre) {

        if( currentRequest != null )
            currentRequest.remove();

        /*
        if( brutosInstance.getIocManager() != null ){
            IOCManager iocManager = brutosInstance.getIocManager();
            if( iocManager.getProvider() != null )
                brutosInstance.getIocManager().getProvider()
                    .requestDestroyed( sre );
            
        }
         */
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
        /*
        if( brutosInstance.getIocManager() != null ){
            IOCManager iocManager = brutosInstance.getIocManager();
            if( iocManager.getProvider() != null )
                brutosInstance.getIocManager().getProvider()
                    .requestInitialized( sre );
        }
        */
        /*
        Form form = brutosInstance
                .getResolveController()
                    .getController(
                        (WebFrameManager)ContextLoaderListener.currentContext
                            .getAttribute( BrutosConstants.WEBFRAME_MANAGER ),
                        (HttpServletRequest)ContextLoaderListener.currentRequest.get()
                );

        if( form != null )
            sre.getServletRequest().setAttribute( BrutosConstants.CONTROLLER , form);
        */
        
    }
    public void servletInitialized( Servlet servlet ){
    }
    
    public void servletDestroyed( Servlet servlet ){
    }
    
}
