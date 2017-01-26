


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
        
        public AssertMockWebInvoker(ControllerResolver controllerResolver, 
                ObjectFactory objectFactory, ControllerManager controllerManager, 
                ActionResolver actionResolver, ConfigurableApplicationContext applicationContext, 
                RenderView renderView) {
            super(controllerResolver, objectFactory, controllerManager, 
                    actionResolver, applicationContext, renderView);
        }

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
