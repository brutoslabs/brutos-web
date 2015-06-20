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

package org.brandao.brutos.annotation;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.annotation.helper.actionstrategy.app1.ActionStrategyTest1Controller;
import org.brandao.brutos.annotation.helper.actionstrategy.app1.ActionStrategyTest2Controller;
import org.brandao.brutos.annotation.helper.actionstrategy.app1.ActionStrategyTest3Controller;
import org.brandao.brutos.annotation.helper.actionstrategy.app1.ActionStrategyTest4Controller;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

/**
 *
 * @author Brandao
 */
public class ActionStrategyTypeTest extends TestCase {
    
    public void test1() throws Throwable{
        WebApplicationContextTester.run(
            "/ActionStrategyTest1/teste", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.assertEquals("action", request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                }

                public void checkException(Throwable e) {
                    Assert.fail();
                }
            },
            new Class[]{ActionStrategyTest1Controller.class});
    }

    public void test2() throws Throwable{
        WebApplicationContextTester.run(
            "/ActionStrategyTest2", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                    parameters.put("invoke", "teste");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.assertEquals("action", request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                }

                public void checkException(Throwable e) {
                    Assert.fail();
                }
            },
            new Class[]{ActionStrategyTest2Controller.class});
    }

    public void test3() throws Throwable{
        WebApplicationContextTester.run(
            "/ActionStrategyTest3/teste", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.assertEquals("action", request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                }

                public void checkException(Throwable e) {
                    Assert.fail();
                }
            },
            new Class[]{ActionStrategyTest3Controller.class});
    }
    
    public void test4() throws Throwable{
        WebApplicationContextTester.run(
            "/teste", 
            new WebApplicationTester(){

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockAnnotationWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                            "true"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.assertEquals("action", request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME));
                }

                public void checkException(Throwable e) {
                    Assert.fail();
                }
            },
            new Class[]{ActionStrategyTest4Controller.class});
    }    
    
}
