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

import java.util.List;
import java.util.Map;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.helper.controller.AbstractTester;
import org.brandao.brutos.helper.controller.SimpleBean;
import org.brandao.brutos.helper.controller.SimpleController;
import org.brandao.brutos.io.ClassPathResource;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.validator.ValidatorException;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.GenericXMLWebApplicationContext;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.WebApplicationContextWrapper;

/**
 *
 * @author Brandao
 */
public class WebApplicationMappingBeanTest extends AbstractTester implements Test{

    public ConfigurableWebApplicationContext getApplicationContext() {
        return new GenericXMLWebApplicationContext(
                new Resource[]{
                    new ClassPathResource( 
                            getClass(),
                            "org/brandao/brutos/helper/xml/test-bean.xml" )});
    }

    public void testInstance(){
        WebApplicationContextWrapper a =
                new WebApplicationContextWrapper(null);

        boolean b = a instanceof ConfigurableApplicationContext;
    }

    public void testSimpleMappingBean(){

        HandlerTest handler = new HandlerTest(){

            public void run(ConfigurableApplicationContext app) {
                Controller controller =
                        app.getControllerManager()
                            .getController(SimpleController.class);

                Bean bean = controller.getMappingBean("bean1");
                Object value = bean.getValue();

                TestCase.assertNotNull(value);
            }

        };

        super.execTest(handler);
    }

    public void testSimpleMappingBeanWithConstructorWithStaticArgs(){

        HandlerTest handler = new HandlerTest(){

            public void run(ConfigurableApplicationContext app) {

                Controller controller =
                        app.getControllerManager()
                            .getController(SimpleController.class);

                Bean bean = controller.getMappingBean("bean2");
                SimpleBean value = (SimpleBean) bean.getValue();

                TestCase.assertEquals("Value1", value.getArg());
                TestCase.assertEquals(100, value.getArg2());
                TestCase.assertNull(value.getBean());
            }

        };

        super.execTest(handler);
    }

    public void testSimpleMappingBeanWithConstructorWithRefArg(){

        HandlerTest handler = new HandlerTest(){

            public void run(ConfigurableApplicationContext app) {

                Controller controller =
                        app.getControllerManager()
                            .getController(SimpleController.class);

                Bean bean = controller.getMappingBean("bean3");
                SimpleBean value = (SimpleBean) bean.getValue();

                TestCase.assertEquals("Value1", value.getArg());
                TestCase.assertEquals(100, value.getArg2());
                TestCase.assertNotNull(value.getBean());
            }

        };

        super.execTest(handler);
    }

    public void testSimpleMappingBeanWithConstructorWithBeanArg(){

        HandlerTest handler = new HandlerTest(){

            public void run(ConfigurableApplicationContext app) {
                Controller controller =
                        app.getControllerManager()
                            .getController(SimpleController.class);

                Bean bean = controller.getMappingBean("bean4");
                SimpleBean value = (SimpleBean) bean.getValue();

                TestCase.assertEquals("Value1", value.getArg());
                TestCase.assertEquals(100, value.getArg2());
                TestCase.assertNotNull(value.getBean());
            }

        };

        super.execTest(handler);
    }

    public void testSimpleMappingBeanWithConstructorWithBeanAndRefArgs(){

        HandlerTest handler = new HandlerTest(){

            public void run(ConfigurableApplicationContext app) {
                Controller controller =
                        app.getControllerManager()
                            .getController(SimpleController.class);

                Bean bean = controller.getMappingBean("bean5");
                SimpleBean value = (SimpleBean) bean.getValue();

                TestCase.assertEquals("Value1", value.getArg());
                TestCase.assertEquals(100, value.getArg2());
                TestCase.assertNotNull(value.getBean());
            }

        };

        super.execTest(handler);
    }

    public void testMappingBeanWithConstructorWithValidator(){

        HandlerTest handler = new HandlerTest(){

            public void run(ConfigurableApplicationContext app) {
                Controller controller =
                        app.getControllerManager()
                            .getController(SimpleController.class);

                Bean bean = controller.getMappingBean("bean6");
                SimpleBean value = (SimpleBean) bean.getValue();

                TestCase.assertEquals("Value1", value.getArg());
                TestCase.assertEquals(100, value.getArg2());
                TestCase.assertNotNull(value.getBean());
            }

        };

        super.execTest(handler);
    }

