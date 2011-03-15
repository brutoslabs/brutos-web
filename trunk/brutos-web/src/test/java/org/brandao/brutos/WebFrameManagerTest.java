/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */

package org.brandao.brutos;

import org.brandao.brutos.old.programatic.Bean;
import org.brandao.brutos.old.programatic.WebFrameBuilder;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.old.programatic.WebFrameManager;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSession;
import junit.framework.TestCase;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosContext;
import org.brandao.brutos.ContextLoaderListener;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.scope.Scopes;
import org.brandao.brutos.test.MockApplicationContext;
import org.brandao.brutos.test.MockViewProvider;
import org.brandao.brutos.scope.Scope;
/**
 *
 * @author Afonso Brandao
 */
public class WebFrameManagerTest extends TestCase{

    public static class TestController{

        public TestController(){
            BrutosContext context = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            context.getContext().setAttribute("status", "OK");
        }

        public Object testView(){
            return "Text message";
        }

        public void testException() throws Exception{
            throw new Exception();
        }

    }

    public static class TestInheritanceController extends TestController2{
    }

    public static class TestController2{

        private int propertyRequest;
        private double propertySession;
        private Properties propertyIOC;
        private String propertyFlash;
        private long propertyApplication;
        
        public TestController2(){
        }

        public void testMethod(){
            BrutosContext context = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            context.getContext().setAttribute("status", "OK");
        }

        public int getPropertyRequest() {
            return propertyRequest;
        }

        public void setPropertyRequest(int propertyRequest) {
            this.propertyRequest = propertyRequest;
        }

        public double getPropertySession() {
            return propertySession;
        }

        public void setPropertySession(double propertySession) {
            this.propertySession = propertySession;
        }

        public Properties getPropertyIOC() {
            return propertyIOC;
        }

        public void setPropertyIOC(Properties propertyIOC) {
            this.propertyIOC = propertyIOC;
        }

        public String getPropertyFlash() {
            return propertyFlash;
        }

        public void setPropertyFlash(String propertyFlash) {
            this.propertyFlash = propertyFlash;
        }

        public long getPropertyApplication() {
            return propertyApplication;
        }

        public void setPropertyApplication(long propertyApplication) {
            this.propertyApplication = propertyApplication;
        }

        public void request(int propertyRequest) {
            this.propertyRequest = propertyRequest;
        }

        public void session(double propertySession) {
            this.propertySession = propertySession;
        }

        public void ioc(Properties propertyIOC) {
            this.propertyIOC = propertyIOC;
        }

        public void flash(String propertyFlash) {
            this.propertyFlash = propertyFlash;
        }

        public void application(long propertyApplication) {
            this.propertyApplication = propertyApplication;
        }

    }

    public static class TestMappingBeanController{

        private Object bean;

        public TestMappingBeanController(){
        }

        public Object getBean() {
            return bean;
        }

        public void setBean(Object bean) {
            this.bean = bean;
        }

    }

    public static class Bean2Test{

        private int id;
        private String nome;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
        
    }

    public static class BeanTest{
        
        private int id;
        private String name;
        private String message;
        private Bean2Test testhierarchy;
        private List<Bean2Test> testhierarchyList;
        private Map<Integer,Bean2Test> testhierarchyMap;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Bean2Test getTesthierarchy() {
            return testhierarchy;
        }

        public void setTesthierarchy(Bean2Test value) {
            this.testhierarchy = value;
        }

        public List<Bean2Test> getTestHierarchyList() {
            return testhierarchyList;
        }

        public void setTestHierarchyList(List<Bean2Test> testinheritanceList) {
            this.testhierarchyList = testinheritanceList;
        }

        public Map<Integer, Bean2Test> getTestHierarchyMap() {
            return testhierarchyMap;
        }

        public void setTestHierarchyMap(Map<Integer, Bean2Test> testinheritanceMap) {
            this.testhierarchyMap = testinheritanceMap;
        }
    }

    public static class InheritanceBeanTest extends BeanTest{
    }

