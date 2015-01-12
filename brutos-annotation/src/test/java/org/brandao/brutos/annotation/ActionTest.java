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


package org.brandao.brutos.annotation;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Assert;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.test.MockRenderView;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

/**
 *
 * @author Brandao
 */
public class ActionTest extends AbstractWebAnnotationApplicationContextTest{
    
    public void test1() throws ServletException, IOException{
        final String xmlContent = 
                "<ns2:controllers\n" +
                "    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
                "    xmlns:ns2='http://www.brutosframework.com.br/schema/controllers'\n" +
                "    xmlns:ns1='http://www.brutosframework.com.br/schema/context'\n" +
                "    xsi:schemaLocation='\n" +
                "    http://www.brutosframework.com.br/schema/controllers http://www.brutosframework.com.br/schema/controllers/brutos-controllers-1.1.xsd\n" +
                "    http://www.brutosframework.com.br/schema/context http://www.brutosframework.com.br/schema/context/brutos-context-1.1.xsd'>\n" +
                "<ns1:component-scan use-default-filters=\"false\" base-package=\"org.brandao.brutos.annotation.helper.action.app1\"/>\n" +
                "</ns2:controllers>";
        
        WebApplicationContextTester.run(
            "/App1Test/test1", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.XML_CONTENT,
                            xmlContent
                    );
                    
                }

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertEquals("result", request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/WEB-INF/app1test/test1action/index.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }

