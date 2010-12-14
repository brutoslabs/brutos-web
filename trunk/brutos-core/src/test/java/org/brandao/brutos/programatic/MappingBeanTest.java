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

package org.brandao.brutos.programatic;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import junit.framework.TestCase;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.test.MockApplicationContext;
import org.brandao.brutos.web.ContextLoaderListener;
import org.brandao.brutos.web.WebApplicationContext;

/**
 *
 * @author Afonso Brandao
 */
public class MappingBeanTest extends TestCase {

    public void testProperty(){
        MockServletContext servletContext = new MockServletContext();
        ServletContextEvent sce = new ServletContextEvent( servletContext );
        ContextLoaderListener listener = new ContextLoaderListener();
        ApplicationContext app = new ApplicationContext(){


                public void destroy() {
                }

                public void loadIOCManager(IOCManager iocManager) {
                    iocManager
                        .addBean("TestController", MappingBeanTestHelper.TestController.class );
                }

                public void loadWebFrameManager(WebFrameManager webFrameManager) {
                }

                public void loadInterceptorManager(InterceptorManager interceptorManager) {
                }

                protected void loadController(ControllerManager controllerManager) {
                    ControllerBuilder cb = controllerManager.addController("/test.jbrs",
                            MappingBeanTestHelper.TestController.class );

                    cb
                        .addMappingBean("bean",MappingBeanTestHelper.MyBean.class )
                            .addProperty("intValue", "intProperty");

                    cb.addAction("testAction", "testAction")
                            .addParameterMapping("bean", MappingBeanTestHelper.MyBean.class );
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

            ApplicationContext bc = WebApplicationContext.getCurrentApplicationContext();
            request.setContextPath("");
            try{
                request.setRequestURI("/test.jbrs");
                request.setSession(session);
                request.setupAddParameter("intValue", "100");
                listener.requestInitialized(sre);
                bc.getInvoker().invoke("/test.jbrs");
                MappingBeanTestHelper.TestController controller =
                    (MappingBeanTestHelper.TestController)request.getAttribute( "TestController" );

                assertNotNull( controller );
                assertEquals( 100 , controller.getMyBeanProperty().getIntProperty() );
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
