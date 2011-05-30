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

    public void testController7(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test7.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
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

    public void testController8(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test8.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getScopes().get(ScopeType.PARAM).put("value", "100");
                    app.getInvoker().invoke("/testController.htm");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("/viewAction.jsp", view.getView());
                    TestCase.assertEquals(DispatcherType.REDIRECT, view.getDispatcherType());
                }

        });
    }

    public void testController9(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test9.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getScopes().get(ScopeType.PARAM).put("value", "100");
                    app.getInvoker().invoke("/testController.htm");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("/viewAction.jsp", view.getView());
                    TestCase.assertEquals(DispatcherType.REDIRECT, view.getDispatcherType());
                }

        });
    }

    public void testController10(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test10.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getScopes().get(ScopeType.PARAM).put("value", "100");
                    try{
                        app.getInvoker().invoke("/testController.htm");
                        TestCase.fail("expected NoSuchMethodException");
                    }
                    catch( BrutosException e ){
                        if( !(e.getCause() instanceof NoSuchMethodException) )
                            throw e;
                    }
                }

        });
    }

    public void testController11(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test11.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getScopes().get(ScopeType.PARAM).put("value", "100");
                    app.getInvoker().invoke("/testController.htm");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("/viewAction.jsp", view.getView());
                    TestCase.assertEquals(DispatcherType.REDIRECT, view.getDispatcherType());
                }

        });
    }

    public void testController12(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test12.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    try{
                        app.getInvoker().invoke("/testController.htm");
                        TestCase.fail("expected NoSuchMethodException");
                    }
                    catch( BrutosException e ){
                        if( !(e.getCause() instanceof NoSuchMethodException) )
                            throw e;
                    }
                }

        });
    }

    public void testController13(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test13.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getScopes().get(ScopeType.SESSION).put("value", "100");
                    app.getInvoker().invoke("/testController.htm");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("/viewAction.jsp", view.getView());
                    TestCase.assertEquals(DispatcherType.REDIRECT, view.getDispatcherType());
                }

        });
    }

    public void testController14(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test14.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getScopes().get(ScopeType.PARAM).put("value", "101");
                    try{
                        app.getInvoker().invoke("/testController.htm");
                        TestCase.fail("expected ValidatorException");
                    }
                    catch( ValidatorException e ){
                    }
                }

        });
    }

    public void testController15(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test15.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getInvoker().invoke("/testController.htm");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("/viewAction.jsp", view.getView());
                    TestCase.assertEquals(DispatcherType.REDIRECT, view.getDispatcherType());
                }

        });
    }

    public void testController16(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test16.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getScopes().get(ScopeType.PARAM).put("value", "1");
                    app.getScopes().get(ScopeType.PARAM).put("value", "6");
                    app.getScopes().get(ScopeType.PARAM).put("value", "111");
                    app.getInvoker().invoke("/testController.htm");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("/viewAction.jsp", view.getView());
                    TestCase.assertEquals(DispatcherType.REDIRECT, view.getDispatcherType());
                }

        });
    }

    public void testController17(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test17.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getInvoker().invoke("/testController.htm");
                    String result =
                            (String) app.getScopes()
                                .get(ScopeType.REQUEST).get("result");

                    TestCase.assertEquals("MSG", result);
                }

        });
    }

    public void testController18(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test18.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getInvoker().invoke("/testController.htm");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("/viewAction.jsp", view.getView());
                    TestCase.assertEquals(DispatcherType.REDIRECT, view.getDispatcherType());
                }

        });
    }

    public void testController19(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test19.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    try{
                        app.getInvoker().invoke("/testController.htm");
                        TestCase.fail("expected UnsupportedOperationException");
                    }
                    catch( UnsupportedOperationException e ){
                    }

                }

        });
    }

    public void testController20(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test20.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getInvoker().invoke("/testController.htm");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("/exception.jsp", view.getView());
                    TestCase.assertEquals(DispatcherType.REDIRECT, view.getDispatcherType());
                    TestCase.assertNotNull(app.getScopes().get(ScopeType.REQUEST).get("ex"));
                }

        });
    }

    public void testController21(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test21.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getInvoker().invoke("/testController.htm");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("/exceptionAction.jsp", view.getView());
                    TestCase.assertEquals(DispatcherType.INCLUDE, view.getDispatcherType());
                    TestCase.assertNotNull(app.getScopes().get(ScopeType.REQUEST).get("exx"));
                }

        });
    }

    public void testController22(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test22.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getInvoker().invoke("/testController.htm");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("/exceptionAction.jsp", view.getView());
                    TestCase.assertEquals(DispatcherType.INCLUDE, view.getDispatcherType());
                    TestCase.assertNotNull(app.getScopes().get(ScopeType.REQUEST).get("exx"));
                }

        });
    }

    public void testController23(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test23.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getInvoker().invoke("/testController.htm");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("/viewAction.jsp", view.getView());
                }

        });
    }

    public void testController24(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test24.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getInvoker().invoke("/testController.htm");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("/viewAction2.jsp", view.getView());
                }

        });
    }

    public void testController25(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test25.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getScopes().get(ScopeType.PARAM).put("invoke", "testAction");
                    app.getInvoker().invoke("/testController.htm");

                    MockViewProvider view = (MockViewProvider) app.getViewProvider();
                    TestCase.assertEquals("/viewAction2.jsp", view.getView());
                }

        });
    }

    public void testController26(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test26.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getInvoker().invoke("/test.htm");

                    TestCase.assertNotNull("OK",
                            app.getScopes().get(ScopeType.REQUEST).get("result"));
                }

        });
    }

    public void testController27(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/controller/controller-test27.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    app.getInvoker().invoke("/test.htm");
                }

        });
    }

}
