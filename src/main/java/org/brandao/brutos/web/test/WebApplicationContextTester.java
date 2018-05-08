/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSessionEvent;

import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.ContextLoaderListener;
import org.brandao.brutos.web.DispatcherServlet;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletConfig;
import com.mockrunner.mock.web.MockServletContext;

/**
 * 
 * @author Brandao
 */
public class WebApplicationContextTester {
    
    public static void run(
            String uri, 
            WebApplicationTester tester, String complement){
        run(uri, tester, null, complement);
    }
    
    public static void run(
            String uri, 
            WebApplicationTester tester, Class<?> ... clazz){
        run(uri, tester, clazz, null);
    }

    public static void run(
            String uri, 
            WebApplicationTester tester, String[] resources){
    	
    	StringBuilder contextConfig = new StringBuilder();
    	
    	for(String r: resources){
    		if(contextConfig.length() > 0){
    			contextConfig.append(", ");
    		}
    		contextConfig.append(r);
    	}
    	
        Map<String,String> contextParams   = new HashMap<String, String>();
        Map<String,Object> sessionParams   = new HashMap<String, Object>();
        Map<String,String> requestParams   = new HashMap<String, String>();
        Map<String,String> requestHeader   = new HashMap<String, String>();
        Map<String,Object> requestProperty = new HashMap<String, Object>();
        
        contextParams.put(MockXMLWebApplicationContext.contextConfigName, contextConfig.toString());
        
        run(
            uri, 
            tester,
            contextParams,
            sessionParams,
            requestParams,
            requestHeader,
            requestProperty);    	
	}

    public static void run(
            String uri, 
            WebApplicationTester tester, Class<?>[] clazz, String complement){
        run(uri, tester, clazz, complement, null, null);
    }

    public static void run(
            String uri, 
            WebApplicationTester tester,
            Class<?> basePackage){
        
            run(
                uri, 
                tester,
                null, null, null, basePackage);
    }
    
    public static void run(
            String uri, 
            WebApplicationTester tester,
            Class<?>[] clazz, String complement, Class<?>[] types, Class<?> basePackage){
        
        String xml = "";
        xml +="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xml +="<ns2:controllers";
        xml +="    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'";
        xml +="    xmlns:ns2='http://www.brutosframework.com.br/schema/controllers'";
        xml +="    xmlns:ns1='http://www.brutosframework.com.br/schema/context'";
        xml +="    xsi:schemaLocation='";
        xml +="    http://www.brutosframework.com.br/schema/controllers http://www.brutosframework.com.br/schema/controllers/brutos-controllers-1.1.xsd";
        xml +="    http://www.brutosframework.com.br/schema/context http://www.brutosframework.com.br/schema/context/brutos-context-1.1.xsd'>";
        
        if(basePackage != null){
            xml += "<ns1:component-scan use-default-filters=\"false\" base-package=\"" + basePackage.getPackage().getName() + "\"/>";
        }
        else
        if(clazz != null){
            xml +="<ns1:component-scan use-default-filters=\"false\">";

            for(Class<?> c: clazz){
                xml +="        <ns1:include-filter type=\"regex\" expression=\""+c.getName().replace(".","\\.").replace("$","\\$")+"\"/>";
            }

            xml +="</ns1:component-scan>";
        }
        
        if(types != null){
            xml += "<ns1:types>";
            for(Class<?> type: types){
                xml += "<ns1:type factory=\"" + type.getName() + "\"/>";
            }
            xml += "</ns1:types>";
        }

        if(complement != null)
            xml += complement;
        
        xml +="</ns2:controllers>";
            
        Map<String,String> contextParams   = new HashMap<String, String>();
        Map<String,Object> sessionParams   = new HashMap<String, Object>();
        Map<String,String> requestParams   = new HashMap<String, String>();
        Map<String,String> requestHeader   = new HashMap<String, String>();
        Map<String,Object> requestProperty = new HashMap<String, Object>();
        
        contextParams.put(MockXMLWebApplicationContext.XML_CONTENT, xml);
        
        run(
            uri, 
            tester,
            contextParams,
            sessionParams,
            requestParams,
            requestHeader,
            requestProperty);
    }

    public static void run(
            String uri, 
            WebApplicationTester tester){
        run(
            uri, 
            tester,
            new HashMap<String,String>(),
            new HashMap<String,Object>(),
            new HashMap<String,String>(),
            new HashMap<String,String>(),
            new HashMap<String,Object>());
    }
    
    private synchronized static void run(
            String uri, 
            WebApplicationTester tester,
            Map<String,String> contextParams,
            Map<String,Object> sessionParams,
            Map<String,String> requestParams,
            Map<String,String> requestHeader,
            Map<String,Object> requestProperty){
        
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();


        //servletContext.setInitParameter(ContextLoader.CONTEXT_CLASS,
        //        MockXMLWebApplicationContext.class.getName());

        tester.prepareContext(contextParams);
        
        for(String key: contextParams.keySet())
            servletContext.setInitParameter(key, contextParams.get(key));
        
        tester.prepareContext(servletContext);
        
        try{
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            request.setSession(session);
            tester.prepareRequest(request);
            tester.prepareSession(session);
            
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);
            HttpSessionEvent hse = new HttpSessionEvent(session);
            DispatcherServlet servlet = new DispatcherServlet();
            try{
                request.setRequestURI(uri);
                request.setContextPath("");
                //request.setContentType("application/json; charset=UTF-8");
                listener.requestInitialized(sre);
                
                tester.prepareSession(sessionParams);

                for(String key: sessionParams.keySet())
                	session.setAttribute(key, sessionParams.get(key));
                
                listener.sessionCreated(hse);
                
                tester.prepareRequest(requestParams, requestHeader, requestProperty);
                
                for(String key: requestParams.keySet())
                    request.setupAddParameter(key, requestParams.get(key));

                for(String key: requestHeader.keySet())
                    request.setHeader(key, requestHeader.get(key));
                
                for(String key: requestProperty.keySet())
                    request.setAttribute(key, requestProperty.get(key));
                
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
        catch(Throwable e){
            tester.checkException(e);
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }
    
}
