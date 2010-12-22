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
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import junit.framework.TestCase;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.TestHelper;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.programatic.MappingBeanTestHelper.TestController;
import org.brandao.brutos.scope.Scopes;
import org.brandao.brutos.test.MockApplicationContext;
import org.brandao.brutos.web.ContextLoaderListener;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.ioc.RootContainer;

/**
 *
 * @author Afonso Brandao
 */
public class MappingBeanTest extends TestCase {

    public void testProperty(){
        
        Map initParams = new HashMap();
        initParams
            .put(
                "org.brandao.brutos.applicationcontext",
                "org.brandao.brutos.test.MockApplicationContext");
        initParams
            .put(
                "org.brandao.brutos.view.provider",
                "org.brandao.brutos.test.MockViewProvider");

        TestHelper.executeTest(null, initParams, "/test.jbrs", new TestHelper.TestExecutor() {

            public void execute(MockHttpServletRequest request, MockHttpServletResponse response, 
                    MockHttpSession session, MockServletContext context) {

                ApplicationContext app = WebApplicationContext
                        .getCurrentApplicationContext();

                RootContainer.getInstance().addBean(
                        "controllerInstance",
                        MappingBeanTestHelper.TestController.class,
                        org.brandao.ioc.ScopeType.REQUEST);
                
                ControllerManager controllerManager = app.getControllerManager();
                    ControllerBuilder cb = controllerManager.addController("/test.jbrs",
                            null, "controllerInstance",
                            MappingBeanTestHelper.TestController.class, "invoke" );

                    cb.addMappingBean("bean",MappingBeanTestHelper.MyBean.class )
                            .addProperty("intValue", "intProperty");

                    cb.addAction("testAction", "testAction")
                            .addParameterMapping("bean", MappingBeanTestHelper.MyBean.class );

                request.setupAddParameter("intValue", "100");
                request.setupAddParameter("invoke", "testAction");
                app.getInvoker().invoke(request.getRequestURI());
                TestController controller = (TestController) Scopes
                        .get(ScopeType.REQUEST).get("controllerInstance");

                assertNotNull( controller.getMyBeanProperty() );
                assertEquals(100,controller.getMyBeanProperty().getIntProperty());
            }
        });
    }
}
