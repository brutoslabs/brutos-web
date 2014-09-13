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


package org.brandao.brutos.web;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.helper.controller.AbstractTester;
import org.brandao.brutos.test.MockInvoker;
import org.brandao.brutos.web.http.StaticBrutosRequest;

/**
 *
 * @author Brandao
 */
public class DispatcherServletTest extends AbstractTester implements Test{

    public Class getApplicationContext(String resourceName) {
        return XMLWebApplicationContext.class;
    }

    public void testInitAndDestroy(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/dispatcherservlet/dispatcher-servlet-test1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    DispatcherServlet dispatcherServlet =
                            new DispatcherServlet();

                    try{
                        MockServletConfig sc = new MockServletConfig();
                        sc.setServletContext(
                            ((ConfigurableWebApplicationContext)app).getContext());
                        dispatcherServlet.init(sc);
                        dispatcherServlet.destroy();
                    }
                    catch(Exception e){
                        throw new RuntimeException(e);
                    }
                }
        });
    }

    public void testInitWithError(){
        DispatcherServlet dispatcherServlet =
                new DispatcherServlet();

        try{
            dispatcherServlet.init();
            TestCase.fail("expected IllegalArgumentException");
        }
        catch(IllegalStateException e){
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void testRequest(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/dispatcherservlet/dispatcher-servlet-test1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    DispatcherServlet dispatcherServlet =
                            new DispatcherServlet();

                    try{
                        MockServletConfig sc = new MockServletConfig();
                        sc.setServletContext(
                            ((ConfigurableWebApplicationContext)app).getContext());
                        
                        MockInvoker invoker = new MockInvoker();
                        MockHttpServletRequest mr = (MockHttpServletRequest) request;
                        mr.setRequestURI("/TestController.htm");
                        app.setInvoker(invoker);

                        dispatcherServlet.init(sc);
                        dispatcherServlet.processRequest(request, response);
                        TestCase.assertEquals("/TestController.htm", invoker.getRequestId());

                    }
                    catch(Exception e){
                        throw new RuntimeException(e);
                    }
                    finally{
                        dispatcherServlet.destroy();
                    }
                }
        });
    }

    public void testRequest2(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/dispatcherservlet/dispatcher-servlet-test1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        final HttpServletRequest request, final HttpServletResponse response) {

                    DispatcherServlet dispatcherServlet =
                            new DispatcherServlet();

                    try{
                        MockServletConfig sc = new MockServletConfig();
                        sc.setServletContext(
                            ((ConfigurableWebApplicationContext)app).getContext());

                        Invoker invoker = new Invoker(){

                            public boolean invoke(String value){
                                RequestInfo ri = RequestInfo.getCurrentRequestInfo();

                                TestCase.assertNotNull(ri);
                                TestCase.assertEquals(response, ri.getResponse());
                                TestCase.assertTrue(ri.getRequest() instanceof StaticBrutosRequest);
                                return true;
                            }
                        };
                        MockHttpServletRequest mr = (MockHttpServletRequest) request;
                        mr.setRequestURI("/TestController.htm");
                        app.setInvoker(invoker);

                        dispatcherServlet.init(sc);
                        dispatcherServlet.processRequest(request, response);
                    }
                    catch(Exception e){
                        throw new RuntimeException(e);
                    }
                    finally{
                        dispatcherServlet.destroy();
                    }
                }
        });
    }

}
