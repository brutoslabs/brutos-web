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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.helper.controller.AbstractTester;
import org.brandao.brutos.io.ClassPathResource;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.GenericXMLWebApplicationContext;

/**
 *
 * @author Brandao
 */
public class WebApplicationInterceptorTest extends AbstractTester implements Test{

    public ConfigurableWebApplicationContext getApplicationContext(String resourceName) {
        return new GenericXMLWebApplicationContext(
                new Resource[]{
                    new ClassPathResource( 
                            getClass(),
                            resourceName )});
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
