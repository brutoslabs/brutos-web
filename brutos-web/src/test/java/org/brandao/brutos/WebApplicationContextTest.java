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

import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.helper.controller.AbstractTester;
import org.brandao.brutos.helper.controller.SimpleBean;
import org.brandao.brutos.helper.controller.TypeTest;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeManager;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.XMLWebApplicationContext;

/**
 *
 * @author Brandao
 */
public class WebApplicationContextTest extends AbstractTester implements Test{

    public Class getApplicationContext(String resourceName) {
        return XMLWebApplicationContext.class;
    }

    public void testContext1(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/context/context-test1.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Properties prop = app.getConfiguration();
                    TestCase.assertEquals("value", prop.getProperty("teste"));
                    TestCase.assertEquals("value2", prop.getProperty("teste2"));
                }
            
        });
    }

    public void testContext2(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/context/context-test2.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Type type = TypeManager.getType(SimpleBean.class);
                    TestCase.assertTrue(type instanceof TypeTest);
                }

        });
    }

    public void testContext3(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/context/context-test3.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                    Type type = TypeManager.getType(SimpleBean.class);
                    TestCase.assertTrue(type instanceof TypeTest);
                }

        });
    }

    public void testContext4(){
        super.execTest(
            new HandlerTest(){

                public String getResourceName() {
                    return
                        "org/brandao/brutos/xml/helper/context/context-test4.xml";
                }

                public void run(ConfigurableApplicationContext app,
                        HttpServletRequest request, HttpServletResponse response) {

                }

        });
    }

}
