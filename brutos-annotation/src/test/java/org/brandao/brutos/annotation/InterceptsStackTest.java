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

                	Assert.assertEquals(true,request.getAttribute("result"));                	
                	
                	Assert.assertEquals("value1_1_ax",request.getAttribute("param1_1_a"));
                	Assert.assertEquals("value1_2_ax",request.getAttribute("param1_2_a"));

                	Assert.assertEquals("value3_1_ax",request.getAttribute("param3_1_a"));
                	Assert.assertEquals("value3_2_ax",request.getAttribute("param3_2_a"));

                	Assert.assertNull(request.getAttribute("param4_1_a"));
                	Assert.assertNull(request.getAttribute("param4_2_a"));

                	Assert.assertNull(request.getAttribute("param2_1_a"));
                	Assert.assertNull(request.getAttribute("param2_2_a"));
                	
                	Assert.assertEquals(1,request.getAttribute("intercepted.interceptor1"));
                	Assert.assertEquals(2,request.getAttribute("intercepted.interceptor3"));
                	Assert.assertEquals(3,request.getAttribute("intercepted.interceptor4"));
                	
                	Assert.assertNull(request.getAttribute("intercepted.interceptor2"));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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

                	Assert.assertEquals("value1_1_b",request.getAttribute("param1_1_bx"));
                	Assert.assertEquals("value1_2_b",request.getAttribute("param1_2_bx"));

                	//Assert.assertEquals("value2_1_b",request.getAttribute("param2_1_b"));
                	//Assert.assertEquals("value2_2_b",request.getAttribute("param2_2_b"));
                	
                	Assert.assertEquals("value3_1_b",request.getAttribute("param3_1_bx"));
                	Assert.assertEquals("value3_2_b",request.getAttribute("param3_2_bx"));

                	Assert.assertEquals("value4_1_b",request.getAttribute("param4_1_b"));
                	Assert.assertEquals("value4_2_b",request.getAttribute("param4_2_b"));

                	//Assert.assertNull(request.getAttribute("param1_1_b"));
                	//Assert.assertNull(request.getAttribute("param1_2_b"));

                	Assert.assertNull(request.getAttribute("param2_1_b"));
                	Assert.assertNull(request.getAttribute("param2_2_b"));
                	
                	//Assert.assertNull(request.getAttribute("param3_1_b"));
                	//Assert.assertNull(request.getAttribute("param3_2_b"));

                	//Assert.assertNull(request.getAttribute("param4_1_b"));
                	//Assert.assertNull(request.getAttribute("param4_2_b"));
                	
                	Assert.assertEquals("1",request.getAttribute("intercepted.interceptor1"));
                	Assert.assertEquals("2",request.getAttribute("intercepted.interceptor3"));
                	Assert.assertEquals("3",request.getAttribute("intercepted.interceptor4"));
                	
                	Assert.assertNull(request.getAttribute("intercepted.interceptor2"));
                	Assert.assertEquals("true",request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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

                	Assert.assertEquals("value1_1_c",request.getAttribute("param1_1_c"));
                	Assert.assertEquals("value1_2_c",request.getAttribute("param1_2_c"));

                	Assert.assertEquals("value2_1_c",request.getAttribute("param2_1_c"));
                	Assert.assertEquals("value2_2_c",request.getAttribute("param2_2_c"));
                	
                	//Assert.assertEquals("value3_1_c",request.getAttribute("param3_1_c"));
                	//Assert.assertEquals("value3_2_c",request.getAttribute("param3_2_c"));

                	//Assert.assertEquals("value4_1_c",request.getAttribute("param4_1_c"));
                	//Assert.assertEquals("value4_2_c",request.getAttribute("param4_2_c"));

                	//Assert.assertNull(request.getAttribute("param1_1_c"));
                	//Assert.assertNull(request.getAttribute("param1_2_c"));

                	//Assert.assertNull(request.getAttribute("param2_1_c"));
                	//Assert.assertNull(request.getAttribute("param2_2_c"));
                	
                	Assert.assertNull(request.getAttribute("param3_1_c"));
                	Assert.assertNull(request.getAttribute("param3_2_c"));

                	Assert.assertNull(request.getAttribute("param4_1_c"));
                	Assert.assertNull(request.getAttribute("param4_2_c"));
                	
                	Assert.assertEquals("1",request.getAttribute("intercepted.interceptor1"));
                	Assert.assertEquals("2",request.getAttribute("intercepted.interceptor2"));
                	Assert.assertEquals("3",request.getAttribute("intercepted.interceptor4"));
                	
                	Assert.assertNull(request.getAttribute("intercepted.interceptor3"));
                	Assert.assertEquals("true",request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	
                    Assert.fail("expected: {interceptor must be informed}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("interceptor must be informed"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {interceptor must be informed}");
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
                	
                    Assert.fail("expected: {interceptor not found: interceptorX}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("interceptor not found: interceptorX"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {interceptor not found: interceptorX}");
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
                	
                    Assert.fail("expected: {does not compose the interceptor stack stackB: Interceptor1Fail}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("does not compose the interceptor stack stackB: Interceptor1Fail"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("does not compose the interceptor stack stackB: Interceptor1Fail}");
                }
            },
            new Class[]{Interceptor2Fail.class});
    }
    
}
