package org.brandao.brutos.annotation;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.intercepts.app1.Interceptor1ControllerInterceptor;
import org.brandao.brutos.annotation.helper.intercepts.app1.Interceptor2ControllerInterceptor;
import org.brandao.brutos.annotation.helper.intercepts.app1.Interceptor3;
import org.brandao.brutos.annotation.helper.intercepts.app1.Interceptor4;
import org.brandao.brutos.annotation.helper.intercepts.app1.Interceptor5ControllerInterceptor;
import org.brandao.brutos.annotation.helper.intercepts.app1.Interceptor6;
import org.brandao.brutos.annotation.helper.intercepts.app1.Interceptor7;
import org.brandao.brutos.annotation.helper.intercepts.app1.Interceptor8InterceptorController;
import org.brandao.brutos.annotation.helper.intercepts.app1.NotInterceptor;
import org.brandao.brutos.annotation.helper.intercepts.app1.Test1Intercepts;
import org.brandao.brutos.annotation.helper.intercepts.app1.Test2Intercepts;
import org.brandao.brutos.annotation.helper.intercepts.app1.Test3Intercepts;
import org.brandao.brutos.annotation.helper.intercepts.app1.Test4Intercepts;
import org.brandao.brutos.annotation.helper.intercepts.app1.Test4_1Intercepts;
import org.brandao.brutos.annotation.helper.intercepts.app1.Test5Intercepts;
import org.brandao.brutos.annotation.helper.intercepts.app1.Test6Intercepts;
import org.brandao.brutos.annotation.helper.intercepts.app1.Test7Intercepts;
import org.brandao.brutos.annotation.helper.intercepts.app1.TestNotInterceptorIntercepts;
import org.brandao.brutos.annotation.helper.intercepts.fail.Interceptor1FailControllerInterceptor;
import org.brandao.brutos.annotation.helper.intercepts.fail.Interceptor2FailInterceptorController;
import org.brandao.brutos.annotation.helper.intercepts.fail.Interceptor3Fail;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class InterceptsTest extends TestCase{
    
    public void testTest1Intercepts() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
            new WebApplicationTester() {

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

                	assertEquals("value1_1",request.getAttribute("param1_1"));
                	assertEquals("value1_2",request.getAttribute("param1_2"));
                	
                	assertEquals("true",request.getAttribute("intercepted.interceptor1"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor6"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor8"));
                	
                	assertNull(request.getAttribute("intercepted.interceptor2"));
                	assertNull(request.getAttribute("intercepted.interceptor3"));
                	assertNull(request.getAttribute("intercepted.interceptor4"));
                	assertNull(request.getAttribute("intercepted.interceptor5"));
                	assertNull(request.getAttribute("intercepted.interceptor7"));
                	assertEquals(true,request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{
        		Test1Intercepts.class,
        		Interceptor1ControllerInterceptor.class,
        		Interceptor2ControllerInterceptor.class,
        		Interceptor3.class,
        		Interceptor4.class,
        		Interceptor5ControllerInterceptor.class,
        		Interceptor6.class,
        		Interceptor7.class,
        		Interceptor8InterceptorController.class
    		});
    }

    public void testTest2Intercepts() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
            new WebApplicationTester() {

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

                	assertEquals("value2_1",request.getAttribute("param2_1"));
                	assertEquals("value2_2",request.getAttribute("param2_2"));
                	
                	assertEquals("true",request.getAttribute("intercepted.interceptor2"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor6"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor8"));
                	
                	assertNull(request.getAttribute("intercepted.interceptor1"));
                	assertNull(request.getAttribute("intercepted.interceptor3"));
                	assertNull(request.getAttribute("intercepted.interceptor4"));
                	assertNull(request.getAttribute("intercepted.interceptor5"));
                	assertNull(request.getAttribute("intercepted.interceptor7"));
                	assertEquals(true,request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{
        		Test2Intercepts.class,
        		Interceptor1ControllerInterceptor.class,
        		Interceptor2ControllerInterceptor.class,
        		Interceptor3.class,
        		Interceptor4.class,
        		Interceptor5ControllerInterceptor.class,
        		Interceptor6.class,
        		Interceptor7.class,
        		Interceptor8InterceptorController.class
    		});
    }

    public void testTest3Intercepts() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
            new WebApplicationTester() {

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

                	assertEquals("value3_1",request.getAttribute("param3_1"));
                	assertEquals("value3_2",request.getAttribute("param3_2"));
                	
                	assertEquals("true",request.getAttribute("intercepted.interceptor3"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor6"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor8"));
                	
                	assertNull(request.getAttribute("intercepted.interceptor1"));
                	assertNull(request.getAttribute("intercepted.interceptor2"));
                	assertNull(request.getAttribute("intercepted.interceptor4"));
                	assertNull(request.getAttribute("intercepted.interceptor5"));
                	assertNull(request.getAttribute("intercepted.interceptor7"));
                	assertEquals(true,request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{
        		Test3Intercepts.class,
        		Interceptor1ControllerInterceptor.class,
        		Interceptor2ControllerInterceptor.class,
        		Interceptor3.class,
        		Interceptor4.class,
        		Interceptor5ControllerInterceptor.class,
        		Interceptor6.class,
        		Interceptor7.class,
        		Interceptor8InterceptorController.class
    		});
    }
    
    public void testTest4Intercepts() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
            new WebApplicationTester() {

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

                	assertEquals("true",request.getAttribute("intercepted.interceptor4"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor6"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor8"));
                	
                	assertNull(request.getAttribute("intercepted.interceptor1"));
                	assertNull(request.getAttribute("intercepted.interceptor2"));
                	assertNull(request.getAttribute("intercepted.interceptor3"));
                	assertNull(request.getAttribute("intercepted.interceptor5"));
                	assertNull(request.getAttribute("intercepted.interceptor7"));
                	assertEquals(true,request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{
        		Test4Intercepts.class,
        		Interceptor1ControllerInterceptor.class,
        		Interceptor2ControllerInterceptor.class,
        		Interceptor3.class,
        		Interceptor4.class,
        		Interceptor5ControllerInterceptor.class,
        		Interceptor6.class,
        		Interceptor7.class,
        		Interceptor8InterceptorController.class
    		});
    }

    public void testTest4_1Intercepts() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
            new WebApplicationTester() {

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

                	assertEquals("true",request.getAttribute("intercepted.interceptor4"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor6"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor8"));
                	
                	assertNull(request.getAttribute("intercepted.interceptor1"));
                	assertNull(request.getAttribute("intercepted.interceptor2"));
                	assertNull(request.getAttribute("intercepted.interceptor3"));
                	assertNull(request.getAttribute("intercepted.interceptor5"));
                	assertNull(request.getAttribute("intercepted.interceptor7"));
                	assertEquals(true,request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{
        		Test4_1Intercepts.class,
        		Interceptor1ControllerInterceptor.class,
        		Interceptor2ControllerInterceptor.class,
        		Interceptor3.class,
        		Interceptor4.class,
        		Interceptor5ControllerInterceptor.class,
        		Interceptor6.class,
        		Interceptor7.class,
        		Interceptor8InterceptorController.class
    		});
    }
    
    public void testTest5Intercepts() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
            new WebApplicationTester() {

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

                	assertEquals("true",request.getAttribute("intercepted.interceptor5"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor6"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor8"));
                	
                	assertNull(request.getAttribute("intercepted.interceptor1"));
                	assertNull(request.getAttribute("intercepted.interceptor2"));
                	assertNull(request.getAttribute("intercepted.interceptor3"));
                	assertNull(request.getAttribute("intercepted.interceptor4"));
                	assertNull(request.getAttribute("intercepted.interceptor7"));
                	assertEquals(true,request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{
        		Test5Intercepts.class,
        		Interceptor1ControllerInterceptor.class,
        		Interceptor2ControllerInterceptor.class,
        		Interceptor3.class,
        		Interceptor4.class,
        		Interceptor5ControllerInterceptor.class,
        		Interceptor6.class,
        		Interceptor7.class,
        		Interceptor8InterceptorController.class
    		});
    }
    
    public void testTest6Intercepts() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
            new WebApplicationTester() {

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

                	assertEquals("true",request.getAttribute("intercepted.interceptor6"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor8"));
                	
                	assertNull(request.getAttribute("intercepted.interceptor1"));
                	assertNull(request.getAttribute("intercepted.interceptor2"));
                	assertNull(request.getAttribute("intercepted.interceptor3"));
                	assertNull(request.getAttribute("intercepted.interceptor4"));
                	assertNull(request.getAttribute("intercepted.interceptor5"));
                	assertNull(request.getAttribute("intercepted.interceptor7"));
                	assertEquals(true,request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{
        		Test6Intercepts.class,
        		Interceptor1ControllerInterceptor.class,
        		Interceptor2ControllerInterceptor.class,
        		Interceptor3.class,
        		Interceptor4.class,
        		Interceptor5ControllerInterceptor.class,
        		Interceptor6.class,
        		Interceptor7.class,
        		Interceptor8InterceptorController.class
    		});
    }    

    public void testTest7Intercepts() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
            new WebApplicationTester() {

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

                	assertEquals("value7_1x",request.getAttribute("param7_1"));
                	assertEquals("value7_2x",request.getAttribute("param7_2"));
                	
                	assertEquals("true",request.getAttribute("intercepted.interceptor7"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor6"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor8"));

                	assertNull(request.getAttribute("intercepted.interceptor1"));
                	assertNull(request.getAttribute("intercepted.interceptor2"));
                	assertNull(request.getAttribute("intercepted.interceptor3"));
                	assertNull(request.getAttribute("intercepted.interceptor4"));
                	assertNull(request.getAttribute("intercepted.interceptor5"));
                	assertEquals(true,request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{
        		Test7Intercepts.class,
        		Interceptor1ControllerInterceptor.class,
        		Interceptor2ControllerInterceptor.class,
        		Interceptor3.class,
        		Interceptor4.class,
        		Interceptor5ControllerInterceptor.class,
        		Interceptor6.class,
        		Interceptor7.class,
        		Interceptor8InterceptorController.class
    		});
    }    
    
    public void testTestNotInterceptorIntercepts() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
            new WebApplicationTester() {

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

                	assertEquals("true",request.getAttribute("intercepted.interceptor6"));
                	assertEquals("true",request.getAttribute("intercepted.interceptor8"));
                	
                	assertNull(request.getAttribute("intercepted.interceptor1"));
                	assertNull(request.getAttribute("intercepted.interceptor2"));
                	assertNull(request.getAttribute("intercepted.interceptor3"));
                	assertNull(request.getAttribute("intercepted.interceptor4"));
                	assertNull(request.getAttribute("intercepted.interceptor5"));
                	assertNull(request.getAttribute("intercepted.interceptor7"));
                	assertEquals(true,request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{
        		TestNotInterceptorIntercepts.class,
        		Interceptor1ControllerInterceptor.class,
        		Interceptor2ControllerInterceptor.class,
        		Interceptor3.class,
        		Interceptor4.class,
        		Interceptor5ControllerInterceptor.class,
        		Interceptor6.class,
        		Interceptor7.class,
        		NotInterceptor.class,
        		Interceptor8InterceptorController.class
    		});
    }
    
    public void testInterceptor1FailControllerInterceptor() throws Throwable{
        WebApplicationContextTester.run(
            "", 
            new WebApplicationTester() {

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
                	
                    fail("expected: {must implement interface InterceptorController: Interceptor1FailControllerInterceptor}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("must implement interface InterceptorController: Interceptor1FailControllerInterceptor"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    fail("expected: {must implement interface InterceptorController: Interceptor1FailControllerInterceptor}");
                }
            },
            new Class[]{Interceptor1FailControllerInterceptor.class});
    }

    public void testInterceptor2FailControllerInterceptor() throws Throwable{
        WebApplicationContextTester.run(
            "", 
            new WebApplicationTester() {

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
                	
                    fail("expected: {must implement interface InterceptorController: Interceptor2FailInterceptorController}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("must implement interface InterceptorController: Interceptor2FailInterceptorController"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    fail("expected: {must implement interface InterceptorController: Interceptor2FailInterceptorController}");
                }
            },
            new Class[]{Interceptor2FailInterceptorController.class});
    }

    public void testInterceptor3Fail() throws Throwable{
        WebApplicationContextTester.run(
            "", 
            new WebApplicationTester() {

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
                	
                    fail("expected: {must implement interface InterceptorController: Interceptor3Fail}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("must implement interface InterceptorController: Interceptor3Fail"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    fail("expected: {must implement interface InterceptorController: Interceptor3Fail}");
                }
            },
            new Class[]{Interceptor3Fail.class});
    }
    
}
