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
import org.brandao.brutos.annotation.helper.constructor.app1.ConstructorBean1;
import org.brandao.brutos.annotation.helper.constructor.app1.ConstructorBean2;
import org.brandao.brutos.annotation.helper.constructor.app1.ConstructorBean3;
import org.brandao.brutos.annotation.helper.constructor.app1.Contructor1TestController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

/**
 *
 * @author Brandao
 */
public class ConstructorTest  extends TestCase{
    
    public void test1() throws Throwable{
        WebApplicationContextTester.run(
            "/Contructor1Test/test1", 
            new WebApplicationTester() {

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
                    
                    assertNotNull(applicationContext.getControllerManager().getController("/Contructor1Test"));
                    ConstructorBean1 bean = 
                            (ConstructorBean1)request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME);
                    
                    assertNotNull(bean);
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Contructor1TestController.class});
    }

    public void test2() throws Throwable{
        WebApplicationContextTester.run(
            "/Contructor1Test/test2", 
            new WebApplicationTester() {

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
                    parameters.put("constructorBean2.arg0", "action");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    ConstructorBean2 bean = 
                            (ConstructorBean2)request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME);
                    
                    assertNotNull(bean);
                    assertEquals("action", bean.getProperty());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Contructor1TestController.class});
    }

    public void test3() throws Throwable{
        WebApplicationContextTester.run(
            "/Contructor1Test/test3", 
            new WebApplicationTester() {

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
                    parameters.put("constructorBean3.arg0", "action");
                    parameters.put("constructorBean3.arg1", "action2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    
                    ConstructorBean3 bean = 
                            (ConstructorBean3)request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME);
                    
                    assertNotNull(bean);
                    assertEquals("action", bean.getProperty());
                    assertEquals("action2", bean.getProperty2());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Contructor1TestController.class});
    }
    
}
