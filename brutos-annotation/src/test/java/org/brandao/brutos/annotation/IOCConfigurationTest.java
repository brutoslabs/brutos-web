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


package org.brandao.brutos.annotation;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockServletContext;
import java.io.IOException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import junit.framework.TestCase;
import org.brandao.brutos.BrutosContext;
import org.brandao.brutos.ContextLoaderListener;
import org.brandao.brutos.annotation.helper.ConstructorInjectValue;
import org.brandao.brutos.annotation.helper.DefaultInjectable;

/**
 *
 * @author Afonso Brandao
 */
public class IOCConfigurationTest extends TestCase{


    public void testDefaultInjectable() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();

        try{
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                listener.requestInitialized(sre);
                DefaultInjectable instance = (DefaultInjectable) BrutosContext.
                        getCurrentInstance().getIocManager()
                            .getInstance(DefaultInjectable.class.getSimpleName());
                assertNotNull(instance);
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testConstructionInjectValue() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();

        try{
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                listener.requestInitialized(sre);
                ConstructorInjectValue instance = (ConstructorInjectValue) BrutosContext.
                        getCurrentInstance().getIocManager()
                            .getInstance(ConstructorInjectValue.class.getSimpleName());
                assertNotNull(instance);
                assertEquals(300, instance.getNumber());
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

}
