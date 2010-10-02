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

package org.brandao.brutos.ioc.picocontainer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * @author Afonso Brandao
 */
public class PicoContainerContextLoaderListener implements ServletContextListener, HttpSessionListener, ServletRequestListener{
    
    public static ThreadLocal<ServletRequest> currentRequest;
    public static ServletContext currentContext;

    public PicoContainerContextLoaderListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        currentContext = sce.getServletContext();
        currentRequest = new ThreadLocal<ServletRequest>();
        
        Scope applicationScope = new ApplicationScope( currentContext );
        Scope requestScope     = new RequestScope();
        Scope sessionScope     = new SessionScope();
        Scope singletonScope   = new SingletonScope();
        Scope prototypeScope   = new ProtoTypeScope();
        
        PicoContainerScopes.register( "application", applicationScope);
        PicoContainerScopes.register( "request", requestScope);
        PicoContainerScopes.register( "session", sessionScope);
        PicoContainerScopes.register( "singleton", singletonScope);
        PicoContainerScopes.register( "prototype", prototypeScope);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        currentRequest = null;
        PicoContainerScopes.remove( "application" );
        PicoContainerScopes.remove( "request" );
        PicoContainerScopes.remove( "session" );
        PicoContainerScopes.remove( "singleton" );
        PicoContainerScopes.remove( "prototype");
    }

    public void sessionCreated(HttpSessionEvent se) {
    }

    public void sessionDestroyed(HttpSessionEvent se) {
    }
    
    public void requestDestroyed(ServletRequestEvent sre) {
        currentRequest.remove();
    }

    public void requestInitialized(ServletRequestEvent sre) {
        currentRequest.set( sre.getServletRequest() );
    }
}
