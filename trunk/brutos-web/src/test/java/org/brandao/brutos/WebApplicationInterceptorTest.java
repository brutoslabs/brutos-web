


package org.brandao.brutos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.helper.controller.AbstractTester;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.WebScopeType;
import org.brandao.brutos.web.XMLWebApplicationContext;


public class WebApplicationInterceptorTest extends AbstractTester implements Test{

    public Class getApplicationContext(String resourceName) {
        return XMLWebApplicationContext.class;
    }

    public void testInterceptor1(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/interceptor/interceptor-test1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(WebScopeType.PARAM).put("action", "defaultAction");
                    Scope r = app.getScopes().get(WebScopeType.REQUEST);
                    app.getInvoker().invoke("/testController.htm");
                    String result =
                            (String) app.getScopes().get(WebScopeType.REQUEST).get("result");

                    TestCase.assertEquals("OK", result);
                    TestCase.assertEquals("value1", r.get("param1"));
                    TestCase.assertEquals("value2", r.get("param2"));
                }
            
        });
    }

    public void testInterceptor2(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/interceptor/interceptor-test2.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(WebScopeType.PARAM).put("action", "defaultAction");
                    Scope r = app.getScopes().get(WebScopeType.REQUEST);
                    app.getInvoker().invoke("/testController.htm");
                    String result =
                            (String) app.getScopes().get(WebScopeType.REQUEST).get("result");

                    TestCase.assertEquals("OK", result);
                    TestCase.assertEquals("value3", r.get("param1"));
                    TestCase.assertEquals("value2", r.get("param2"));
                }

        });
    }

    public void testInterceptor3(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/interceptor/interceptor-test3.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(WebScopeType.PARAM).put("action", "defaultAction");
                    Scope r = app.getScopes().get(WebScopeType.REQUEST);
                    app.getInvoker().invoke("/testController.htm");
                    String result =
                            (String) app.getScopes().get(WebScopeType.REQUEST).get("result");

                    TestCase.assertEquals("OK", result);
                    TestCase.assertEquals("value4", r.get("param1"));
                    TestCase.assertEquals("value2", r.get("param2"));
                }

        });
    }

    public void testInterceptor4(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/interceptor/interceptor-test4.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(WebScopeType.PARAM).put("action", "defaultAction");
                    Scope r = app.getScopes().get(WebScopeType.REQUEST);
                    app.getInvoker().invoke("/testController.htm");
                    String result =
                            (String) app.getScopes().get(WebScopeType.REQUEST).get("result");

                    TestCase.assertEquals("OK", result);
                    TestCase.assertEquals("value4", r.get("param1"));
                    TestCase.assertEquals("value2", r.get("param2"));
                }

        });
    }

}
