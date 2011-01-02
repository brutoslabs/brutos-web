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

import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.ContextLoaderListener;
import org.brandao.brutos.BrutosContext;
import com.mockrunner.mock.web.MockServletContext;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import junit.framework.TestCase;
import org.brandao.brutos.ioc.CustomEditorConfigurer;
import org.brandao.brutos.ioc.editors.DateEditorSupport;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.scope.CustomScopeConfigurer;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.scope.Scopes;
import org.brandao.brutos.test.MockApplicationContext;
/**
 *
 * @author Neto
 */
public class TestBrutosContext extends TestCase{

    public static class TestScope implements Scope{

        public void put(String name, Object value) {
        }

        public Object get(String name) {
            return null;
        }

        public Object getCollection(String name) {
            return null;
        }

        public void remove(String name) {
        }
    }

    public static class TestEditors{

        private Object value;

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    public static class TestControllerResolver implements ControllerResolver{

        public Form getController(WebFrameManager webFrameManager, HttpServletRequest request) {
            return null;
        }

        public Form getController(ControllerManager controllerManager, InterceptorHandler handler) {
            return null;
        }

    }

    public static class TestMethodResolver implements MethodResolver{

        public ResourceMethod getResourceMethod(HttpServletRequest request) {
            return null;
        }

        public ResourceMethod getResourceMethod(Form controller, Scope scope) {
            return null;
        }

        public ResourceAction getResourceAction(Form controller, Scope scope) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    public void testNaturalLoad(){
        ContextLoaderListener cll = new ContextLoaderListener();
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        cll.contextInitialized(sce);
        assertNull( servletContext.getAttribute(BrutosConstants.EXCEPTION) );
    }

    public void testRegisterScopeIOC(){
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {

                    iocManager.addBean(
                        "scope",
                        TestScope.class );

                    iocManager.addMap(
                        "scopes",
                        java.util.HashMap.class,
                        String.class,
                        Scope.class,
                        null )
                            .addBean(
                                "testScope",
                                "scope" );
                    
                    iocManager.addBean(
                        "customScopes",
                        CustomScopeConfigurer.class )
                            .addPropertyRef(
                                "customScopes",
                                "scopes" );
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
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
            assertNotNull( Scopes.get( "testScope" ) );
        }
        finally{
            listener.contextDestroyed(sce);
        }
    }

    public void testRegisterEditorIOC() throws ParseException{
        ContextLoaderListener listener = new ContextLoaderListener();
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ApplicationContext app = new ApplicationContext(){

                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {

                    iocManager.addBean("dateFormat", SimpleDateFormat.class )
                        .addConstructiorArg( "dd/MM/yyyy" );

                    iocManager.addBean(
                        "dateEditor",
                        DateEditorSupport.class )
                            .addConstructiorRefArg( "dateFormat" )
                            .addConstructiorArg( false );

                    iocManager.addMap(
                        "editors",
                        java.util.HashMap.class,
                        String.class,
                        PropertyEditorSupport.class,
                        null )
                            .addBean(
                                "java.util.Date",
                                "dateEditor" );

                    iocManager.addBean(
                        "customEditors",
                        CustomEditorConfigurer.class )
                            .addPropertyRef(
                                "customEditors",
                                "editors" );

                     iocManager.addBean( "testEditor" , TestEditors.class )
                        .addPropertyValue("value", java.util.Date.class, "29/08/1984");
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
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
                TestEditors o = 
                    (TestEditors)BrutosContext
                        .getCurrentApplicationContext()
                            .getIocManager().getInstance( "testEditor" );
                SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
                assertEquals( sdf.parse( "29/08/1984" ), o.getValue() );
            }
            finally{
                listener.contextDestroyed(sce);
            }


    }

    public void testCustomResolveController(){
        ContextLoaderListener cll = new ContextLoaderListener();
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        
        servletContext.setInitParameter(
            "org.brandao.brutos.controller.class",
            TestControllerResolver.class.getName() );
        
        try{
            cll.contextInitialized(sce);
            BrutosContext c = (BrutosContext) BrutosContext.getCurrentApplicationContext();
            assertEquals(
                TestControllerResolver.class,
                c.getControllerResolver().getClass() );
        }
        finally{
            cll.contextDestroyed(sce);
        }
    }

    public void testCustomMethodResolver(){
        ContextLoaderListener cll = new ContextLoaderListener();
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );

        servletContext.setInitParameter(
            "org.brandao.brutos.controller.method_resolver",
            TestMethodResolver.class.getName() );

        try{
            cll.contextInitialized(sce);
            ApplicationContext c = BrutosContext.getCurrentApplicationContext();
            assertEquals(
                TestMethodResolver.class,
                c.getActionResolver().getClass() );
        }
        finally{
            cll.contextDestroyed(sce);
        }
    }

}