                public void checkException(Throwable e) {
                }
            });
    }
    
    public void test2() throws ServletException, IOException{
        final String xmlContent = 
                "<ns2:controllers\n" +
                "    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
                "    xmlns:ns2='http://www.brutosframework.com.br/schema/controllers'\n" +
                "    xmlns:ns1='http://www.brutosframework.com.br/schema/context'\n" +
                "    xsi:schemaLocation='\n" +
                "    http://www.brutosframework.com.br/schema/controllers http://www.brutosframework.com.br/schema/controllers/brutos-controllers-1.1.xsd\n" +
                "    http://www.brutosframework.com.br/schema/context http://www.brutosframework.com.br/schema/context/brutos-context-1.1.xsd'>\n" +
                "<ns1:component-scan use-default-filters=\"false\" base-package=\"org.brandao.brutos.annotation.helper.action.app1\"/>\n" +
                "</ns2:controllers>";
        
        WebApplicationContextTester.run(
            "/App1Test/test2", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.XML_CONTENT,
                            xmlContent
                    );
                    
                }

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertEquals("result2", request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/WEB-INF/app1test/test2/index.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }
                
                public void checkException(Throwable e) {
                }
            });
    }

    public void test3() throws ServletException, IOException{
        final String xmlContent = 
                "<ns2:controllers\n" +
                "    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
                "    xmlns:ns2='http://www.brutosframework.com.br/schema/controllers'\n" +
                "    xmlns:ns1='http://www.brutosframework.com.br/schema/context'\n" +
                "    xsi:schemaLocation='\n" +
                "    http://www.brutosframework.com.br/schema/controllers http://www.brutosframework.com.br/schema/controllers/brutos-controllers-1.1.xsd\n" +
                "    http://www.brutosframework.com.br/schema/context http://www.brutosframework.com.br/schema/context/brutos-context-1.1.xsd'>\n" +
                "<ns1:component-scan use-default-filters=\"false\" base-package=\"org.brandao.brutos.annotation.helper.action.app1\"/>\n" +
                "</ns2:controllers>";
        
        WebApplicationContextTester.run(
            "/App1Test/test00", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.XML_CONTENT,
                            xmlContent
                    );
                    
                }

                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertEquals("result3", request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/WEB-INF/app1test/test3/index.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }
            });
    }

    public void test4() throws ServletException, IOException{
        final String xmlContent = 
                "<ns2:controllers\n" +
                "    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
                "    xmlns:ns2='http://www.brutosframework.com.br/schema/controllers'\n" +
                "    xmlns:ns1='http://www.brutosframework.com.br/schema/context'\n" +
                "    xsi:schemaLocation='\n" +
                "    http://www.brutosframework.com.br/schema/controllers http://www.brutosframework.com.br/schema/controllers/brutos-controllers-1.1.xsd\n" +
                "    http://www.brutosframework.com.br/schema/context http://www.brutosframework.com.br/schema/context/brutos-context-1.1.xsd'>\n" +
                "<ns1:component-scan use-default-filters=\"false\" base-package=\"org.brandao.brutos.annotation.helper.action.app1\"/>\n" +
                "</ns2:controllers>";
        
        WebApplicationContextTester.run(
            "/App1Test/test01", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.XML_CONTENT,
                            xmlContent
                    );
                    
                }

                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertEquals("result4", request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/WEB-INF/app1test/test4/index.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }
            });
    }

    public void test5() throws ServletException, IOException{
        final String xmlContent = 
                "<ns2:controllers\n" +
                "    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
                "    xmlns:ns2='http://www.brutosframework.com.br/schema/controllers'\n" +
                "    xmlns:ns1='http://www.brutosframework.com.br/schema/context'\n" +
                "    xsi:schemaLocation='\n" +
                "    http://www.brutosframework.com.br/schema/controllers http://www.brutosframework.com.br/schema/controllers/brutos-controllers-1.1.xsd\n" +
                "    http://www.brutosframework.com.br/schema/context http://www.brutosframework.com.br/schema/context/brutos-context-1.1.xsd'>\n" +
                "<ns1:component-scan use-default-filters=\"false\" base-package=\"org.brandao.brutos.annotation.helper.action.app1\"/>\n" +
                "</ns2:controllers>";
        
        WebApplicationContextTester.run(
            "/App1Test/test02", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.XML_CONTENT,
                            xmlContent
                    );
                    
                }

                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertEquals("result4", request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/WEB-INF/app1test/test4/index.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }
            });
    }

    public void test6() throws ServletException, IOException{
        final String xmlContent = 
                "<ns2:controllers\n" +
                "    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
                "    xmlns:ns2='http://www.brutosframework.com.br/schema/controllers'\n" +
                "    xmlns:ns1='http://www.brutosframework.com.br/schema/context'\n" +
                "    xsi:schemaLocation='\n" +
                "    http://www.brutosframework.com.br/schema/controllers http://www.brutosframework.com.br/schema/controllers/brutos-controllers-1.1.xsd\n" +
                "    http://www.brutosframework.com.br/schema/context http://www.brutosframework.com.br/schema/context/brutos-context-1.1.xsd'>\n" +
                "<ns1:component-scan use-default-filters=\"false\" base-package=\"org.brandao.brutos.annotation.helper.action.app1\"/>\n" +
                "</ns2:controllers>";
        
        WebApplicationContextTester.run(
            "/App1Test/test04", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.XML_CONTENT,
                            xmlContent
                    );
                    
                }

                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertNull(request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/WEB-INF/app1test/test04.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }
            });
    }

    public void test7() throws ServletException, IOException{
        final String xmlContent = 
                "<ns2:controllers\n" +
                "    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
                "    xmlns:ns2='http://www.brutosframework.com.br/schema/controllers'\n" +
                "    xmlns:ns1='http://www.brutosframework.com.br/schema/context'\n" +
                "    xsi:schemaLocation='\n" +
                "    http://www.brutosframework.com.br/schema/controllers http://www.brutosframework.com.br/schema/controllers/brutos-controllers-1.1.xsd\n" +
                "    http://www.brutosframework.com.br/schema/context http://www.brutosframework.com.br/schema/context/brutos-context-1.1.xsd'>\n" +
                "<ns1:component-scan use-default-filters=\"false\" base-package=\"org.brandao.brutos.annotation.helper.action.app1\"/>\n" +
                "</ns2:controllers>";
        
        WebApplicationContextTester.run(
            "/App1Test/test05", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.XML_CONTENT,
                            xmlContent
                    );
                    
                }

                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertNull(request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/test05.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }
            });
    }

    public void test8() throws ServletException, IOException{
        final String xmlContent = 
                "<ns2:controllers\n" +
                "    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
                "    xmlns:ns2='http://www.brutosframework.com.br/schema/controllers'\n" +
                "    xmlns:ns1='http://www.brutosframework.com.br/schema/context'\n" +
                "    xsi:schemaLocation='\n" +
                "    http://www.brutosframework.com.br/schema/controllers http://www.brutosframework.com.br/schema/controllers/brutos-controllers-1.1.xsd\n" +
                "    http://www.brutosframework.com.br/schema/context http://www.brutosframework.com.br/schema/context/brutos-context-1.1.xsd'>\n" +
                "<ns1:component-scan use-default-filters=\"false\" base-package=\"org.brandao.brutos.annotation.helper.action.app1\"/>\n" +
                "</ns2:controllers>";
        
        WebApplicationContextTester.run(
            "/App1Test/test06", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.XML_CONTENT,
                            xmlContent
                    );
                    
                }

                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertNull(request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/test06.jsp", renderView.getView());
                    Assert.assertEquals(org.brandao.brutos.DispatcherType.REDIRECT, renderView.getDispatcherType());
                }
            });
    }

    public void test9() throws ServletException, IOException{
        final String xmlContent = 
                "<ns2:controllers\n" +
                "    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
                "    xmlns:ns2='http://www.brutosframework.com.br/schema/controllers'\n" +
                "    xmlns:ns1='http://www.brutosframework.com.br/schema/context'\n" +
                "    xsi:schemaLocation='\n" +
                "    http://www.brutosframework.com.br/schema/controllers http://www.brutosframework.com.br/schema/controllers/brutos-controllers-1.1.xsd\n" +
                "    http://www.brutosframework.com.br/schema/context http://www.brutosframework.com.br/schema/context/brutos-context-1.1.xsd'>\n" +
                "<ns1:component-scan use-default-filters=\"false\" base-package=\"org.brandao.brutos.annotation.helper.action.app1\"/>\n" +
                "</ns2:controllers>";
        
        WebApplicationContextTester.run(
            "/App1Test/test03", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.XML_CONTENT,
                            xmlContent
                    );
                    
                }

                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertNull(request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/WEB-INF/app1test/test03.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }
            });
    }

    public void test10() throws ServletException, IOException{
        final String xmlContent = 
                "<ns2:controllers\n" +
                "    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
                "    xmlns:ns2='http://www.brutosframework.com.br/schema/controllers'\n" +
                "    xmlns:ns1='http://www.brutosframework.com.br/schema/context'\n" +
                "    xsi:schemaLocation='\n" +
                "    http://www.brutosframework.com.br/schema/controllers http://www.brutosframework.com.br/schema/controllers/brutos-controllers-1.1.xsd\n" +
                "    http://www.brutosframework.com.br/schema/context http://www.brutosframework.com.br/schema/context/brutos-context-1.1.xsd'>\n" +
                "<ns1:component-scan use-default-filters=\"false\" base-package=\"org.brandao.brutos.annotation.helper.action.app2\"/>\n" +
                "</ns2:controllers>";
        
        WebApplicationContextTester.run(
            "", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                    
                    parameters.put(
                            MockAnnotationWebApplicationContext.XML_CONTENT,
                            xmlContent
                    );
                    
                }
                
                public void checkException(Throwable e) {
                    Assert.assertNotNull(e);
                }

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.fail();
                }
            });
    }
    
}
