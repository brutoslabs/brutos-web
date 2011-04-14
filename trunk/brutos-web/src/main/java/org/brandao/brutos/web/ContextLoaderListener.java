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
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.web.http.BrutosRequest;
import org.brandao.brutos.web.http.BrutosRequestImp;

/**
 * 
 * @author Afonso Brandao
 */
public class ContextLoaderListener implements ServletContextListener,
        HttpSessionListener, ServletRequestListener{
    
    private ContextLoader contextLoader;
    //public static ThreadLocal<ServletRequest> currentRequest;
    //public static ServletContext currentContext;
    
    public ContextLoaderListener() {
        //currentRequest = new ThreadLocal<ServletRequest>();
        contextLoader = new ContextLoader();
    }

    public void contextInitialized(ServletContextEvent sce) {
        //currentContext = sce.getServletContext();
        contextLoader.init(sce.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent sce) {
        //currentRequest = null;
        contextLoader.destroy(sce.getServletContext());
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
        //if( currentRequest != null )
        //    currentRequest.remove();
    }

    private ServletRequest getRequest( ServletRequest request ){
        try{
            ServletRequest brutosRequest = (ServletRequest) Class.forName( 
                    ContextLoader.getCurrentWebApplicationContext()
                    .getConfiguration().getProperty(
                    "org.brandao.brutos.web.request",
                    BrutosRequestImp.class.getName()
                ), 
                    true, 
                    Thread.currentThread().getContextClassLoader() 
             
            ).getConstructor( ServletRequest.class ).newInstance( request );

            ((BrutosRequest)brutosRequest).parseRequest();
            
            return brutosRequest;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
    
    public void requestInitialized(ServletRequestEvent sre) {
        //currentRequest.set( getRequest( sre.getServletRequest() ) );
    }
    
}
