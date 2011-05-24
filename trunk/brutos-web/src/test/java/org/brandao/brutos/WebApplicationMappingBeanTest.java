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
import org.brandao.brutos.helper.controller.SimpleBean;
import org.brandao.brutos.helper.controller.SimpleController;
import org.brandao.brutos.io.ClassPathResource;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.Controller;
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

}
