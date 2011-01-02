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

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import org.brandao.brutos.ioc.ContextFactory;
import org.brandao.brutos.ioc.IOCProviderFactory;
import org.brandao.brutos.ioc.RequestFactory;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.old.programatic.WebFrameManager;

/**
 *
 * @author Afonso Brandao
 */
public class DefaultApplicationContext extends ApplicationContext{

    public void destroy() {
    }

    protected void loadIOCManager(IOCManager iocManager){
        iocManager.addBean( "servletContextFactory",
                ContextFactory.class, ScopeType.APPLICATION, false, null );
                
        
        iocManager.addBean( "servletContext",
                ServletContext.class, ScopeType.APPLICATION, false,
                "servletContextFactory" ).setFactoryMethod( "createInstance" );

        iocManager.addBean( "iocManagerFactory", 
                IOCProviderFactory.class, ScopeType.APPLICATION, false, null );

        iocManager.addBean( "iocManager",
                IOCManager.class, ScopeType.APPLICATION, false,
                "iocManagerFactory" ).setFactoryMethod( "createInstance" );

        iocManager.addBean( "requestFactory", 
                RequestFactory.class, ScopeType.REQUEST, false, null );
        
        iocManager.addBean( "request",
                ServletRequest.class, ScopeType.REQUEST, false,
                "requestFactory" ).setFactoryMethod( "createInstance" );
    }

    protected void loadWebFrameManager(WebFrameManager webFrameManager){
    }

    protected void loadInterceptorManager(InterceptorManager interceptorManager){
    }

    protected void loadController(ControllerManager controllerManager) {
    }

}
