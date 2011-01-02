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

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockServletContext;
import java.io.IOException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import junit.framework.TestCase;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosContext;
import org.brandao.brutos.ContextLoaderListener;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.test.MockApplicationContext;
import org.brandao.brutos.validator.RestrictionRules;

/**
 *
 * @author Afonso Brandao
 */
public class ValidatorTest extends TestCase {

    public static class TestController {
        
        private int number;
        private String text;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }

    public void testMinProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("number", "number",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.MIN, 10 );
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
                request.setupAddParameter("number", "10");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                bc.getInvoker().invoke(bc, response);
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMinErrorProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("number", "number",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.MIN, 10 );
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
                request.setupAddParameter("number", "9");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                try{
                    bc.getInvoker().invoke(bc, response);
                    fail("expected: ValidatoException");
                }
                catch( Exception e ){}
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMaxProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("number", "number",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.MAX, 10 );
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
                request.setupAddParameter("number", "10");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                bc.getInvoker().invoke(bc, response);
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMaxErrorProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("number", "number",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.MAX, 10 );
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
                request.setupAddParameter("number", "11");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                try{
                    bc.getInvoker().invoke(bc, response);
                    fail("expected: ValidatoException");
                }
                catch( Exception e ){}
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testEqualNumberProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("number", "number",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.EQUAL, 10 );
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
                request.setupAddParameter("number", "10");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                bc.getInvoker().invoke(bc, response);
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testEqualNumberErrorProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("number", "number",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.EQUAL, 11 );
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
                request.setupAddParameter("number", "10");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                try{
                    bc.getInvoker().invoke(bc, response);
                    fail("expected: ValidatoException");
                }
                catch( Exception e ){}
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testEqualTextProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("text", "text",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.EQUAL, "Hi" );
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
                request.setupAddParameter("text", "Hi");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                bc.getInvoker().invoke(bc, response);
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testEqualTextErrorProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("text", "text",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.EQUAL, "Hi" );
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
                request.setupAddParameter("text", "hi");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                try{
                    bc.getInvoker().invoke(bc, response);
                    fail("expected: ValidatoException");
                }
                catch( Exception e ){}
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMinLengthProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("text", "text",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.MIN_LENGTH, 3 );
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
                request.setupAddParameter("text", "kkk");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                bc.getInvoker().invoke(bc, response);
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMinLengthErrorProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("text", "text",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.MIN_LENGTH, 3 );
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
                request.setupAddParameter("text", "kk");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                try{
                    bc.getInvoker().invoke(bc, response);
                    fail("expected: ValidatoException");
                }
                catch( Exception e ){}
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMaxLengthProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("text", "text",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.MAX_LENGTH, 6 );
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
                request.setupAddParameter("text", "kkkkkk");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                bc.getInvoker().invoke(bc, response);
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMaxLengthErrorProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("text", "text",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.MAX_LENGTH, 6 );
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
                request.setupAddParameter("text", "kkkkkkk");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                try{
                    bc.getInvoker().invoke(bc, response);
                    fail("expected: ValidatoException");
                }
                catch( Exception e ){}
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMatchesProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("text", "text",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.MATCHES, "^\\d{2}.\\d{3}\\-?\\d{3}$" );
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
                request.setupAddParameter("text", "14.940-000");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                bc.getInvoker().invoke(bc, response);
            }
            finally{
                listener.requestDestroyed(sre);
            }
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testMatchesErrorProperty() throws IOException{
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
                        .addWebFrame( "/test.jbrs", TestController.class )
                            .addProperty("text", "text",ScopeType.REQUEST)
                                .addRestriction(RestrictionRules.MATCHES, "^\\d{2}.\\d{3}\\-?\\d{3}$" );
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
                request.setupAddParameter("text", "aa.940-000");
                listener.requestInitialized(sre);
                BrutosContext bc = BrutosContext.getCurrentInstance();
                try{
                    bc.getInvoker().invoke(bc, response);
                    fail("expected: ValidatoException");
                }
                catch( Exception e ){}
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