    public void testMappingBeanWithConstructorWithValidatorError(){

        HandlerTest handler = new HandlerTest(){

            public void run(ConfigurableApplicationContext app) {
                Controller controller =
                        app.getControllerManager()
                            .getController(SimpleController.class);

                Bean bean = controller.getMappingBean("bean12");
                try{
                    SimpleBean value = (SimpleBean) bean.getValue();
                }
                catch( ValidatorException e ){
                    return;
                }
                TestCase.fail();

            }

        };

        super.execTest(handler);
    }

    public void testMappingBeanWithPropertyWithValidatorError(){

        HandlerTest handler = new HandlerTest(){

            public void run(ConfigurableApplicationContext app) {
                Controller controller =
                        app.getControllerManager()
                            .getController(SimpleController.class);

                Bean bean = controller.getMappingBean("bean7");
                try{
                    SimpleBean value = (SimpleBean) bean.getValue();
                }
                catch( ValidatorException e ){
                    return;
                }
                TestCase.fail();

            }

        };

        super.execTest(handler);
    }

    public void testMappingBeanFactory(){

        HandlerTest handler = new HandlerTest(){

            public void run(ConfigurableApplicationContext app) {
                Controller controller =
                        app.getControllerManager()
                            .getController(SimpleController.class);

                Bean bean = controller.getMappingBean("bean8");
                SimpleBean value = (SimpleBean) bean.getValue();

                TestCase.assertNotNull(value);
            }

        };

        super.execTest(handler);
    }

    public void testMappingBeanFactoryWithStaticMethod(){

        HandlerTest handler = new HandlerTest(){

            public void run(ConfigurableApplicationContext app) {
                Controller controller =
                        app.getControllerManager()
                            .getController(SimpleController.class);

                Bean bean = controller.getMappingBean("bean9");
                SimpleBean value = (SimpleBean) bean.getValue();

                TestCase.assertNotNull(value);
            }

        };

        super.execTest(handler);
    }

    public void testMappingBeanMap(){

        HandlerTest handler = new HandlerTest(){

            public void run(ConfigurableApplicationContext app) {
                Controller controller =
                        app.getControllerManager()
                            .getController(SimpleController.class);

                app.getScopes().get(ScopeType.REQUEST).put("id[0]", "10");
                app.getScopes().get(ScopeType.REQUEST).put("arg[0]", "25");
                app.getScopes().get(ScopeType.REQUEST).put("id[1]", "50");
                app.getScopes().get(ScopeType.REQUEST).put("arg[1]", "AAAAA");
                app.getScopes().get(ScopeType.REQUEST).put("id[2]", "65");
                app.getScopes().get(ScopeType.REQUEST).put("arg[2]", "BBBBB");
                
                Bean bean = controller.getMappingBean("bean10");
                Map<Integer,SimpleBean> value = (Map) bean.getValue();

                TestCase.assertNotNull(value);
                TestCase.assertNotNull(value.get(10));
                TestCase.assertEquals("25",value.get(10).getArg());
                TestCase.assertNotNull(value.get(50));
                TestCase.assertEquals("AAAAA",value.get(50).getArg());
                TestCase.assertNotNull(value.get(65));
                TestCase.assertEquals("BBBBB",value.get(65).getArg());
            }

        };

        super.execTest(handler);
    }

    public void testMappingBeanList(){

        HandlerTest handler = new HandlerTest(){

            public void run(ConfigurableApplicationContext app) {

                Controller controller =
                        app.getControllerManager()
                            .getController(SimpleController.class);

                app.getScopes().get(ScopeType.REQUEST).put("arg[0]", "25");
                app.getScopes().get(ScopeType.REQUEST).put("arg[1]", "AAAAA");
                app.getScopes().get(ScopeType.REQUEST).put("arg[2]", "BBBBB");
                Bean bean = controller.getMappingBean("bean11");
                List<SimpleBean> value = (List) bean.getValue();

                TestCase.assertNotNull(value);
                TestCase.assertEquals(3,value.size());
                TestCase.assertEquals("25",value.get(0).getArg());
                TestCase.assertEquals("AAAAA",value.get(1).getArg());
                TestCase.assertEquals("BBBBB",value.get(2).getArg());
            }

        };

        super.execTest(handler);
    }

}
