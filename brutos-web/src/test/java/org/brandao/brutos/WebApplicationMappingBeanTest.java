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
import org.brandao.brutos.validator.ValidatorException;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.GenericXMLWebApplicationContext;

/**
 *
 * @author Brandao
 */
public class WebApplicationMappingBeanTest extends AbstractTester implements Test{

    public ConfigurableWebApplicationContext getApplicationContext(String resourceName) {
        return new GenericXMLWebApplicationContext(
                new Resource[]{
                    new ClassPathResource( 
                            getClass(),
                            resourceName )});
    }
 

    public void testConstructorArgRefScopeTag(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller = 
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("valueRef", "teste");
                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();
                    
                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals("teste", instance.getArg());
                }
            
        });
    }

    public void testConstructorArgRefTagScopeDefType(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor2.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("valueRef", "teste");
                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals("teste", instance.getArg());
                }

        });
    }

    public void testConstructorArgInnerBean(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor3.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals("Texto", instance.getArg());
                }

        });
    }

    public void testConstructorArgDefTypeInnerBean(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor4.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals("Texto", instance.getArg());
                }

        });
    }

    public void testConstructorArgStaticValue(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor5.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals("1111", instance.getArg());
                }

        });
    }

    public void testConstructorArgStaticValueDefType(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor6.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals(111, instance.getArg2());
                }

        });
    }

    public void testConstructorArgEnumString(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor8-1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("enum", "VALUE2");
                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals(EnumTest.VALUE2, instance.getEnumTest());
                }

        });
    }

    public void testConstructorArgEnumNumber(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor8.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("enum", "1");
                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals(EnumTest.VALUE2, instance.getEnumTest());
                }

        });
    }

    public void testConstructorArgEnumStringWithoutType(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor9.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("enum", "1");
                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals("1", instance.getArg());
                }

        });
    }

    public void testConstructorArgEnumNumberWithoutType(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor9-1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("enum", "VALUE1");
                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals("VALUE1", instance.getArg());
                }

        });
    }

    public void testConstructor10(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor10.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals(100, instance.getArg2());
                }

        });
    }

    public void testConstructor11(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor11.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals("100", instance.getArg());
                }

        });
    }

    public void testConstructor12(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor12.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    try {
                        TestCase
                            .assertEquals(
                                (new SimpleDateFormat("dd/MM/yyyy")).parse("29/08/1984"),
                                instance.getDate());
                    } catch (ParseException ex) {
                        TestCase.fail();
                    }
                }

        });
    }

    public void testConstructor12_1(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor12-1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    try {
                        TestCase
                            .assertEquals(
                                (new SimpleDateFormat("dd/MM/yyyy")).parse("29/08/1984"),
                                instance.getCalendar().getTime());
                    } catch (ParseException ex) {
                        TestCase.fail();
                    }
                }

        });
    }

    public void testConstructor13(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor13.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertNotNull(instance.getDate());
                }

        });
    }

    public void testConstructor13_1(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor13-1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertNotNull(instance.getCalendar());
                }

        });
    }

    public void testConstructor14(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor14.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase
                        .assertNotNull(instance.getDate());
                }

        });
    }

    public void testConstructor15(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor15.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase
                        .assertNotNull(instance.getDate());
                }

        });
    }

    public void testConstructor16(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor16.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    app.getScopes().get(ScopeType.PARAM).put("data", "11");
                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase
                        .assertEquals(11,instance.getArg2());
                }

        });
    }

    public void testConstructor17(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor17.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    app.getScopes().get(ScopeType.PARAM).put("data", "Teste");
                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase
                        .assertEquals("Teste",instance.getArg());
                }

        });
    }

    public void testConstructor18(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor18.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    app.getScopes().get(ScopeType.SESSION).put("data", "Teste");
                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase
                        .assertEquals("Teste",instance.getArg());
                }

        });
    }

    public void testConstructor19(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor19.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase
                        .assertNotNull(instance.getBean());
                }

        });
    }

    public void testConstructor20(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor20.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase
                        .assertNotNull(instance.getBean());
                }

        });
    }

    public void testConstructor21(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor21.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    try{
                        Bean bean = controller.getMappingBean("bean");
                        bean.getValue();
                        TestCase.fail();
                    }
                    catch( ValidatorException e ){
                    }
                }

        });
    }

    public void testConstructor22(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor22.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals("Texto", instance.getArg());
                }

        });
    }

    public void testConstructor23(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor23.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("text", "123");
                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals("123", instance.getArg());
                }

        });
    }

    public void testConstructor24(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor24.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    try{
                        Bean bean = controller.getMappingBean("bean");
                        SimpleBean instance = (SimpleBean) bean.getValue();
                        TestCase.fail("expected NullPointerException");
                    }
                    catch( NullPointerException e ){
                    }
                }

        });
    }

    public void testProperty1(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("param", "123");
                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    TestCase.assertEquals("123", instance.getArg());
                }

        });
    }

    public void testProperty2(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property2.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    TestCase.assertEquals("Texto", instance.getArg());
                }

        });
    }

    public void testProperty3(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property3.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    TestCase.assertNull(instance.getBean());
                }

        });
    }

    public void testProperty4(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property4.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    TestCase.assertEquals("Texto",instance.getArg());
                }

        });
    }

    public void testProperty5(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property5.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("enum", "VALUE");
                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    TestCase.assertEquals(EnumTest.VALUE,instance.getEnumTest());
                }

        });
    }

    public void testProperty5_1(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property5-1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("enum", "0");
                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    TestCase.assertEquals(EnumTest.VALUE,instance.getEnumTest());
                }

        });
    }

    public void testProperty6(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property6.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    TestCase.assertEquals(EnumTest.VALUE2,instance.getEnumTest());
                }

        });
    }

    public void testProperty7(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property7.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    try {
                        TestCase.assertEquals((new SimpleDateFormat("dd/MM/yyyy")).parse("29/08/1984"), instance.getDate());
                    } catch (ParseException ex) {

                    }
                }

        });
    }

    public void testProperty7_1(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property7-1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    try {
                        TestCase.assertEquals(
                                (new SimpleDateFormat("dd/MM/yyyy")).parse("29/08/1984"),
                                instance.getCalendar().getTime());
                    } catch (ParseException ex) {

                    }
                }

        });
    }

    public void testProperty8(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property8.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    TestCase.assertEquals("Texto", instance.getArg());
                }

        });
    }

    public void testProperty9(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property9.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("bean2", "123");
                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    TestCase.assertEquals("123", instance.getArg());
                }

        });
    }

    public void testProperty10(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property10.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.SESSION).put("bean2", "123");
                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    TestCase.assertEquals("123", instance.getArg());
                }

        });
    }

    public void testProperty11(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property11.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    try{
                        Bean bean = controller.getMappingBean("bean");
                        SimpleBean instance = (SimpleBean) bean.getValue();
                        TestCase.fail("expected ValidatorException");
                    }
                    catch( ValidatorException e ){
                    }
                }

        });
    }

    public void testProperty12(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property12.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    TestCase.assertNotNull(instance.getBean());
                }

        });
    }

    public void testProperty13(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property13.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    TestCase.assertNotNull(instance.getBean());
                }

        });
    }

    public void testProperty14(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property14.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getMappingBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    try {
                        Date expected = (new SimpleDateFormat("dd/MM/yyyy")).parse("29/08/1984");
                        TestCase.assertEquals(expected, instance.getDate());
                    }
                    catch (ParseException ex) {
                    }
                }

        });
    }

    public void testCollection1(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-collection1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("arg[0]", "1");
                    app.getScopes().get(ScopeType.PARAM).put("arg[1]", "2");
                    app.getScopes().get(ScopeType.PARAM).put("arg[2]", "3");
                    Bean bean = controller.getMappingBean("bean");
                    List<SimpleBean> instance = (List<SimpleBean>) bean.getValue();
                    TestCase.assertEquals(3,instance.size());
                    TestCase.assertEquals(1,instance.get(0).getArg());
                    TestCase.assertEquals(2,instance.get(1).getArg());
                    TestCase.assertEquals(3,instance.get(2).getArg());
                }

        });
    }

    public void testCollection2(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-collection2.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    try{
                        Bean bean = controller.getMappingBean("bean");
                        List<SimpleBean> instance = (List<SimpleBean>) bean.getValue();
                        TestCase.fail("expected MappingException");
                    }
                    catch( MappingException e ){
                    }
                }

        });
    }

    public void testCollection3(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-collection3.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("arg[0]", "1");
                    app.getScopes().get(ScopeType.PARAM).put("arg[1]", "2");
                    app.getScopes().get(ScopeType.PARAM).put("arg[2]", "3");
                    Bean bean = controller.getMappingBean("bean");
                    List<SimpleBean> instance = (List<SimpleBean>) bean.getValue();
                    TestCase.assertEquals(3,instance.size());
                    TestCase.assertEquals(1,instance.get(0).getArg());
                    TestCase.assertEquals(2,instance.get(1).getArg());
                    TestCase.assertEquals(3,instance.get(2).getArg());
                }

        });
    }

    public void testCollection4(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-collection4.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("arg(0)", "1");
                    app.getScopes().get(ScopeType.PARAM).put("arg(1)", "2");
                    app.getScopes().get(ScopeType.PARAM).put("arg(2)", "3");
                    Bean bean = controller.getMappingBean("bean");
                    List<SimpleBean> instance = (List<SimpleBean>) bean.getValue();
                    TestCase.assertEquals(3,instance.size());
                    TestCase.assertEquals(1,instance.get(0).getArg());
                    TestCase.assertEquals(2,instance.get(1).getArg());
                    TestCase.assertEquals(3,instance.get(2).getArg());
                }

        });
    }

    public void testCollection5(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-collection5.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("arg[0]", "1");
                    app.getScopes().get(ScopeType.PARAM).put("arg[1]", "2");
                    app.getScopes().get(ScopeType.PARAM).put("arg[2]", "3");
                    Bean bean = controller.getMappingBean("bean");
                    Set<SimpleBean> instance = (Set<SimpleBean>) bean.getValue();
                    TestCase.assertEquals(3,instance.size());
                    SimpleBean[] arr = instance.toArray(new SimpleBean[]{});
                    TestCase.assertEquals(1,arr[0].getArg());
                    TestCase.assertEquals(2,arr[1].getArg());
                    TestCase.assertEquals(3,arr[2].getArg());
                }

        });
    }

    public void testCollection6(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-collection6.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    try{
                        Bean bean = controller.getMappingBean("bean");
                        Map instance = (Map) bean.getValue();
                        TestCase.fail("expected MappingException");
                    }
                    catch( MappingException e ){
                    }
                }

        });
    }

    public void testCollection7(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-collection7.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("arg[0]", "1");
                    app.getScopes().get(ScopeType.PARAM).put("arg[1]", "2");
                    app.getScopes().get(ScopeType.PARAM).put("arg[2]", "3");
                    Bean bean = controller.getMappingBean("bean");
                    Map<String,SimpleBean> instance = (Map<String,SimpleBean>) bean.getValue();
                    TestCase.assertEquals(3,instance.size());
                    TestCase.assertEquals(1,instance.get("0").getArg());
                    TestCase.assertEquals(2,instance.get("1").getArg());
                    TestCase.assertEquals(3,instance.get("2").getArg());
                }

        });
    }

}
