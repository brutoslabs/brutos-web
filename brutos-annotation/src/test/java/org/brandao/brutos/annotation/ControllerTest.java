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
import org.brandao.brutos.annotation.helper.action.fail.Fail2TestController;
import org.brandao.brutos.annotation.helper.controller.app1.Controller10Test;
import org.brandao.brutos.annotation.helper.controller.app1.Controller11Test;
import org.brandao.brutos.annotation.helper.controller.app1.Controller1TestController;
import org.brandao.brutos.annotation.helper.controller.app1.Controller2TestController;
import org.brandao.brutos.annotation.helper.controller.app1.Controller3TestController;
import org.brandao.brutos.annotation.helper.controller.app1.Controller4Test;
import org.brandao.brutos.annotation.helper.controller.app1.Controller5Test;
import org.brandao.brutos.annotation.helper.controller.app1.Controller6Test;
import org.brandao.brutos.annotation.helper.controller.app1.Controller7Test;
import org.brandao.brutos.annotation.helper.controller.app1.Controller8Test;
import org.brandao.brutos.annotation.helper.controller.app1.Controller9Test;
import org.brandao.brutos.annotation.helper.controller.fail.Fail1TestController;
import org.brandao.brutos.annotation.helper.controller.fail.Fail2Test;
import org.brandao.brutos.annotation.helper.controller.fail.Fail3Test;
import org.brandao.brutos.annotation.helper.controller.fail.Fail4Test;
import org.brandao.brutos.annotation.helper.controller.fail.Fail5Test;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.test.MockRenderView;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

/**
 *
 * @author Brandao
 */
public class ControllerTest  extends TestCase{
    
    public void test1() throws Throwable{
        WebApplicationContextTester.run(
            "/Controller1Test", 
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
                    
                    MockRenderView render = (MockRenderView) applicationContext.getRenderView();
                    Assert.assertEquals("/WEB-INF/controller1test/index.jsp", render.getView());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Controller1TestController.class});
    }

    public void test2() throws Throwable{
        WebApplicationContextTester.run(
            "/Controller2Test", 
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
                    
                    MockRenderView render = (MockRenderView) applicationContext.getRenderView();
                    Assert.assertEquals("/WEB-INF/controller2test/index.jsp", render.getView());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Controller2TestController.class});
    }

    public void test3() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/a/b", 
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
                    
                    String[] result = (String[])request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME);
                    Assert.assertEquals("a", result[0]);
                    Assert.assertEquals("b", result[1]);
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Controller3TestController.class});
    }

    public void test4() throws Throwable{
        WebApplicationContextTester.run(
            "/Controller4Test", 
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
                    
                    MockRenderView render = (MockRenderView) applicationContext.getRenderView();
                    Assert.assertEquals("/WEB-INF/controller4test/index.jsp", render.getView());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Controller4Test.class});
    }

    public void test5() throws Throwable{
        WebApplicationContextTester.run(
            "/myController", 
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
                    
                    MockRenderView render = (MockRenderView) applicationContext.getRenderView();
                    Assert.assertEquals("/WEB-INF/controller5test/index.jsp", render.getView());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Controller5Test.class});
    }

    public void test6() throws Throwable{
        WebApplicationContextTester.run(
            "/mycontroller", 
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
                    
                    MockRenderView render = (MockRenderView) applicationContext.getRenderView();
                    Assert.assertEquals("/WEB-INF/controller6test/index.jsp", render.getView());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Controller6Test.class});
    }

    public void test7() throws Throwable{
        WebApplicationContextTester.run(
            "/mycontroller2", 
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
                    
                    MockRenderView render = (MockRenderView) applicationContext.getRenderView();
                    Assert.assertEquals("/WEB-INF/controller6test/index.jsp", render.getView());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Controller6Test.class});
    }

    public void test8() throws Throwable{
        WebApplicationContextTester.run(
            "/myController/teste1", 
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
                    
                    String result = (String)request.getAttribute(BrutosConstants.DEFAULT_RETURN_NAME);
                    
                    Assert.assertEquals("action", result);
                    MockRenderView render = (MockRenderView) applicationContext.getRenderView();
                    Assert.assertEquals("/WEB-INF/controller7test/teste1action/index.jsp", render.getView());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Controller7Test.class});
    }

    public void test9() throws Throwable{
        WebApplicationContextTester.run(
            "/myController/action", 
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
                    
                    Controller8Test controller = (Controller8Test)request.getAttribute("Controller");
                    
                    Assert.assertEquals("action", controller.getProperty());
                    MockRenderView render = (MockRenderView) applicationContext.getRenderView();
                    Assert.assertEquals("/WEB-INF/controller8test/index.jsp", render.getView());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Controller8Test.class});
    }

    public void test10() throws Throwable{
        WebApplicationContextTester.run(
            "/Controller9Test", 
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
                    
                    MockRenderView render = (MockRenderView) applicationContext.getRenderView();
                    Assert.assertEquals("/WEB-INF/controller9test/teste1action/index.jsp", render.getView());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Controller9Test.class});
    }

    public void test11() throws Throwable{
        WebApplicationContextTester.run(
            "/Controller10Test", 
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
                    
                    MockRenderView render = (MockRenderView) applicationContext.getRenderView();
                    Assert.assertEquals("/page.jsp", render.getView());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Controller10Test.class});
    }

    public void test12() throws Throwable{
        WebApplicationContextTester.run(
            "/Controller10Test", 
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
                    
                    org.brandao.brutos.mapping.Controller controller = 
                            applicationContext.getControllerManager().getController(Controller11Test.class);
                    
                    Assert.assertEquals("controllerName", controller.getName());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Controller11Test.class});
    }

    public void test13() throws Throwable{
        WebApplicationContextTester.run(
            "", 
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
                    Assert.fail();
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("expected starts with \"/\": \"controller\""))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {expected starts with \"/\": \"controller\"}");
                }
            },
            new Class[]{Fail1TestController.class});
    }

    public void test14() throws Throwable{
        WebApplicationContextTester.run(
            "", 
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
                    Assert.fail();
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("expected starts with \"/\": \"controller\""))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {expected starts with \"/\": \"controller\"}");
                }
            },
            new Class[]{Fail2Test.class});
    }

    public void test15() throws Throwable{
        WebApplicationContextTester.run(
            "", 
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
                    Assert.fail();
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("duplicate controller: \"/controller\""))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {duplicate controller: \"/controller\"}");
                }
            },
            new Class[]{Fail3Test.class});
    }

    public void test16() throws Throwable{
        WebApplicationContextTester.run(
            "", 
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
                    Assert.fail("expected: {invalid action id: \"a.ction\"}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("invalid action id: \"a.ction\""))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {invalid action id: \"a.ction\"}");
                }
            },
            new Class[]{Fail4Test.class});
    }

    public void test17() throws Throwable{
        WebApplicationContextTester.run(
            "", 
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
                    Assert.fail();
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("action not found: \"/teste1\""))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {action not found: \"/teste1\"}");
                }
            },
            new Class[]{Fail5Test.class});
    }
    
}
