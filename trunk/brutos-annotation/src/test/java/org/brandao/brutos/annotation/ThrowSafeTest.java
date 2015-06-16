package org.brandao.brutos.annotation;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.annotation.helper.throwsafe.app1.Exception4;
import org.brandao.brutos.annotation.helper.throwsafe.app1.Test1ThrowSafeController;
import org.brandao.brutos.annotation.helper.throwsafe.fail.Test1FailThrowSafeController;
import org.brandao.brutos.annotation.helper.throwsafe.fail.Test2FailThrowSafeController;
import org.brandao.brutos.annotation.helper.throwsafe.fail.Test3FailThrowSafeController;
import org.brandao.brutos.annotation.helper.throwsafe.fail.Test4FailThrowSafeController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.test.MockRenderView;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class ThrowSafeTest extends TestCase{
    
    public void testTest1ResultViewController_test1Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test1", 
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
                	
                	RequestInstrument requestInstrument = 
                			applicationContext.getInvoker().getRequestInstrument();
                	
                	MockRenderView renderView = (MockRenderView) requestInstrument.getRenderView();
                	
                	Assert.assertEquals("/WEB-INF/test1throwsafecontroller/test1action/exception4.jsp", renderView.getView());
                	Assert.assertEquals(DispatcherType.FORWARD, renderView.getDispatcherType());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1ThrowSafeController.class});
    }

    public void testTest1ResultViewController_test2Action_Exception1() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test2", 
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
                	parameters.put("exceptionType", "exception1");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	RequestInstrument requestInstrument = 
                			applicationContext.getInvoker().getRequestInstrument();
                	
                	MockRenderView renderView = (MockRenderView) requestInstrument.getRenderView();
                	
                	Assert.assertEquals("/WEB-INF/test1throwsafecontroller/exception1.jsp",renderView.getView());
                	Assert.assertEquals(DispatcherType.FORWARD,renderView.getDispatcherType());
                	Assert.assertNotNull(request.getAttribute("exception"));
                }

                public void checkException(Throwable e) throws Throwable {
                	throw e;
                }
            },
            new Class[]{Test1ThrowSafeController.class});
    }

    
    public void testTest1ResultViewController_test2Action_Exception2() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test2", 
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
                	parameters.put("exceptionType", "exception2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	RequestInstrument requestInstrument = 
                			applicationContext.getInvoker().getRequestInstrument();
                	
                	MockRenderView renderView = (MockRenderView) requestInstrument.getRenderView();
                	
                	Assert.assertEquals("/WEB-INF/test1throwsafecontroller/exception2.jsp",renderView.getView());
                	Assert.assertEquals(DispatcherType.INCLUDE,renderView.getDispatcherType());
                	Assert.assertNotNull(request.getAttribute("ex"));
                }

                public void checkException(Throwable e) throws Throwable {
                	throw e;
                }
            },
            new Class[]{Test1ThrowSafeController.class});
    }
    
    public void testTest1ResultViewController_test2Action_Exception3() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test2", 
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
                	parameters.put("exceptionType", "exception3");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	RequestInstrument requestInstrument = 
                			applicationContext.getInvoker().getRequestInstrument();
                	
                	MockRenderView renderView = (MockRenderView) requestInstrument.getRenderView();
                	
                	Assert.assertNull(renderView.getView());
                	Assert.assertNotNull(request.getAttribute("exception"));
                }

                public void checkException(Throwable e) throws Throwable {
                	throw e;
                }
            },
            new Class[]{Test1ThrowSafeController.class});
    }    
    public void testTest1ResultViewController_test2Action_Exception4() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test2", 
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
                	parameters.put("exceptionType", "exception4");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Assert.fail("expected {Exception4}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex instanceof Exception4)
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {Exception4}");
                }
            },
            new Class[]{Test1ThrowSafeController.class});
    }
    
    public void testTest1ResultViewController_test3Action_Exception1() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test3", 
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
                	parameters.put("exceptionType", "exception1");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	RequestInstrument requestInstrument = 
                			applicationContext.getInvoker().getRequestInstrument();
                	
                	MockRenderView renderView = (MockRenderView) requestInstrument.getRenderView();
                	
                	Assert.assertNull(renderView.getView());
                }

                public void checkException(Throwable e) throws Throwable {
                	throw e;
                }
            },
            new Class[]{Test1ThrowSafeController.class});
    }

    
    public void testTest1ResultViewController_test3Action_Exception2() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test3", 
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
                	parameters.put("exceptionType", "exception2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	RequestInstrument requestInstrument = 
                			applicationContext.getInvoker().getRequestInstrument();
                	
                	MockRenderView renderView = (MockRenderView) requestInstrument.getRenderView();
                	
                	Assert.assertEquals("/WEB-INF/test1throwsafecontroller/exception2.jsp",renderView.getView());
                	Assert.assertEquals(DispatcherType.INCLUDE,renderView.getDispatcherType());
                	Assert.assertNotNull(request.getAttribute("ex"));
                }

                public void checkException(Throwable e) throws Throwable {
                	throw e;
                }
            },
            new Class[]{Test1ThrowSafeController.class});
    }
    
    public void testTest1ResultViewController_test3Action_Exception3() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test3", 
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
                	parameters.put("exceptionType", "exception3");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	RequestInstrument requestInstrument = 
                			applicationContext.getInvoker().getRequestInstrument();
                	
                	MockRenderView renderView = (MockRenderView) requestInstrument.getRenderView();
                	
                	Assert.assertEquals("/test.jsp",renderView.getView());
                	Assert.assertEquals(DispatcherType.FORWARD,renderView.getDispatcherType());
                	Assert.assertNotNull(request.getAttribute("exception"));
                }

                public void checkException(Throwable e) throws Throwable {
                	throw e;
                }
            },
            new Class[]{Test1ThrowSafeController.class});
    }    
    
    public void testTest1ResultViewController_test3Action_Exception4() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test3", 
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
                	parameters.put("exceptionType", "exception4");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Assert.fail("expected {Exception4}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex instanceof Exception4)
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {Exception4}");
                }
            },
            new Class[]{Test1ThrowSafeController.class});
    }

    public void testTest1FailThrowSafeController() throws Throwable{
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
                	
                	Assert.fail("expected {duplicate exception mapping: Exception1}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("duplicate exception mapping: Exception1"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {duplicate exception mapping: Exception1}");
                }
            },
            new Class[]{Test1FailThrowSafeController.class});
    }

    public void testTest2FailThrowSafeController() throws Throwable{
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
                	
                	Assert.fail("expected {duplicate exception mapping: Exception2}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("duplicate exception mapping: Exception2"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {duplicate exception mapping: Exception2}");
                }
            },
            new Class[]{Test2FailThrowSafeController.class});
    }

    public void testTest3FailThrowSafeController() throws Throwable{
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
                	
                	Assert.fail("expected {exception not informed}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("exception not informed"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {exception not informed}");
                }
            },
            new Class[]{Test3FailThrowSafeController.class});
    }

    public void testTest4FailThrowSafeController() throws Throwable{
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
                	
                	Assert.fail("expected {exception not informed}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("exception not informed"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {exception not informed}");
                }
            },
            new Class[]{Test4FailThrowSafeController.class});
    }
    
}
