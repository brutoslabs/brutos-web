/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import org.brandao.brutos.test.MockInvoker;
import org.brandao.brutos.test.MockObjectFactory;
import org.brandao.brutos.test.MockRenderView;
import org.brandao.brutos.validator.DefaultValidatorFactory;
import org.brandao.brutos.web.test.MockWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.ContextLoaderListener;
import org.brandao.brutos.web.RequestInfo;
import org.brandao.brutos.web.http.StaticBrutosRequest;
import org.brandao.brutos.web.test.MockWebInvoker;
import org.springframework.core.io.ResourceLoader;

/**
 *
 * @author Brandao
 */
public abstract class AbstractTester extends TestCase{

    protected ActionType actionType;
    
    private Class invokerClass;
    
    private Boolean resolveView;
    
    public AbstractTester(){
    }
    
    public abstract Class getApplicationContext(String resourceName);
    
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

        servletContext.setInitParameter(BrutosConstants.INVOKER_CLASS, 
                this.invokerClass == null? MockWebInvoker.class.getName() : this.invokerClass.getName());
        
        if(actionType != null)
            servletContext.setInitParameter(BrutosConstants.ACTION_TYPE, actionType.name());
        
        servletContext.setInitParameter(BrutosConstants.RENDER_VIEW_CLASS,
                MockRenderView.class.getName());

        servletContext.setInitParameter(ConfigurableWebApplicationContext.contextConfigName,
                ResourceLoader.CLASSPATH_URL_PREFIX + handler.getResourceName());

        servletContext.setInitParameter(ContextLoader.CONTEXT_CLASS,
                getApplicationContext(handler.getResourceName()).getName());
        
        servletContext.setInitParameter(BrutosConstants.VIEW_RESOLVER_AUTO, 
                this.resolveView == null? "false" : String.valueOf(this.resolveView));
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

    public Class getInvokerClass() {
        return invokerClass;
    }

    public void setInvokerClass(Class invokerClass) {
        this.invokerClass = invokerClass;
    }

    public Boolean isResolveView() {
        return resolveView;
    }

    public void setResolveView(Boolean resolveView) {
        this.resolveView = resolveView;
    }

    public static interface HandlerTest{

        String getResourceName();

        void run( ConfigurableApplicationContext app, HttpServletRequest request,
                HttpServletResponse response );

    }
}
