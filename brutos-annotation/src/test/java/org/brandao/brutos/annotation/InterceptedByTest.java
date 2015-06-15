package org.brandao.brutos.annotation;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.interceptedby.app1.Test2InterceptedByController;
import org.brandao.brutos.annotation.helper.interceptedby.app1.Test3InterceptedByController;
import org.brandao.brutos.annotation.helper.interceptedby.app1.Test4InterceptedByController;
import org.brandao.brutos.annotation.helper.interceptedby.app1.Test5InterceptedByController;
import org.brandao.brutos.annotation.helper.interceptedby.app1.TestInterceptedByController;
import org.brandao.brutos.annotation.helper.interceptedby.app1.TestName2InterceptorController;
import org.brandao.brutos.annotation.helper.interceptedby.app1.TestNameInterceptorController;
import org.brandao.brutos.annotation.helper.interceptedby.fail.NotInterceptor;
import org.brandao.brutos.annotation.helper.interceptedby.fail.Test1InterceptedByFailController;
import org.brandao.brutos.annotation.helper.interceptedby.fail.Test2InterceptedByFailController;
import org.brandao.brutos.annotation.helper.interceptedby.fail.Test3InterceptedByFailController;
import org.brandao.brutos.annotation.helper.interceptedby.fail.Test4InterceptedByFailController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class InterceptedByTest extends TestCase{
    
    public void testTestInterceptedByController() throws Throwable{
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
                	
                	Assert.assertEquals("true",request.getAttribute("intercepted.testName"));
                	Assert.assertNull(request.getAttribute("intercepted.testName2"));
                	Assert.assertEquals("true",request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{
        		TestInterceptedByController.class, 
        		TestNameInterceptorController.class, 
        		TestName2InterceptorController.class});
    }

    public void testTest2InterceptedByController() throws Throwable{
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

                	Assert.assertEquals("value1",request.getAttribute("param1"));
                	
                	Assert.assertEquals("true",request.getAttribute("intercepted.testName"));
                	Assert.assertNull(request.getAttribute("intercepted.testName2"));
                	
                	Assert.assertEquals("true",request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{
        		Test2InterceptedByController.class, 
        		TestNameInterceptorController.class, 
        		TestName2InterceptorController.class});
    }

    public void testTest3InterceptedByController() throws Throwable{
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

                	Assert.assertEquals("value1",request.getAttribute("param1"));
                	
                	Assert.assertEquals("true",request.getAttribute("intercepted.testName"));
                	Assert.assertNull(request.getAttribute("intercepted.testName2"));
                	
                	Assert.assertEquals("true",request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{
        		Test3InterceptedByController.class, 
        		TestNameInterceptorController.class, 
        		TestName2InterceptorController.class});
    }

    public void testTest4InterceptedByController() throws Throwable{
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

                	Assert.assertEquals("value1",request.getAttribute("param1"));
                	Assert.assertEquals("value2",request.getAttribute("param2"));
                	
                	Assert.assertEquals("true",request.getAttribute("intercepted.testName"));
                	Assert.assertNull(request.getAttribute("intercepted.testName2"));
                	
                	Assert.assertEquals("true",request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{
        		Test4InterceptedByController.class, 
        		TestNameInterceptorController.class, 
        		TestName2InterceptorController.class});
    }

    public void testTest5InterceptedByController() throws Throwable{
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

                	Assert.assertEquals("value1",request.getAttribute("param1"));
                	Assert.assertEquals("value2",request.getAttribute("param2"));
                	Assert.assertEquals("value3",request.getAttribute("param3"));
                	Assert.assertEquals("value4",request.getAttribute("param4"));
                	
                	Assert.assertEquals("true",request.getAttribute("intercepted.testName"));
                	Assert.assertEquals("true",request.getAttribute("intercepted.testName2"));
                	
                	Assert.assertEquals("true",request.getAttribute("result"));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{
        		Test5InterceptedByController.class, 
        		TestNameInterceptorController.class, 
        		TestName2InterceptorController.class});
    }

    public void testTest1InterceptedByFailController() throws Throwable{
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
                	
                    Assert.fail("expected: {interceptor already intercept this controller: testName}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("interceptor already intercept this controller: testName"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {interceptor already intercept this controller: testName}");
                }
            },
            new Class[]{Test1InterceptedByFailController.class, TestNameInterceptorController.class});
    }

    public void testTest2InterceptedByFailController() throws Throwable{
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
                	
                    Assert.fail("expected: {interceptor name must be informed}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("interceptor name must be informed"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {interceptor name must be informed}");
                }
            },
            new Class[]{Test2InterceptedByFailController.class});
    }

    public void testTest3InterceptedByFailController() throws Throwable{
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
                	
                    Assert.fail("expected: {interceptor not found: interceptorNotExist}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("interceptor not found: interceptorNotExist"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {interceptor not found: interceptorNotExist}");
                }
            },
            new Class[]{Test3InterceptedByFailController.class});
    }

    public void testTest4InterceptedByFailController() throws Throwable{
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
                	
                    Assert.fail("expected: {interceptor not found: org.brandao.brutos.annotation.helper.interceptedby.fail.NotInterceptor}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("interceptor not found: org.brandao.brutos.annotation.helper.interceptedby.fail.NotInterceptor"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {interceptor not found: org.brandao.brutos.annotation.helper.interceptedby.fail.NotInterceptor}");
                }
            },
            new Class[]{Test4InterceptedByFailController.class, NotInterceptor.class});
    }
    
}
