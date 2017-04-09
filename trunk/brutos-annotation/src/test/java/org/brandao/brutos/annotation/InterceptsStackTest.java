package org.brandao.brutos.annotation;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.interceptsstack.app1.Interceptor1;
import org.brandao.brutos.annotation.helper.interceptsstack.app1.Interceptor2;
import org.brandao.brutos.annotation.helper.interceptsstack.app1.Interceptor3;
import org.brandao.brutos.annotation.helper.interceptsstack.app1.Interceptor4;
import org.brandao.brutos.annotation.helper.interceptsstack.app1.Test1interceptsStack;
import org.brandao.brutos.annotation.helper.interceptsstack.app1.Test2interceptsStack;
import org.brandao.brutos.annotation.helper.interceptsstack.app1.Test3interceptsStack;
import org.brandao.brutos.annotation.helper.interceptsstack.fail.Interceptor1Fail;
import org.brandao.brutos.annotation.helper.interceptsstack.fail.Interceptor2Fail;
import org.brandao.brutos.annotation.helper.interceptsstack.fail.Test1FailinterceptsStack;
import org.brandao.brutos.annotation.helper.interceptsstack.fail.Test2FailinterceptsStack;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class InterceptsStackTest extends TestCase{
    
    public void testTest1interceptsStack() throws Throwable{
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

                	assertEquals(true,request.getAttribute("result"));                	
                	
                	assertEquals("value1_1_ax",request.getAttribute("i1.param1_1_a"));
                	assertEquals("value1_2_ax",request.getAttribute("i1.param1_2_a"));

                	assertEquals("value3_1_ax",request.getAttribute("i3.param3_1_a"));
                	assertEquals("value3_2_ax",request.getAttribute("i3.param3_2_a"));

                	assertNull(request.getAttribute("i4.param4_1_a"));
                	assertNull(request.getAttribute("i4.param4_2_a"));

                	assertNull(request.getAttribute("i2.param2_1_a"));
                	assertNull(request.getAttribute("i2.param2_2_a"));
                	
                	assertEquals(1,request.getAttribute("intercepted.interceptor1"));
                	assertEquals(2,request.getAttribute("intercepted.interceptor3"));
                	assertEquals(3,request.getAttribute("intercepted.interceptor4"));
                	
                	assertNull(request.getAttribute("intercepted.interceptor2"));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{
        		Test1interceptsStack.class,
        		Interceptor1.class,
        		Interceptor2.class,
        		Interceptor3.class,
        		Interceptor4.class
    		});
    }

    public void testTest2interceptsStack() throws Throwable{
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

                	assertEquals("value1_1_bx",request.getAttribute("i1.param1_1_b"));
                	assertEquals("value1_2_bx",request.getAttribute("i1.param1_2_b"));

                	assertEquals("value3_1_bx",request.getAttribute("i3.param3_1_b"));
                	assertEquals("value3_2_bx",request.getAttribute("i3.param3_2_b"));

                	assertEquals("value4_1_b",request.getAttribute("i4.param4_1_b"));
                	assertEquals("value4_2_b",request.getAttribute("i4.param4_2_b"));

                	assertNull(request.getAttribute("i2.param2_1_b"));
                	assertNull(request.getAttribute("i2.param2_2_b"));
                	
                	assertEquals(1,request.getAttribute("intercepted.interceptor1"));
                	assertEquals(2,request.getAttribute("intercepted.interceptor3"));
                	assertEquals(3,request.getAttribute("intercepted.interceptor4"));
                	
                	assertNull(request.getAttribute("intercepted.interceptor2"));
                	assertEquals(true,request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{
        		Test2interceptsStack.class,
        		Interceptor1.class,
        		Interceptor2.class,
        		Interceptor3.class,
        		Interceptor4.class
    		});
    }
    
    public void testTest3interceptsStack() throws Throwable{
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

                	assertEquals("value1_1_c",request.getAttribute("i1.param1_1_c"));
                	assertEquals("value1_2_c",request.getAttribute("i1.param1_2_c"));

                	assertEquals("value2_1_c",request.getAttribute("i2.param2_1_c"));
                	assertEquals("value2_2_c",request.getAttribute("i2.param2_2_c"));
                	
                	assertNull(request.getAttribute("i3.param3_1_c"));
                	assertNull(request.getAttribute("i3.param3_2_c"));

                	assertNull(request.getAttribute("i4.param4_1_c"));
                	assertNull(request.getAttribute("i4.param4_2_c"));
                	
                	assertEquals(1,request.getAttribute("intercepted.interceptor1"));
                	assertEquals(2,request.getAttribute("intercepted.interceptor2"));
                	assertEquals(3,request.getAttribute("intercepted.interceptor4"));
                	
                	assertNull(request.getAttribute("intercepted.interceptor3"));
                	assertEquals(true,request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{
        		Test3interceptsStack.class,
        		Interceptor1.class,
        		Interceptor2.class,
        		Interceptor3.class,
        		Interceptor4.class
    		});
    }
        
    public void testTest1FailinterceptsStack() throws Throwable{
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
                	
                    fail("expected: {interceptor must be informed on parameter: param1_1}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("interceptor must be informed on parameter: param1_1"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    fail("expected: {interceptor must be informed on parameter: param1_1}");
                }
            },
            new Class[]{Test1FailinterceptsStack.class, Interceptor1Fail.class});
    }

    public void testTest2FailinterceptsStack() throws Throwable{
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
                	
                    fail("expected: {interceptor not found: interceptorX}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("interceptor not found: interceptorX"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    fail("expected: {interceptor not found: interceptorX}");
                }
            },
            new Class[]{Test2FailinterceptsStack.class, Interceptor1Fail.class});
    }

    public void testInterceptor2Fail() throws Throwable{
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
                	
                    fail("expected: {does not compose the interceptor stack stackB: Interceptor1Fail}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("does not compose the interceptor stack stackB: Interceptor1Fail"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    fail("does not compose the interceptor stack stackB: Interceptor1Fail}");
                }
            },
            new Class[]{Interceptor2Fail.class});
    }
    
}
