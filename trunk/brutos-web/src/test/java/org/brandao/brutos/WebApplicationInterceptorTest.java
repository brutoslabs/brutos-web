/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

/**
 *
 * @author Brandao
 */
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