    public void testDefaultConfig() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    webFrameManager.addWebFrame( "/test.jbrs", TestController.class );
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);
            
            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                assertEquals( "OK" , servletContext.getAttribute( "status" ));
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testView() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    webFrameManager.addWebFrame("/test.jbrs", "/test.jsp", TestController.class);
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                assertEquals( "OK" , servletContext.getAttribute( "status" ));
                assertEquals( "/test.jsp" , ((MockViewProvider)bc.getViewProvider()).getView() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testRedirectView() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    webFrameManager
                            .addWebFrame(
                                "/test.jbrs",
                                "/test.jsp",
                                true,
                                "TestController",
                                TestController.class,
                                ScopeType.REQUEST,
                                "invoke");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                assertEquals( "OK" , servletContext.getAttribute( "status" ));
                assertEquals( "/test.jsp" , ((MockViewProvider)bc.getViewProvider()).getView() );
                assertTrue( ((MockViewProvider)bc.getViewProvider()).isRedirect() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMethodView() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfm = webFrameManager
                        .addWebFrame("/test.jbrs", null, TestController.class);

                    wfm.addMethod("method", "testView", "/method.jsp" );
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setupAddParameter("invoke", "method");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                assertEquals( "/method.jsp" , ((MockViewProvider)bc.getViewProvider()).getView() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testThrowView() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfm = webFrameManager
                        .addWebFrame("/test.jbrs", null, TestController.class);

                    wfm.addMethod("method", "testException", "/method.jsp" )
                        .addThrowable(Exception.class, "/throw.jsp", "methodException", false );
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setupAddParameter("invoke", "method");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                assertEquals( "/throw.jsp" , ((MockViewProvider)bc.getViewProvider()).getView() );
                Scope requestScope = Scopes.get(ScopeType.REQUEST);
                assertNotNull( requestScope.get( "methodException" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testHierarchyView() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfm = webFrameManager
                        .addWebFrame("/test.jbrs", "/test.jsp", TestController.class);

                    wfm.addMethod("method", "testView", "/method.jsp" );
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setupAddParameter("invoke", "method");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                assertEquals( "/method.jsp" , ((MockViewProvider)bc.getViewProvider()).getView() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testInstanceController() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    webFrameManager.addWebFrame(
                            "/test.jbrs",
                            "/test.jsp",
                            "instanceController",
                            TestController.class,
                            ScopeType.valueOf("prototype"),
                            "invoke");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);

                Bean b = bc.getIocManager().getBean("instanceController");
                
                assertEquals( "instanceController" , b.getInjectable().getName() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testRequestScope() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    webFrameManager.addWebFrame(
                            "/test.jbrs",
                            "/test.jsp",
                            "instanceController",
                            TestController.class,
                            ScopeType.REQUEST,
                            "invoke");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                assertNotNull( request.getAttribute( "instanceController" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testApplicationScope() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    webFrameManager.addWebFrame(
                            "/test.jbrs",
                            "/test.jsp",
                            "instanceController",
                            TestController.class,
                            ScopeType.APPLICATION,
                            "invoke");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                assertNotNull( servletContext.getAttribute( "instanceController" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testSessionScope() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    webFrameManager.addWebFrame(
                            "/test.jbrs",
                            "/test.jsp",
                            "instanceController",
                            TestController.class,
                            ScopeType.SESSION,
                            "invoke");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            HttpSession session = new MockHttpSession();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setSession( session );
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                assertNotNull( session.getAttribute( "instanceController" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testFlashScope() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    webFrameManager.addWebFrame(
                            "/test.jbrs",
                            "/test.jsp",
                            "instanceController",
                            TestController.class,
                            ScopeType.FLASH,
                            "invoke");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            HttpSession session = new MockHttpSession();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setSession( session );
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                fail("expected exception");
            }
            catch( Exception e ){
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testInvoker() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController2.class)
                        .addMethod("testAction", "testMethod");

                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setupAddParameter("invoke", "testAction");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                assertNotNull( "OK", servletContext.getAttribute( "status" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMethodParameter() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    webFrameManager.addWebFrame(
                        "/test.jbrs",
                        "/test.jsp",
                        "instanceController",
                        TestController2.class,
                        ScopeType.REQUEST,
                        "_method")
                        .addMethod("testAction", "testMethod");

                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setupAddParameter("_method", "testAction");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                assertNotNull( "OK", servletContext.getAttribute( "status" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testDefaultMethodName() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController2.class);
                    
                    wfb.addMethod("testAction", "testMethod");
                    wfb.setDefaultMethodName( "testAction" );
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                assertNotNull( "OK", servletContext.getAttribute( "status" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testPropertyInheritance() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestInheritanceController.class,
                        ScopeType.REQUEST);

                    wfb.addProperty("propertyRequest", "testValue");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setupAddParameter("testValue", "20");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                TestController2 controller =
                    (TestController2)request.getAttribute( "TestInheritanceController" );

                assertNotNull( controller );
                assertEquals( 20 , controller.getPropertyRequest() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }
    public void testPropertyRequest() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController2.class,
                        ScopeType.REQUEST);

                    wfb.addProperty("propertyRequest", "testValue");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setupAddParameter("testValue", "20");
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                TestController2 controller =
                    (TestController2)request.getAttribute( "TestController2" );

                assertNotNull( controller );
                assertEquals( 20 , controller.getPropertyRequest() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testPropertySession() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController2.class,
                        ScopeType.REQUEST);

                    wfb.addProperty("propertySession", "testValue", ScopeType.SESSION);
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setSession(session);
                session.setAttribute("testValue", "20.0");
                
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                TestController2 controller =
                    (TestController2)request.getAttribute( "TestController2" );

                assertNotNull( controller );
                assertEquals( 20.0 , controller.getPropertySession() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testPropertyIOC() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                    iocManager
                        .addProperties("testValue", Properties.class )
                            .addValue("test", "testValue");
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController2.class,
                        ScopeType.REQUEST);

                    wfb.addProperty("propertyIOC", "testValue", ScopeType.IOC);
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");

                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                TestController2 controller =
                    (TestController2)request.getAttribute( "TestController2" );

                assertNotNull( controller );
                assertNotNull( controller.getPropertyIOC() );
                assertEquals( "testValue", controller.getPropertyIOC().getProperty("test") );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testPropertyFlash() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController2.class,
                        ScopeType.REQUEST);

                    wfb.addProperty("propertyFlash", "testValue", ScopeType.FLASH);
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setSession(session);

                listener.requestInitialized(sre);

                Scopes.get("flash").put("testValue", "Teste");

                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                TestController2 controller =
                    (TestController2)request.getAttribute( "TestController2" );

                assertEquals( "Teste", controller.getPropertyFlash() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testPropertyApplication() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController2.class,
                        ScopeType.REQUEST);

                    wfb.addProperty("propertyApplication", "testValue", ScopeType.APPLICATION);
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                servletContext.setAttribute( "testValue", 10L );
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                TestController2 controller =
                    (TestController2)request.getAttribute( "TestController2" );

                assertNotNull( controller );
                assertEquals( 10L , controller.getPropertyApplication() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testParameterMethodRequest() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController2.class,
                        ScopeType.REQUEST);

                    wfb.addMethod( "method", "request", int.class )
                        .addParameter("testValue", ScopeType.REQUEST);
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setupAddParameter("testValue", "20");
                request.setupAddParameter( "invoke" , "method" );
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                TestController2 controller =
                    (TestController2)request.getAttribute( "TestController2" );

                assertNotNull( controller );
                assertEquals( 20 , controller.getPropertyRequest() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testParameterMethodSession() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController2.class,
                        ScopeType.REQUEST);

                    wfb.addMethod( "method", "session", double.class )
                        .addParameter("testValue", ScopeType.SESSION);
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setSession(session);
                request.setupAddParameter( "invoke", "method" );
                session.setAttribute("testValue", "20.0");

                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                TestController2 controller =
                    (TestController2)request.getAttribute( "TestController2" );

                assertNotNull( controller );
                assertEquals( 20.0 , controller.getPropertySession() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testParameterMethodIOC() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                    iocManager
                        .addProperties("testValue", Properties.class )
                            .addValue("test", "testValue");
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController2.class,
                        ScopeType.REQUEST);

                    wfb.addMethod( "method", "ioc", Properties.class )
                        .addParameter("testValue", ScopeType.IOC);
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setupAddParameter( "invoke", "method" );
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                TestController2 controller =
                    (TestController2)request.getAttribute( "TestController2" );

                assertNotNull( controller );
                assertNotNull( controller.getPropertyIOC() );
                assertEquals( "testValue", controller.getPropertyIOC().getProperty("test") );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testParameterMethodFlash() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController2.class,
                        ScopeType.REQUEST);

                    wfb.addMethod( "method", "flash", String.class )
                        .addParameter("testValue", ScopeType.FLASH);
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setSession(session);
                request.setupAddParameter("invoke", "method");
                listener.requestInitialized(sre);

                Scopes.get("flash").put("testValue", "Teste");

                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                TestController2 controller =
                    (TestController2)request.getAttribute( "TestController2" );

                assertEquals( "Teste", controller.getPropertyFlash() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testParameterMethodApplication() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController2.class,
                        ScopeType.REQUEST);

                    wfb.addMethod( "method", "application", long.class )
                        .addParameter("testValue", ScopeType.APPLICATION);
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            try{
                request.setRequestURI("/test.jbrs");
                request.setContextPath("");
                request.setupAddParameter("invoke", "method");
                servletContext.setAttribute( "testValue", 10L );
                listener.requestInitialized(sre);
                BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
                bc.getInvoker().invoke(null);
                TestController2 controller =
                    (TestController2)request.getAttribute( "TestController2" );

                assertNotNull( controller );
                assertEquals( 10L , controller.getPropertyApplication() );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testAlias() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController.class,
                        ScopeType.REQUEST);

                    wfb.addAlias("/test2.jbrs");
                    wfb.addAlias("/test3.jbrs");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                assertNotNull( "OK", servletContext.getAttribute( "status" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }

            servletContext.removeAttribute("status");
            request.resetAll();
            try{
                request.setRequestURI("/test2.jbrs");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                assertNotNull( "OK", servletContext.getAttribute( "status" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }

            servletContext.removeAttribute("status");
            request.resetAll();
            try{
                request.setRequestURI("/test3.jbrs");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                assertNotNull( "OK", servletContext.getAttribute( "status" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testControllerThrows() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController.class,
                        ScopeType.REQUEST);

                    wfb.addMethod("method", "testException");
                    wfb.addThrowable(Exception.class, "ex");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setupAddParameter("invoke", "method");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                Scope requestScope = Scopes.get(ScopeType.REQUEST);

                assertNotNull( requestScope.get( "ex" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMethodThrows() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController.class,
                        ScopeType.REQUEST);

                    wfb.addMethod("method", "testException")
                        .addThrowable(Exception.class, "methodException");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setupAddParameter("invoke", "method");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                Scope requestScope = Scopes.get(ScopeType.REQUEST);
                assertNotNull( requestScope.get( "methodException" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testHierarchyThrows() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController.class,
                        ScopeType.REQUEST);

                    wfb.addThrowable(Exception.class, "controllerException");
                    wfb.addMethod("method", "testException")
                        .addThrowable(Exception.class, "methodException");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setupAddParameter("invoke", "method");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                Scope requestScope = Scopes.get(ScopeType.REQUEST);
                assertNotNull( requestScope.get( "methodException" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testHierarchyViewThrows() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb =
                        webFrameManager
                            .addWebFrame(
                                "/test.jbrs",
                                "/Test.jsp",
                                "TestController",
                                TestController.class,
                                ScopeType.REQUEST,
                                null );

                    wfb.addThrowable(Exception.class, "controllerException");
                    wfb.addMethod("method", "testException")
                        .addThrowable(Exception.class, "/throw.jsp", "methodException", true);
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setupAddParameter("invoke", "method");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                Scope requestScope = Scopes.get(ScopeType.REQUEST);
                assertNotNull( requestScope.get( "methodException" ) );
                assertEquals( "/throw.jsp" , ((MockViewProvider)bc.getViewProvider()).getView() );
                assertTrue( ((MockViewProvider)bc.getViewProvider()).isRedirect() );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMethodValue() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController.class,
                        ScopeType.REQUEST);

                    wfb.addMethod("method", "testView", "result" );
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setupAddParameter("invoke", "method");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                Scope requestScope = Scopes.get(ScopeType.REQUEST);
                assertNotNull( requestScope.get( "result" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMethodValueInView() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController.class,
                        ScopeType.REQUEST);

                    wfb.addMethod("method", "result", "/method.jsp", "testView" );
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setupAddParameter("invoke", "method");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                assertEquals( "/method.jsp" , ((MockViewProvider)bc.getViewProvider()).getView() );
                Scope requestScope = Scopes.get(ScopeType.REQUEST);
                assertNotNull( requestScope.get( "result" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMethodValueInViewRedirect() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestController.class,
                        ScopeType.REQUEST);

                    wfb.addMethod("method", "result", "/method.jsp", true, "testView");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setupAddParameter("invoke", "method");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                assertEquals( "/method.jsp" , ((MockViewProvider)bc.getViewProvider()).getView() );
                Scope requestScope = Scopes.get(ScopeType.REQUEST);
                assertNotNull( requestScope.get( "result" ) );
                assertTrue( ((MockViewProvider)bc.getViewProvider()).isRedirect() );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMappingBeanProperty() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestMappingBeanController.class,
                        ScopeType.REQUEST);

                    wfb.addMappingBean( "testBean", BeanTest.class )
                        .addProperty("id", "id")
                        .addProperty("name", "name")
                        .addProperty("message", "message");

                    wfb.addPropertyMapping("bean", "bean", "testBean");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setSession(session);
                request.setupAddParameter("id", "100");
                request.setupAddParameter("name", "Test");
                request.setupAddParameter("message", "message test");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                TestMappingBeanController controller =
                    (TestMappingBeanController)request.getAttribute( "TestMappingBeanController" );

                assertNotNull( controller );
                assertEquals( 100 , ((BeanTest)controller.getBean()).getId() );
                assertEquals( "Test" , ((BeanTest)controller.getBean()).getName() );
                assertEquals( "message test" , ((BeanTest)controller.getBean()).getMessage() );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMappingBeanInheritance() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestMappingBeanController.class,
                        ScopeType.REQUEST);

                    wfb.addMappingBean( "testBean", InheritanceBeanTest.class )
                        .addProperty("id", "id")
                        .addProperty("name", "name")
                        .addProperty("message", "message");

                    wfb.addPropertyMapping("bean", "bean", "testBean");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setSession(session);
                request.setupAddParameter("id", "100");
                request.setupAddParameter("name", "Test");
                request.setupAddParameter("message", "message test");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                TestMappingBeanController controller =
                    (TestMappingBeanController)request.getAttribute( "TestMappingBeanController" );

                assertNotNull( controller );
                assertEquals( 100 , ((BeanTest)controller.getBean()).getId() );
                assertEquals( "Test" , ((BeanTest)controller.getBean()).getName() );
                assertEquals( "message test" , ((BeanTest)controller.getBean()).getMessage() );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMappingBeanScope() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestMappingBeanController.class,
                        ScopeType.REQUEST);

                    wfb.addMappingBean( "testBean", BeanTest.class )
                        .addProperty("id", "id", ScopeType.APPLICATION)
                        .addProperty("name", "name")
                        .addProperty("message", "message");

                    wfb.addPropertyMapping("bean", "bean", "testBean");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setSession(session);
                servletContext.setAttribute("id", "100");
                request.setupAddParameter("name", "Test");
                request.setupAddParameter("message", "message test");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                TestMappingBeanController controller =
                    (TestMappingBeanController)request.getAttribute( "TestMappingBeanController" );

                assertNotNull( controller );
                assertEquals( 100 , ((BeanTest)controller.getBean()).getId() );
                assertEquals( "Test" , ((BeanTest)controller.getBean()).getName() );
                assertEquals( "message test" , ((BeanTest)controller.getBean()).getMessage() );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testCollectionMapping() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestMappingBeanController.class,
                        ScopeType.REQUEST);

                    wfb.addMappingCollection("testBean", ArrayList.class)
                        .bean(BeanTest.class)
                            .addProperty("id", "id")
                            .addProperty("name", "name")
                            .addProperty("message", "message");

                    wfb.addPropertyMapping("bean", "bean", "testBean");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setSession(session);
                request.setupAddParameter("id[0]", "100");
                request.setupAddParameter("name[0]", "Test");
                request.setupAddParameter("message[0]", "message test");
                request.setupAddParameter("id[1]", "200");
                request.setupAddParameter("name[1]", "Test2");
                request.setupAddParameter("message[1]", "message test2");

                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                TestMappingBeanController controller =
                    (TestMappingBeanController)request.getAttribute( "TestMappingBeanController" );

                assertNotNull( controller );
                List<BeanTest> list = ((List<BeanTest>)controller.getBean());
                assertTrue( list != null && list.size() == 2 );
                assertEquals( 100 , list.get(0).getId() );
                assertEquals( "Test" , list.get(0).getName() );
                assertEquals( "message test" , list.get(0).getMessage() );
                assertEquals( 200 , list.get(1).getId() );
                assertEquals( "Test2" , list.get(1).getName() );
                assertEquals( "message test2" , list.get(1).getMessage() );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMapMapping() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestMappingBeanController.class,
                        ScopeType.REQUEST);

                    wfb.addMappingMap("testBean", HashMap.class)
                        .setKey(int.class)
                        .bean(BeanTest.class)
                            .addProperty("id", "id")
                            .addProperty("name", "name")
                            .addProperty("message", "message");

                    wfb.addPropertyMapping("bean", "bean", "testBean");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setSession(session);
                request.setupAddParameter("key[0]", "100");
                request.setupAddParameter("id[0]", "100");
                request.setupAddParameter("name[0]", "Test");
                request.setupAddParameter("message[0]", "message test");
                request.setupAddParameter("key[1]", "200");
                request.setupAddParameter("id[1]", "200");
                request.setupAddParameter("name[1]", "Test2");
                request.setupAddParameter("message[1]", "message test2");

                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                TestMappingBeanController controller =
                    (TestMappingBeanController)request.getAttribute( "TestMappingBeanController" );

                assertNotNull( controller );
                Map<Integer,BeanTest> map = ((Map<Integer,BeanTest>)controller.getBean());
                assertTrue( map != null && map.size() == 2 );
                assertEquals( 100 , map.get(100).getId() );
                assertEquals( "Test" , map.get(100).getName() );
                assertEquals( "message test" , map.get(100).getMessage() );
                assertEquals( 200 , map.get(200).getId() );
                assertEquals( "Test2" , map.get(200).getName() );
                assertEquals( "message test2" , map.get(200).getMessage() );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMapMappingKeyScope() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestMappingBeanController.class,
                        ScopeType.REQUEST);

                    wfb.addMappingMap("testBean", HashMap.class)
                        .setKey("key", int.class, ScopeType.APPLICATION)
                        .bean(BeanTest.class)
                            .addProperty("id", "id")
                            .addProperty("name", "name")
                            .addProperty("message", "message");

                    wfb.addPropertyMapping("bean", "bean", "testBean");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setSession(session);
                servletContext.setAttribute("key[0]", "100");
                request.setupAddParameter("id[0]", "100");
                request.setupAddParameter("name[0]", "Test");
                request.setupAddParameter("message[0]", "message test");
                servletContext.setAttribute("key[1]", "200");
                request.setupAddParameter("id[1]", "200");
                request.setupAddParameter("name[1]", "Test2");
                request.setupAddParameter("message[1]", "message test2");

                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                TestMappingBeanController controller =
                    (TestMappingBeanController)request.getAttribute( "TestMappingBeanController" );

                assertNotNull( controller );
                Map<Integer,BeanTest> map = ((Map<Integer,BeanTest>)controller.getBean());
                assertTrue( map != null && map.size() == 2 );
                assertEquals( 100 , map.get(100).getId() );
                assertEquals( "Test" , map.get(100).getName() );
                assertEquals( "message test" , map.get(100).getMessage() );
                assertEquals( 200 , map.get(200).getId() );
                assertEquals( "Test2" , map.get(200).getName() );
                assertEquals( "message test2" , map.get(200).getMessage() );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testHierarchyMapping() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestMappingBeanController.class,
                        ScopeType.REQUEST);

                    wfb.addMappingBean("hierarchy", Bean2Test.class)
                        .addProperty("id", "id")
                        .addProperty("nome", "nome");
                        
                    wfb.addMappingBean( "testBean", BeanTest.class )
                            .addProperty("id", "id")
                            .addProperty("name", "name")
                            .addProperty("message", "message")
                            .addMappedProperty("testinheritance","testhierarchy", "hierarchy");

                    wfb.addPropertyMapping("bean", "bean", "testBean");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setSession(session);
                request.setupAddParameter("id", "100");
                request.setupAddParameter("name", "Test");
                request.setupAddParameter("message", "message test");
                request.setupAddParameter("testinheritance.id", "1");
                request.setupAddParameter("testinheritance.nome", "jose");

                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                TestMappingBeanController controller =
                    (TestMappingBeanController)request.getAttribute( "TestMappingBeanController" );

                assertNotNull( controller );
                assertEquals( 100 , ((BeanTest)controller.getBean()).getId() );
                assertEquals( "Test" , ((BeanTest)controller.getBean()).getName() );
                assertEquals( "message test" , ((BeanTest)controller.getBean()).getMessage() );

                Bean2Test b2t = ((BeanTest)controller.getBean()).getTesthierarchy();
                assertEquals( 1 , b2t.getId() );
                assertEquals( "jose" , b2t.getNome() );

            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testHierarchyMappingMap() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestMappingBeanController.class,
                        ScopeType.REQUEST);

                    wfb.addMappingMap("hierarchy", HashMap.class)
                        .setKey(int.class)
                        .bean(Bean2Test.class)
                            .addProperty("id", "id")
                            .addProperty("name", "nome");

                    wfb.addMappingBean( "testBean", BeanTest.class )
                            .addMappedProperty("testhierarchy","testHierarchyMap", "hierarchy");

                    wfb.addPropertyMapping("bean", "bean", "testBean");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setSession(session);
                request.setupAddParameter("testhierarchy.key[0]", "100");
                request.setupAddParameter("testhierarchy.id[0]", "100");
                request.setupAddParameter("testhierarchy.name[0]", "Test");
                request.setupAddParameter("testhierarchy.key[1]", "200");
                request.setupAddParameter("testhierarchy.id[1]", "200");
                request.setupAddParameter("testhierarchy.name[1]", "Test2");

                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                TestMappingBeanController controller =
                    (TestMappingBeanController)request.getAttribute( "TestMappingBeanController" );

                assertNotNull( controller );
                Map<Integer,Bean2Test> map = ((BeanTest)controller.getBean()).getTestHierarchyMap();
                assertTrue( map != null && map.size() == 2 );
                assertEquals( 100 , map.get(100).getId() );
                assertEquals( "Test" , map.get(100).getNome() );
                assertEquals( 200 , map.get(200).getId() );
                assertEquals( "Test2" , map.get(200).getNome() );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testHierarchyMappingList() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager.addWebFrame(
                        "/test.jbrs",
                        TestMappingBeanController.class,
                        ScopeType.REQUEST);

                    wfb.addMappingCollection("hierarchy", ArrayList.class)
                        .bean(Bean2Test.class)
                            .addProperty("id", "id")
                            .addProperty("name", "nome");

                    wfb.addMappingBean( "testBean", BeanTest.class )
                            .addMappedProperty("testhierarchy","testHierarchyList", "hierarchy");

                    wfb.addPropertyMapping("bean", "bean", "testBean");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                }
            };

        servletContext
            .setInitParameter(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        servletContext
            .setInitParameter(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        try{
            MockApplicationContext.setCurrentApplicationContext(app);
            listener.contextInitialized(sce);
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            MockHttpSession session = new MockHttpSession();
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, request);

            BrutosContext bc = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setSession(session);
                request.setupAddParameter("testhierarchy.key[0]", "100");
                request.setupAddParameter("testhierarchy.id[0]", "100");
                request.setupAddParameter("testhierarchy.name[0]", "Test");
                request.setupAddParameter("testhierarchy.key[1]", "200");
                request.setupAddParameter("testhierarchy.id[1]", "200");
                request.setupAddParameter("testhierarchy.name[1]", "Test2");

                listener.requestInitialized(sre);
                bc.getInvoker().invoke(null);
                TestMappingBeanController controller =
                    (TestMappingBeanController)request.getAttribute( "TestMappingBeanController" );

                assertNotNull( controller );
                List<Bean2Test> list = ((BeanTest)controller.getBean()).getTestHierarchyList();
                assertTrue( list != null && list.size() == 2 );
                assertEquals( 100 , list.get(0).getId() );
                assertEquals( "Test" , list.get(0).getNome() );
                assertEquals( 200 , list.get(1).getId() );
                assertEquals( "Test2" , list.get(1).getNome() );
            }
            finally{
                listener.requestDestroyed(sre);
            }

        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

}
