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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.helper.controller.AbstractTester;
import org.brandao.brutos.helper.controller.EnumTest;
import org.brandao.brutos.helper.controller.SimpleBean;
import org.brandao.brutos.helper.controller.SimpleController;
import org.brandao.brutos.io.ClassPathResource;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.test.MockViewProvider;
import org.brandao.brutos.validator.ValidatorException;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.GenericXMLWebApplicationContext;

/**
 *
 * @author Brandao
 */
public class WebApplicationControllerTest extends AbstractTester implements Test{

    public ConfigurableWebApplicationContext getApplicationContext(String resourceName) {
        return new GenericXMLWebApplicationContext(
                new Resource[]{
                    new ClassPathResource( 
                            getClass(),
                            resourceName )});
    }
 

    public void testController1(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("action", "defaultAction");
                    app.getInvoker().invoke("/testController.htm");
                    String result =
                            (String) app.getScopes().get(ScopeType.REQUEST).get("result");

                    TestCase.assertEquals("OK", result);
                }
            
        });
    }

    public void testController2(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test2.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getInvoker().invoke("/testController.htm");
                    String result =
                            (String) app.getScopes().get(ScopeType.REQUEST).get("result");

                    TestCase.assertEquals("OK", result);
                }

        });
    }

    public void testController3(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test3.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getInvoker().invoke("/testController.htm");
                    String result =
                            (String) app.getScopes().get(ScopeType.REQUEST).get("result");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("OK", result);
                    TestCase.assertEquals("/view.jsp", view.getView());
                }

        });
    }

    public void testController4(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test4.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getInvoker().invoke("/testController.htm");
                    String result =
                            (String) app.getScopes().get(ScopeType.REQUEST).get("result");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("OK", result);
                    TestCase.assertEquals("/viewAction.jsp", view.getView());
                }

        });
    }

    public void testController5(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test5.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getInvoker().invoke("/testController.htm");
                    String result =
                            (String) app.getScopes().get(ScopeType.REQUEST).get("result");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("OK", result);
                    TestCase.assertEquals("/viewAction.jsp", view.getView());
                }

        });
    }

    public void testController6(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test6.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getInvoker().invoke("/testController.htm");
                    String result =
                            (String) app.getScopes().get(ScopeType.REQUEST).get("result");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("OK", result);
                    TestCase.assertEquals("/viewAction.jsp", view.getView());
                    TestCase.assertEquals(DispatcherType.REDIRECT, view.getDispatcherType());
                }

        });
    }

}
