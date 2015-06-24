package org.brandao.brutos.annotation;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.annotation.helper.resultview.app1.Test1ResultViewController;
import org.brandao.brutos.annotation.helper.resultview.fail.Test1FailResultViewController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.test.MockRenderView;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class ResultViewTest extends TestCase{
    
    public void testTest1ResultViewController_testAction() throws Throwable{
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
                	parameters.put("arg0","v1");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1ResultViewController controller = (Test1ResultViewController)request.getAttribute("Controller");
                	MockRenderView renderView = (MockRenderView)applicationContext.getRenderView();
                	
                	Assert.assertEquals("/WEB-INF/test1resultviewcontroller/testaction/index.jsp", renderView.getView());
                	Assert.assertNull(request.getAttribute("result"));
                	Assert.assertEquals("v1", controller.value);
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1ResultViewController.class});
    }

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
                	parameters.put("arg0","v1");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1ResultViewController controller = (Test1ResultViewController)request.getAttribute("Controller");
                	MockRenderView renderView = (MockRenderView)applicationContext.getRenderView();
                	
                	Assert.assertEquals("/WEB-INF/test1resultviewcontroller/test1action/index.jsp", renderView.getView());
                	Assert.assertEquals(true, request.getAttribute("result"));
                	Assert.assertEquals("v1", controller.value);
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1ResultViewController.class});
    }
    
    public void testTest1ResultViewController_test2Action() throws Throwable{
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
                	parameters.put("arg0","v1");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1ResultViewController controller = (Test1ResultViewController)request.getAttribute("Controller");
                	MockRenderView renderView = (MockRenderView)applicationContext.getRenderView();
                	
                	Assert.assertNull(renderView.getView());
                	Assert.assertEquals(true, request.getAttribute("result"));
                	Assert.assertEquals("v1", controller.value);
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1ResultViewController.class});
    }    
    
    
    public void testTest1FailRestrictionController() throws Throwable{
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
                	
                    Assert.fail("expected: the action not return any value: test1Action}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("the action not return any value: test1Action"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {the action not return any value: test1Action}");
                }
            },
            new Class[]{Test1FailResultViewController.class});
    }
    
}
