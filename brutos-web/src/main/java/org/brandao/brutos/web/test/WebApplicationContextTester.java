/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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


package org.brandao.brutos.web.test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletConfig;
import com.mockrunner.mock.web.MockServletContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSessionEvent;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.ContextLoaderListener;
import org.brandao.brutos.web.DispatcherServlet;

/**
 *
 * @author Brandao
 */
public class WebApplicationContextTester {
    
    public synchronized static void run(
            String uri, 
            WebApplicationTester tester) throws ServletException, IOException{
        
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();


        servletContext.setInitParameter(ContextLoader.CONTEXT_CLASS,
                MockXMLWebApplicationContext.class.getName());

        Map<String,String> contextParams = new HashMap<String,String>();
        
        tester.prepareContext(contextParams);
        
        for(String key: contextParams.keySet())
            servletContext.setInitParameter(key, contextParams.get(key));
        
        try{
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            request.setSession(session);
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);
            HttpSessionEvent hse = new HttpSessionEvent(session);
            DispatcherServlet servlet = new DispatcherServlet();
            try{
                request.setRequestURI(uri);
                request.setContextPath("");
                listener.requestInitialized(sre);
                listener.sessionCreated(hse);
                
                Map<String,String> requestParams = new HashMap<String,String>();
                tester.prepareRequest(requestParams);
                
                for(String key: requestParams.keySet())
                    request.setupAddParameter(key, requestParams.get(key));
                
                MockServletConfig config = new MockServletConfig();
                config.setServletContext(servletContext);
                servlet.init(config);
                servlet.service(request, response);
                tester.checkResult(
                        request, 
                        response, 
                        servletContext, 
                        (ConfigurableWebApplicationContext) ContextLoader.getCurrentWebApplicationContext());
            }
            finally{
                servlet.destroy();
                listener.requestDestroyed(sre);
                listener.sessionDestroyed(hse);
            }
        }
        catch(BrutosException e){
            tester.checkException(e);
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }
    
}
