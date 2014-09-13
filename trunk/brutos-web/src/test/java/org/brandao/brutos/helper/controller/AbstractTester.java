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
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import junit.framework.TestCase;
import org.brandao.brutos.ActionType;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.test.MockObjectFactory;
import org.brandao.brutos.test.MockRenderView;
import org.brandao.brutos.validator.DefaultValidatorFactory;
import org.brandao.brutos.web.test.MockWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.ContextLoaderListener;
import org.brandao.brutos.web.RequestInfo;
import org.brandao.brutos.web.XMLWebApplicationContext;
import org.brandao.brutos.web.http.StaticBrutosRequest;
import org.springframework.core.io.ResourceLoader;

/**
 *
 * @author Brandao
 */
public abstract class AbstractTester extends TestCase{

    protected ActionType actionType;
    
    public AbstractTester(){
    }
    
    public abstract ConfigurableWebApplicationContext getApplicationContext(String resourceName);
    
    public void execTest( HandlerTest handler ){
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();

        servletContext.setInitParameter("context_class",
                MockWebApplicationContext.class.getName());

        servletContext.setInitParameter(BrutosConstants.OBJECT_FACTORY_CLASS,
                MockObjectFactory.class.getName());

        servletContext.setInitParameter(BrutosConstants.VALIDATOR_FACTORY_CLASS, 
                DefaultValidatorFactory.class.getName());
        
        if(actionType != null)
            servletContext.setInitParameter(BrutosConstants.ACTION_TYPE, actionType.name());
        
        servletContext.setInitParameter(BrutosConstants.RENDER_VIEW_CLASS,
                MockRenderView.class.getName());

        servletContext.setInitParameter(ConfigurableWebApplicationContext.contextConfigName,
                ResourceLoader.CLASSPATH_URL_PREFIX + handler.getResourceName());

        servletContext.setInitParameter(ContextLoader.CONTEXT_CLASS,
                XMLWebApplicationContext.class.getName());
        
        //ConfigurableWebApplicationContext context = getApplicationContext(handler.getResourceName());
        //MockWebApplicationContext
        //        .setCurrentApplicationContext(context);
        
        RequestInfo requestInfo = null;
        try{
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            request.setSession(session);
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);
            HttpSessionEvent hse = new HttpSessionEvent(session);
            try{
                request.setRequestURI("");
                request.setContextPath("");
                listener.requestInitialized(sre);
                listener.sessionCreated(hse);

                requestInfo   = new RequestInfo();
                requestInfo.setRequest(new StaticBrutosRequest(request));
                requestInfo.setResponse(response);
                RequestInfo.setCurrent(requestInfo);
                
                ConfigurableApplicationContext app =
                        (ConfigurableApplicationContext)ContextLoader
                    .getCurrentWebApplicationContext();
                
                
                
                ThreadLocal currentApp = null;
                try{
                    Invoker invoker = app.getInvoker();
                    Field currentAppField = 
                            Invoker.class.getDeclaredField("currentApp");
                    currentAppField.setAccessible(true);
                    currentApp = (ThreadLocal) currentAppField.get(invoker);
                    currentApp.set(app);
                    handler.run(
                    (ConfigurableApplicationContext)ContextLoader
                        .getCurrentWebApplicationContext(), request, response);
                }
                catch(Exception e){
                    throw new BrutosException(e);
                }
                finally{
                    if(currentApp != null)
                        currentApp.remove();
                }
            }
            finally{
                if(requestInfo != null)
                    requestInfo.removeCurrent();

                listener.requestDestroyed(sre);
                listener.sessionDestroyed(hse);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public static interface HandlerTest{

        String getResourceName();

        void run( ConfigurableApplicationContext app, HttpServletRequest request,
                HttpServletResponse response );

    }
}
