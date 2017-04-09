package org.brandao.brutos.annotation;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.annotation.helper.view.app1.Test1ViewController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.test.MockRenderView;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class ViewTest extends TestCase{
    
    public void testTest1ResultViewController_action1() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/action1", 
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
                	
                	MockRenderView renderView = (MockRenderView)applicationContext.getRenderView();
                	
                	assertEquals("/view01.jsp", renderView.getView());
                	assertEquals(org.brandao.brutos.DispatcherType.FORWARD, renderView.getDispatcherType());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test1ViewController.class});
    }

    public void testTest1ResultViewController_action2() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/action2", 
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
                	
                	MockRenderView renderView = (MockRenderView)applicationContext.getRenderView();
                	
                	assertEquals("/WEB-INF/views/view02.jsp", renderView.getView());
                	assertEquals(org.brandao.brutos.DispatcherType.FORWARD, renderView.getDispatcherType());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test1ViewController.class});
    }

    public void testTest1ResultViewController_action3() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/action3", 
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
                	
                	MockRenderView renderView = (MockRenderView)applicationContext.getRenderView();
                	
                	assertEquals("/WEB-INF/views/view03.jsp", renderView.getView());
                	assertEquals(org.brandao.brutos.DispatcherType.FORWARD, renderView.getDispatcherType());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test1ViewController.class});
    }

    public void testTest1ResultViewController_action4() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/action4", 
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
                	
                	MockRenderView renderView = (MockRenderView)applicationContext.getRenderView();
                	
                	assertEquals("/WEB-INF/views/view04.jsp", renderView.getView());
                	assertEquals(org.brandao.brutos.DispatcherType.FORWARD, renderView.getDispatcherType());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test1ViewController.class});
    }

    public void testTest1ResultViewController_action5() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/action5", 
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
                	
                	MockRenderView renderView = (MockRenderView)applicationContext.getRenderView();
                	
                	assertEquals("/view05.jsp", renderView.getView());
                	assertEquals(org.brandao.brutos.DispatcherType.FORWARD, renderView.getDispatcherType());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test1ViewController.class});
    }

    public void testTest1ResultViewController_action6() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/action6", 
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
                	
                	MockRenderView renderView = (MockRenderView)applicationContext.getRenderView();
                	
                	assertEquals("/WEB-INF/views/view06.jsp", renderView.getView());
                	assertEquals(org.brandao.brutos.DispatcherType.INCLUDE, renderView.getDispatcherType());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test1ViewController.class});
    }
    
    public void testTest1ResultViewController_action7() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/action7", 
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
                	
                	MockRenderView renderView = (MockRenderView)applicationContext.getRenderView();
                	
                	assertEquals("/WEB-INF/views/controller.jsp", renderView.getView());
                	assertEquals(org.brandao.brutos.DispatcherType.INCLUDE, renderView.getDispatcherType());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test1ViewController.class});
    }    
    
}
