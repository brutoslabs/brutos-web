/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.web;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.ActionResolver;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.ControllerResolver;
import org.brandao.brutos.ObjectFactory;
import org.brandao.brutos.RenderView;
import org.brandao.brutos.helper.controller.AbstractTester;
import org.brandao.brutos.web.http.StaticBrutosRequest;
import org.brandao.brutos.web.test.MockWebInvoker;


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
                        
                        MockHttpServletRequest mr = (MockHttpServletRequest) request;
                        mr.setRequestURI("/TestController.htm");

                        dispatcherServlet.init(sc);
                        dispatcherServlet.processRequest(request, response);
                        TestCase.assertEquals("/TestController.htm", ((MockWebInvoker)app.getInvoker()).getRequestId());

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
        try{
            super.setInvokerClass(AssertMockWebInvoker.class);
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

                            MockHttpServletRequest mr = (MockHttpServletRequest) request;
                            mr.setRequestURI("/TestController.htm");
                            ((AssertMockWebInvoker)app.getInvoker()).setExpectedResponse(response);

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
        finally{
            super.setInvokerClass(null);
        }
    }

    public static class AssertMockWebInvoker extends MockWebInvoker{

        private HttpServletResponse expectedResponse;
        
        public boolean invoke(String value, Throwable externalThrow ){
            RequestInfo ri = RequestInfo.getCurrentRequestInfo();

            TestCase.assertNotNull(ri);
            TestCase.assertEquals(expectedResponse, ri.getResponse());
            TestCase.assertTrue(ri.getRequest() instanceof StaticBrutosRequest);
            return true;
        }

        public HttpServletResponse getExpectedResponse() {
            return expectedResponse;
        }

        public void setExpectedResponse(HttpServletResponse expectedResponse) {
            this.expectedResponse = expectedResponse;
        }
        
    }
}
