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
package org.brandao.brutos.helper.controller;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import junit.framework.TestCase;
import org.brandao.brutos.AbstractApplicationContext;
import org.brandao.brutos.test.MockApplicationContext;
import org.brandao.brutos.test.MockWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.ContextLoaderListener;

/**
 *
 * @author Brandao
 */
public abstract class AbstractTester extends TestCase{


    public abstract AbstractApplicationContext getApplicationContext();
    
    public void execTest( HandlerTest handler ){
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();

        servletContext.setInitParameter("context_class",
                MockWebApplicationContext.class.getName());
        
        MockWebApplicationContext
                .setCurrentApplicationContext(getApplicationContext());
        try{
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("");
                request.setContextPath("");
                listener.requestInitialized(sre);
                handler.run(
                ContextLoader
                    .getCurrentWebApplicationContext());
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public static interface HandlerTest{

        void run( AbstractApplicationContext app );

    }
}
