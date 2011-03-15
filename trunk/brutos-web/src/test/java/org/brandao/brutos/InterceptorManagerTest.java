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

import org.brandao.brutos.old.programatic.WebFrameBuilder;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.old.programatic.WebFrameManager;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockServletContext;
import java.io.IOException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import junit.framework.TestCase;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosContext;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.ContextLoaderListener;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.test.MockApplicationContext;

/**
 *
 * @author Afonso Brandao
 */
public class InterceptorManagerTest extends TestCase{

    public static class InvalidInterceptor{
    }

    public static class Interceptor extends AbstractInterceptor{

        public void intercepted(InterceptorStack stack, InterceptorHandler handler) throws InterceptedException {
            handler.getContext().setAttribute("OK", true);
            handler.getContext().setAttribute("testProperty", this.props.get("prop"));
        }

    }

    public static class DefaultController{

        public void methodI(){}
        public void methodII(){}
        public void methodIII(){}
        public void methodIV(){}
        
    }

    public void testNotValidInterceptor() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                    interceptorManager.addInterceptor("myInterceptor", InvalidInterceptor.class, false);
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
            assertNotNull( servletContext.getAttribute( BrutosConstants.EXCEPTION ) );
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testInterceptor() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                    iocManager.addBean("myInterceptor", Interceptor.class, ScopeType.REQUEST);
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    webFrameManager.addWebFrame( "/test.jbrs", DefaultController.class )
                            .addInterceptor("myInterceptor");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                    interceptorManager.addInterceptor("myInterceptor", Interceptor.class, false);
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
                WebApplicationContext bc = (WebApplicationContext) ApplicationContext.getCurrentApplicationContext();
                bc.getInvoker().invoke((BrutosContext)bc, response);
                assertNotNull( servletContext.getAttribute( "OK" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testDefaultInterceptor() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                    iocManager.addBean("myInterceptor", Interceptor.class, ScopeType.REQUEST);
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    webFrameManager.addWebFrame( "/test.jbrs", DefaultController.class );
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                    interceptorManager.addInterceptor("myInterceptor", Interceptor.class, true);
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
                WebApplicationContext bc = (WebApplicationContext) WebApplicationContext.getCurrentApplicationContext();
                bc.getInvoker().invoke((BrutosContext)bc, response);
                assertNotNull( servletContext.getAttribute( "OK" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMethodInterceptor() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                    iocManager.addBean("myInterceptor", Interceptor.class, ScopeType.REQUEST);
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager
                        .addWebFrame( "/test.jbrs", DefaultController.class );

                    wfb.addInterceptor("myInterceptor");
                    
                    wfb.addMethod("methodI", "methodI");
                    wfb.addMethod("methodII", "methodII");
                    wfb.addMethod("methodIII", "methodIII");
                    wfb.addMethod("methodIV", "methodIV");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                    interceptorManager.addInterceptor("myInterceptor", Interceptor.class, false);
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
                request.setupAddParameter("invoke", "methodI");
                listener.requestInitialized(sre);
                ConfigurableApplicationContext bc = (ConfigurableApplicationContext) ApplicationContext.getCurrentApplicationContext();
                bc.getInvoker().invoke((BrutosContext)bc, response);
                assertNotNull( servletContext.getAttribute( "OK" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testExcludeMethod() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                    iocManager.addBean("myInterceptor", Interceptor.class, ScopeType.REQUEST);
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager
                        .addWebFrame( "/test.jbrs", DefaultController.class );

                    wfb.addInterceptor("myInterceptor")
                        .addParameter("excludeMethods", "methodI,methodII");

                    wfb.addMethod("methodI", "methodI");
                    wfb.addMethod("methodII", "methodII");
                    wfb.addMethod("methodIII", "methodIII");
                    wfb.addMethod("methodIV", "methodIV");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                    interceptorManager.addInterceptor("myInterceptor", Interceptor.class, false);
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
                request.setupAddParameter("invoke", "methodI");
                listener.requestInitialized(sre);
                ConfigurableApplicationContext bc = (ConfigurableApplicationContext) ApplicationContext.getCurrentApplicationContext();
                bc.getInvoker().invoke((BrutosContext)bc, response);
                assertNull( servletContext.getAttribute( "OK" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testLocalParameter() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                    iocManager.addBean("myInterceptor", Interceptor.class, ScopeType.REQUEST);
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager
                        .addWebFrame( "/test.jbrs", DefaultController.class );

                    wfb.addInterceptor("myInterceptor")
                        .addParameter("prop", "value1");

                    wfb.addMethod("methodI", "methodI");
                    wfb.addMethod("methodII", "methodII");
                    wfb.addMethod("methodIII", "methodIII");
                    wfb.addMethod("methodIV", "methodIV");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                    interceptorManager.addInterceptor("myInterceptor", Interceptor.class, false);
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
                request.setupAddParameter("invoke", "methodI");
                listener.requestInitialized(sre);
                ConfigurableApplicationContext bc = (ConfigurableApplicationContext) WebApplicationContext.getCurrentApplicationContext();
                bc.getInvoker().invoke((BrutosContext)bc, response);
                assertEquals("value1", servletContext.getAttribute( "testProperty" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testOverrideParameterInStack() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                    iocManager.addBean("myInterceptor", Interceptor.class, ScopeType.REQUEST);
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager
                        .addWebFrame( "/test.jbrs", DefaultController.class );

                    wfb.addInterceptor("myStack");
                        

                    wfb.addMethod("methodI", "methodI");
                    wfb.addMethod("methodII", "methodII");
                    wfb.addMethod("methodIII", "methodIII");
                    wfb.addMethod("methodIV", "methodIV");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                    interceptorManager.addInterceptor("myInterceptor", Interceptor.class, false)
                            .addParameter("prop", "value1");
                    
                    interceptorManager.addInterceptorStack("myStack", false)
                            .addInterceptor("myInterceptor")
                                .addParameter( "prop" , "value2");
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
                request.setupAddParameter("invoke", "methodI");
                listener.requestInitialized(sre);
                ConfigurableApplicationContext bc = (ConfigurableApplicationContext) WebApplicationContext.getCurrentApplicationContext();
                bc.getInvoker().invoke((BrutosContext)bc, response);
                assertEquals("value2", servletContext.getAttribute( "testProperty" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testOverrideParameterLocal() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                    iocManager.addBean("myInterceptor", Interceptor.class, ScopeType.REQUEST);
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager
                        .addWebFrame( "/test.jbrs", DefaultController.class );

                    wfb.addInterceptor("myStack")
                        .addParameter("myInterceptor.prop", "value3");


                    wfb.addMethod("methodI", "methodI");
                    wfb.addMethod("methodII", "methodII");
                    wfb.addMethod("methodIII", "methodIII");
                    wfb.addMethod("methodIV", "methodIV");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                    interceptorManager.addInterceptor("myInterceptor", Interceptor.class, false)
                            .addParameter("prop", "value1");

                    interceptorManager.addInterceptorStack("myStack", false)
                            .addInterceptor("myInterceptor")
                                .addParameter( "prop" , "value2");
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
                request.setupAddParameter("invoke", "methodI");
                listener.requestInitialized(sre);
                ConfigurableApplicationContext bc = (ConfigurableApplicationContext) WebApplicationContext.getCurrentApplicationContext();
                bc.getInvoker().invoke((BrutosContext)bc, response);
                assertEquals("value3", servletContext.getAttribute( "testProperty" ) );
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testGlobalParameter() throws IOException{
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                    iocManager.addBean("myInterceptor", Interceptor.class, ScopeType.REQUEST);
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                    WebFrameBuilder wfb = webFrameManager
                        .addWebFrame( "/test.jbrs", DefaultController.class );

                    wfb.addInterceptor("myInterceptor");

                    wfb.addMethod("methodI", "methodI");
                    wfb.addMethod("methodII", "methodII");
                    wfb.addMethod("methodIII", "methodIII");
                    wfb.addMethod("methodIV", "methodIV");
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                    interceptorManager.addInterceptor("myInterceptor", Interceptor.class, false)
                        .addParameter("prop", "value1");
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
                request.setupAddParameter("invoke", "methodI");
                listener.requestInitialized(sre);
                ConfigurableApplicationContext bc = (ConfigurableApplicationContext) WebApplicationContext.getCurrentApplicationContext();
                bc.getInvoker().invoke((BrutosContext)bc, response);
                assertEquals("value1", servletContext.getAttribute( "testProperty" ) );
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
