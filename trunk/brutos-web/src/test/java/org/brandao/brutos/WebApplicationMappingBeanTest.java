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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.helper.controller.AbstractTester;
import org.brandao.brutos.helper.controller.BeanTestConstructor9;
import org.brandao.brutos.helper.controller.EnumTest;
import org.brandao.brutos.helper.controller.SimpleBean;
import org.brandao.brutos.helper.controller.SimpleController;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.validator.ValidatorException;
import org.brandao.brutos.web.WebScopeType;
import org.brandao.brutos.web.XMLWebApplicationContext;

/**
 *
 * @author Brandao
 */
public class WebApplicationMappingBeanTest extends AbstractTester implements Test{

    public WebApplicationMappingBeanTest(){
        super.actionType = ActionType.DETACHED;
    }
    
    public Class<?> getApplicationContext(String resourceName) {
        return XMLWebApplicationContext.class;
    }
 

    public void testCollection8(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-collection8.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(WebScopeType.PARAM).put("element(0).arg", "1");
                    app.getScopes().get(WebScopeType.PARAM).put("element(1).arg", "2");
                    app.getScopes().get(WebScopeType.PARAM).put("element(2).arg", "3");
                    Bean bean = controller.getBean("bean");
                    List<SimpleBean> instance = (List<SimpleBean>) bean.getValue();
                    TestCase.assertEquals(3,instance.size());
                    TestCase.assertEquals(1,instance.get(0).getArg2());
                    TestCase.assertEquals(2,instance.get(1).getArg2());
                    TestCase.assertEquals(3,instance.get(2).getArg2());
                }

        });
    }

    public void testCollection9(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-collection9.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(WebScopeType.PARAM).put("element(0).arg", "1");
                    app.getScopes().get(WebScopeType.PARAM).put("element(1).arg", "2");
                    app.getScopes().get(WebScopeType.PARAM).put("element(2).arg", "3");
                    Bean bean = controller.getBean("bean");
                    List<SimpleBean> instance = (List<SimpleBean>) bean.getValue();
                    TestCase.assertEquals(3,instance.size());
                    TestCase.assertEquals(1,instance.get(0).getArg2());
                    TestCase.assertEquals(2,instance.get(1).getArg2());
                    TestCase.assertEquals(3,instance.get(2).getArg2());
                }

        });
    }

    public void testCollection10(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-collection10.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(WebScopeType.PARAM).put("element(0).arg", "1");
                    app.getScopes().get(WebScopeType.PARAM).put("element(1).arg", "2");
                    app.getScopes().get(WebScopeType.PARAM).put("element(2).arg", "3");
                    Bean bean = controller.getBean("bean");

                    try{
                        bean.getValue();
                        TestCase.fail("excepted ValidatorException");
                    }
                    catch( ValidatorException e ){
                    }
                }

        });
    }

    public void testCollection11(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-collection11.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(WebScopeType.PARAM).put("map.key[0].key", "VALUE");
                    app.getScopes().get(WebScopeType.PARAM).put("map.key[1].key", "VALUE2");
                    app.getScopes().get(WebScopeType.PARAM).put("map.element[1].value", "valor2");
                    app.getScopes().get(WebScopeType.PARAM).put("map.element[0].value", "valor1");
                    Bean bean = controller.getBean("bean");

                    SimpleBean instance = (SimpleBean) bean.getValue();
                    Map<EnumTest,String> map = instance.getMap();
                    TestCase.assertEquals(2, map.size());
                    TestCase.assertEquals("valor1", map.get(EnumTest.VALUE));
                    TestCase.assertEquals("valor2", map.get(EnumTest.VALUE2));
                }

        });
    }

    public void testCollection12(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-collection12.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(WebScopeType.PARAM).put("chave[0]", "VALUE");
                    app.getScopes().get(WebScopeType.PARAM).put("elemento[0]", "valor1");
                    app.getScopes().get(WebScopeType.PARAM).put("chave[1]", "VALUE2");
                    app.getScopes().get(WebScopeType.PARAM).put("elemento[1]", "valor2");
                    Bean bean = controller.getBean("bean");

                    Map<EnumTest,String> map = (Map<EnumTest,String>)bean.getValue();
                    TestCase.assertEquals(2, map.size());
                    TestCase.assertEquals("valor1", map.get(EnumTest.VALUE));
                    TestCase.assertEquals("valor2", map.get(EnumTest.VALUE2));
                }

        });
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

                    app.getScopes().get(WebScopeType.PARAM).put("valueRef", "teste");
                    Bean bean = controller.getBean("bean");

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

                    app.getScopes().get(WebScopeType.PARAM).put("valueRef", "teste");
                    Bean bean = controller.getBean("bean");

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

                    Bean bean = controller.getBean("bean");

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

                    Bean bean = controller.getBean("bean");

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

                    Bean bean = controller.getBean("bean");

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

                    Bean bean = controller.getBean("bean");

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

                    app.getScopes().get(WebScopeType.PARAM).put("enum", "VALUE2");
                    Bean bean = controller.getBean("bean");

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

                    app.getScopes().get(WebScopeType.PARAM).put("enum", "1");
                    Bean bean = controller.getBean("bean");

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
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor9-1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(WebScopeType.PARAM).put("enum", "VALUE");
                    Bean bean = controller.getBean("bean");

                    BeanTestConstructor9 instance = (BeanTestConstructor9) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals(EnumTest.VALUE, instance.getValue());
                }

        });
    }

    public void testConstructorArgEnumNumberWithoutType(){
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

                    app.getScopes().get(WebScopeType.PARAM).put("enum", "1");
                    Bean bean = controller.getBean("bean");

                    BeanTestConstructor9 instance = (BeanTestConstructor9) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase.assertEquals(EnumTest.VALUE2, instance.getValue());
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

                    Bean bean = controller.getBean("bean");

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

                    Bean bean = controller.getBean("bean");

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

                    Bean bean = controller.getBean("bean");

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

                    Bean bean = controller.getBean("bean");

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

                    Bean bean = controller.getBean("bean");

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

                    Bean bean = controller.getBean("bean");

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

                    Bean bean = controller.getBean("bean");

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

                    Bean bean = controller.getBean("bean");

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

                    Bean bean = controller.getBean("bean");

                    app.getScopes().get(WebScopeType.PARAM).put("data", "11");
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

                    Bean bean = controller.getBean("bean");

                    app.getScopes().get(WebScopeType.PARAM).put("data", "Teste");
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

                    Bean bean = controller.getBean("bean");

                    app.getScopes().get(WebScopeType.SESSION).put("data", "Teste");
                    SimpleBean instance = (SimpleBean) bean.getValue();

                    TestCase.assertNotNull(instance);
                    TestCase
                        .assertEquals("Teste",instance.getArg());
                }

        });
    }

    public void testConstructor19(){
        try{
            super.execTest(
                new HandlerTest(){

                    public String getResourceName() {
                        return
                            "org/brandao/brutos/xml/helper/bean/bean-test-constructor19.xml";
                    }

                    public void run(ConfigurableApplicationContext app,
                            HttpServletRequest request, HttpServletResponse response) {
                        /*
                        Controller controller =
                                app.getControllerManager()
                                    .getController(SimpleController.class);

                        Bean bean = controller.getBean("bean");

                        SimpleBean instance = (SimpleBean) bean.getValue();

                        TestCase.assertNotNull(instance);
                        TestCase
                            .assertNotNull(instance.getBean());
                        */
                    }

            });
            TestCase.fail("expected IllegalArgumentException");
        }
        catch(BrutosException e){
            if(!e.getMessage().matches("expected EnumTest found SimpleBean")){
            	e.printStackTrace();
                TestCase.fail();
            }
        }
        
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

                    Bean bean = controller.getBean("bean");

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
                        Bean bean = controller.getBean("bean");
                        bean.getValue(true);
                        TestCase.fail("expected ValidatorException");
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

                    Bean bean = controller.getBean("bean");
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

                    app.getScopes().get(WebScopeType.PARAM).put("text", "123");
                    Bean bean = controller.getBean("bean");
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
                        Bean bean = controller.getBean("bean");
                        SimpleBean instance = (SimpleBean) bean.getValue();
                        TestCase.fail("expected NullPointerException");
                    }
                    catch( BrutosException e ){
                        if( !(e.getCause() instanceof IllegalArgumentException)){
                        	e.printStackTrace();
                            TestCase.fail("expected NullPointerException");
                        }
                    }
                }

        });
    }

    public void testConstructor25(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor25.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getBean("bean");
                    TestCase.assertNull(bean.getValue());
                }

        });
    }

    public void testConstructor26(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor26.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("arg0.bean_arg", "teste");
                    Bean bean = controller.getBean("bean");
                    
                    SimpleBean obj = (SimpleBean) bean.getValue();
                    TestCase.assertEquals("teste",obj.getBean().getArg());
                }

        });
    }

    public void testConstructor27(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor27.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("bean.arg0.bean_arg", "teste");
                    Bean bean = controller.getBean("bean");
                    
                    SimpleBean obj = (SimpleBean) bean.getValue();
                    TestCase.assertEquals("teste",obj.getBean().getBean().getArg());
                }

        });
    }

    public void testConstructor28(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-constructor28.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("bean.arg0.bean_arg", "teste");
                    Bean bean = controller.getBean("bean");
                    
                    SimpleBean obj = (SimpleBean) bean.getValue();
                    TestCase.assertEquals("teste",obj.getBean().getBean().getArg());
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

                    app.getScopes().get(WebScopeType.PARAM).put("param", "123");
                    Bean bean = controller.getBean("bean");
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

                    Bean bean = controller.getBean("bean");
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

                    Bean bean = controller.getBean("bean");
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

                    Bean bean = controller.getBean("bean");
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

                    app.getScopes().get(WebScopeType.PARAM).put("enum", "VALUE");
                    Bean bean = controller.getBean("bean");
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

                    app.getScopes().get(WebScopeType.PARAM).put("enum", "0");
                    Bean bean = controller.getBean("bean");
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

                    Bean bean = controller.getBean("bean");
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

                    Bean bean = controller.getBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    try {
                        TestCase.assertEquals((new SimpleDateFormat("dd/MM/yyyy")).parse("29/08/2011"), instance.getDate());
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

                    Bean bean = controller.getBean("bean");
                    SimpleBean instance = (SimpleBean) bean.getValue();
                    try {
                        TestCase.assertEquals(
                                (new SimpleDateFormat("dd/MM/yyyy")).parse("29/08/2011"),
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

                    Bean bean = controller.getBean("bean");
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

                    app.getScopes().get(WebScopeType.PARAM).put("bean2", "123");
                    Bean bean = controller.getBean("bean");
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

                    app.getScopes().get(WebScopeType.SESSION).put("bean2", "123");
                    Bean bean = controller.getBean("bean");
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
                        Bean bean = controller.getBean("bean");
                        bean.getValue(true);
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

                    Bean bean = controller.getBean("bean");
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

                    Bean bean = controller.getBean("bean");
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

                    Bean bean = controller.getBean("bean");
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

    public void testProperty15(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property15.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    try {
                        Bean bean = controller.getBean("bean");
                        bean.getValue();
                        TestCase.fail("expected BrutosException");
                    }
                    catch (BrutosException ex) {
                    }
                }

        });
    }

    public void testProperty16(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property16.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    Bean bean = controller.getBean("bean");
                    TestCase.assertNull(bean.getValue());
                }

        });
    }

    public void testProperty17(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property17.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("bean.bean_arg", "teste");
                    Bean bean = controller.getBean("bean");
                    
                    SimpleBean obj = (SimpleBean) bean.getValue();
                    TestCase.assertEquals("teste",obj.getBean().getArg());
                }

        });
    }

    public void testProperty18(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property18.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("bean_property.bean_arg", "teste");
                    Bean bean = controller.getBean("bean");
                    
                    SimpleBean obj = (SimpleBean) bean.getValue();
                    TestCase.assertEquals("teste",obj.getBean().getArg());
                }

        });
    }

    public void testProperty19(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/bean/bean-test-property19.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Controller controller =
                            app.getControllerManager()
                                .getController(SimpleController.class);

                    app.getScopes().get(ScopeType.PARAM).put("arg0.arg", "teste");
                    app.getScopes().get(ScopeType.PARAM).put("bean_property.arg2", "100");
                    Bean bean = controller.getBean("bean");
                    
                    SimpleBean obj = (SimpleBean) bean.getValue();
                    TestCase.assertEquals("teste",obj.getBean().getArg());
                    TestCase.assertEquals(100,obj.getBean().getArg2());
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

                    app.getScopes().get(WebScopeType.PARAM).put("element[0].arg", "1");
                    app.getScopes().get(WebScopeType.PARAM).put("element[1].arg", "2");
                    app.getScopes().get(WebScopeType.PARAM).put("element[2].arg", "3");
                    Bean bean = controller.getBean("bean");
                    List<SimpleBean> instance = (List<SimpleBean>) bean.getValue();
                    TestCase.assertEquals(3,instance.size());
                    TestCase.assertEquals("1",instance.get(0).getArg());
                    TestCase.assertEquals("2",instance.get(1).getArg());
                    TestCase.assertEquals("3",instance.get(2).getArg());
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
                        Bean bean = controller.getBean("bean");
                        bean.getValue();
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

                    app.getScopes().get(WebScopeType.PARAM).put("element[0].arg", "1");
                    app.getScopes().get(WebScopeType.PARAM).put("element[1].arg", "2");
                    app.getScopes().get(WebScopeType.PARAM).put("element[2].arg", "3");
                    Bean bean = controller.getBean("bean");
                    List<SimpleBean> instance = (List<SimpleBean>) bean.getValue();
                    TestCase.assertEquals(3,instance.size());
                    TestCase.assertEquals("1",instance.get(0).getArg());
                    TestCase.assertEquals("2",instance.get(1).getArg());
                    TestCase.assertEquals("3",instance.get(2).getArg());
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

                    app.getScopes().get(WebScopeType.PARAM).put("element(0).arg", "1");
                    app.getScopes().get(WebScopeType.PARAM).put("element(1).arg", "2");
                    app.getScopes().get(WebScopeType.PARAM).put("element(2).arg", "3");
                    Bean bean = controller.getBean("bean");
                    List<SimpleBean> instance = (List<SimpleBean>) bean.getValue();
                    TestCase.assertEquals(3,instance.size());
                    TestCase.assertEquals("1",instance.get(0).getArg());
                    TestCase.assertEquals("2",instance.get(1).getArg());
                    TestCase.assertEquals("3",instance.get(2).getArg());
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

                    app.getScopes().get(WebScopeType.PARAM).put("element[0].arg", "1");
                    app.getScopes().get(WebScopeType.PARAM).put("element[1].arg", "2");
                    app.getScopes().get(WebScopeType.PARAM).put("element[2].arg", "3");
                    Bean bean = controller.getBean("bean");
                    Set<SimpleBean> instance = (Set<SimpleBean>) bean.getValue();
                    TestCase.assertEquals(3,instance.size());
                    boolean id1 = false;
                    boolean id2 = false;
                    boolean id3 = false;
                    for( SimpleBean b: instance ){
                        if( b.getArg().equals("1") )
                            id1 = true;
                        if( b.getArg().equals("2") )
                            id2 = true;
                        if( b.getArg().equals("3") )
                            id3 = true;
                    }
                    TestCase.assertTrue(id1 && id2 && id3);
                }

        });
    }

    public void testCollection6(){
        try{
            super.execTest(
                new HandlerTest(){

                    public String getResourceName() {
                        return
                            "org/brandao/brutos/xml/helper/bean/bean-test-collection6.xml";
                    }

                    public void run(ConfigurableApplicationContext app,
                            HttpServletRequest request, HttpServletResponse response) {
                    }

            });
            TestCase.fail("expected BrutosException");
        }
        catch( BrutosException e ){
        }
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

                    app.getScopes().get(WebScopeType.PARAM).put("item[0].arg", "1");
                    app.getScopes().get(WebScopeType.PARAM).put("item[1].arg", "2");
                    app.getScopes().get(WebScopeType.PARAM).put("item[2].arg", "3");
                    Bean bean = controller.getBean("bean");
                    Map<String,SimpleBean> instance = (Map<String,SimpleBean>) bean.getValue();
                    TestCase.assertEquals(3,instance.size());
                    TestCase.assertEquals("1",instance.get("1").getArg());
                    TestCase.assertEquals("2",instance.get("2").getArg());
                    TestCase.assertEquals("3",instance.get("3").getArg());
                }

        });
    }


}
