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

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.annotation.helper.action.app1.App1TestController;
import org.brandao.brutos.annotation.helper.action.fail.Fail10TestController;
import org.brandao.brutos.annotation.helper.action.fail.Fail11TestController;
import org.brandao.brutos.annotation.helper.action.fail.Fail2TestController;
import org.brandao.brutos.annotation.helper.action.fail.Fail3TestController;
import org.brandao.brutos.annotation.helper.action.fail.Fail4TestController;
import org.brandao.brutos.annotation.helper.action.fail.Fail7TestController;
import org.brandao.brutos.annotation.helper.action.fail.Fail8TestController;
import org.brandao.brutos.annotation.helper.action.fail.Fail9TestController;
import org.brandao.brutos.annotation.helper.action.fail.FailTestController;
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
public class ActionTest extends TestCase {
    
    public void test1() throws Throwable{
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
                }

                public void prepareSession(Map<String, String> parameters) {
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
                    Assert.fail();
                }
            },
            new Class[]{App1TestController.class});
    }
    
    public void test2() throws Throwable{
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
                    
                }

                public void prepareSession(Map<String, String> parameters) {
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
                    Assert.fail();
                }
            },
            new Class[]{App1TestController.class});
    }

    public void test3() throws Throwable{
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
                    
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) {
                    Assert.fail();
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertEquals("result3", request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/WEB-INF/app1test/test3/index.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }
            },
            new Class[]{App1TestController.class});
    }

    public void test4() throws Throwable{
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
                    
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) {
                    Assert.fail();
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertEquals("result4", request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/WEB-INF/app1test/test4/index.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }
            },
            new Class[]{App1TestController.class});
    }

    public void test5() throws Throwable{
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
                    
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) {
                    Assert.fail();
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertEquals("result4", request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/WEB-INF/app1test/test4/index.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }
            },
            new Class[]{App1TestController.class});
    }

    public void test6() throws Throwable{
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
                    
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) throws Throwable{
                    throw e;
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertNull(request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/WEB-INF/app1test/test04.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }
            },
            new Class[]{App1TestController.class});
    }

    public void test7() throws Throwable{
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
                    
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) throws Throwable{
                    throw e;
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertNull(request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/test05.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }
            },
            new Class[]{App1TestController.class});
    }

    public void test8() throws Throwable{
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
                    
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) throws Throwable{
                    throw e;
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertNull(request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/test06.jsp", renderView.getView());
                    Assert.assertEquals(org.brandao.brutos.DispatcherType.REDIRECT, renderView.getDispatcherType());
                }
            },
            new Class[]{App1TestController.class});
    }

    public void test9() throws Throwable{
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
                    
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) throws Throwable{
                    throw e;
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertNull(request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals("/WEB-INF/app1test/test03.jsp", renderView.getView());
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }
            },
            new Class[]{App1TestController.class});
    }

    public void test() throws Throwable{
        WebApplicationContextTester.run(
            "/App1Test/action", 
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
                    
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkException(Throwable e) throws Throwable{
                    throw e;
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                	org.brandao.brutos.mapping.Controller controller = 
                			applicationContext.getControllerManager().getController(App1TestController.class);
                	
                	org.brandao.brutos.mapping.Action a;
                	try{
                		a = controller.getMethod(App1TestController.class.getDeclaredMethod("action"));
                	}
                	catch(Throwable e){
                		throw new RuntimeException(e);
                	}
                	
                	Assert.assertNull(a);
                	
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertNull(request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertNull(renderView.getView());
                }
            },
            new Class[]{App1TestController.class});
    }
    
    public void test10() throws Throwable{
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
                    
                }
                
                public void checkException(Throwable e) {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("view must be rendered in abstract actions: /test03"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {view must be rendered in abstract actions: /test03}");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.fail();
                }
            },
            new Class[]{FailTestController.class});
    }

    public void test11() throws Throwable{
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
                    
                }
                
                public void checkException(Throwable e) {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("view must be informed in abstract actions: /test03"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {view must be informed in abstract actions: /test03}");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.fail();
                }
            },
            new Class[]{Fail2TestController.class});
    }

    public void test12() throws Throwable{
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
                    
                }
                
                public void checkException(Throwable e) {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("view must be informed in abstract actions: /test03"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {view must be informed in abstract actions: /test03}");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.fail();
                }
            },
            new Class[]{Fail3TestController.class});
    }

    public void test13() throws Throwable{
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
                    
                }
                
                public void checkException(Throwable e) {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("action id cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {action id cannot be empty}");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.fail();
                }
            },
            new Class[]{Fail4TestController.class});
    }

    public void test14() throws Throwable{
        WebApplicationContextTester.run(
            "/App1Test/test0006", 
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
                    
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    MockRenderView renderView = (MockRenderView) applicationContext.getRenderView();

                    Assert.assertEquals("result006", request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                    Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE, renderView.getDispatcherType());
                }

                public void checkException(Throwable e) throws Throwable{
                    throw e;
                }
            },
            new Class[]{App1TestController.class});
    }

    public void test15() throws Throwable{
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
                    
                }
                
                public void checkException(Throwable e) {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("expected starts with \"/\": teste"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {expected starts with \"/\": teste}");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.fail();
                }
            },
            new Class[]{Fail7TestController.class});
    }    
    
    public void test16() throws Throwable{
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
                    
                }
                
                public void checkException(Throwable e) {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("duplicate action: /teste"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {duplicate action: /teste}");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.fail();
                }
            },
            new Class[]{Fail8TestController.class});
    }    

    public void test17() throws Throwable{
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
                    
                }
                
                public void checkException(Throwable e) {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("duplicate action: /teste"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {duplicate action: /teste}");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.fail();
                }
            },
            new Class[]{Fail9TestController.class});
    }    

    public void test18() throws Throwable{
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
                    
                }
                
                public void checkException(Throwable e) {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("expected starts with \"/\": teste00"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {duplicate action: /teste}");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.fail();
                }
            },
            new Class[]{Fail10TestController.class});
    }    

    public void test19() throws Throwable{
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
                    
                }
                
                public void checkException(Throwable e) {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("Illegal character in path at index 1: / klkjjh"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {Illegal character in path at index 1: / klkjjh}");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.fail("expected: {Illegal character in path at index 1: / klkjjh}");
                }
            },
            new Class[]{Fail11TestController.class});
    }    
    
}
