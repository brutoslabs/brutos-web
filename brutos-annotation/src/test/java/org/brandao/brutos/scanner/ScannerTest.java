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

package org.brandao.brutos.scanner;

import junit.framework.Assert;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.AbstractWebAnnotationApplicationContextTest;
import org.brandao.brutos.helper.TestController;
import org.brandao.brutos.helper.controller.Test2Controller;

/**
 *
 * @author Brandao
 */
public class ScannerTest extends AbstractWebAnnotationApplicationContextTest{
    
    public void testApp1(){
        String content ="";
        content +="<ns1:component-scan base-package=\"org.brandao.brutos.helper\">";
        content +="        <ns1:exclude-filter type=\"regex\" expression=\".*InterceptorController$\"/>";
        content +="        <ns1:exclude-filter type=\"annotation\" expression=\"org.brandao.brutos.annotation.Intercepts\"/>";
        content +="</ns1:component-scan>";
        ConfigurableApplicationContext applicationContext = 
                super.getApplication(content);
        
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test2(){
        String content ="<ns1:component-scan base-package=\"org.brandao.brutos.helper.controller\"/>";
        ConfigurableApplicationContext applicationContext = 
                super.getApplication(content);
        
        Assert.assertNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test3(){
        String content ="<ns1:component-scan base-package=\"org.brandao.brutos.helper.controller\"/>";
        ConfigurableApplicationContext applicationContext = 
                super.getApplication(content);
        
        Assert.assertNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test4(){
        String content = "";
        content +="<ns1:component-scan use-default-filters=\"false\">";
        content +="        <ns1:include-filter type=\"regex\" expression=\""+TestController.class.getName().replace(".","\\.")+"\"/>";
        content +="        <ns1:include-filter type=\"regex\" expression=\""+Test2Controller.class.getName().replace(".","\\.")+"\"/>";
        content +="</ns1:component-scan>";
        ConfigurableApplicationContext applicationContext = 
                super.getApplication(content);
        
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test5(){
        String content ="<ns1:component-scan base-package=\"org.brandao.brutos.helper\" use-default-filters=\"false\"/>";
        ConfigurableApplicationContext applicationContext = 
                super.getApplication(content);
        
        Assert.assertNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test6(){
        String content = "";
        content +="<ns1:component-scan base-package=\"org.brandao.brutos.helper\" use-default-filters=\"false\">";
        content +="        <ns1:include-filter type=\"regex\" expression=\".*2Controller$\"/>";
        content +="</ns1:component-scan>";
        ConfigurableApplicationContext applicationContext = 
                super.getApplication(content);
        
        Assert.assertNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test7(){
        String content = "";
        content +="<ns1:component-scan base-package=\"org.brandao.brutos.helper\">";
        content +="        <ns1:exclude-filter type=\"regex\" expression=\".*2Controller$\"/>";
        content +="</ns1:component-scan>";
        ConfigurableApplicationContext applicationContext = 
                super.getApplication(content);
        
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test8(){
        String content = "";
        content +="<ns1:component-scan base-package=\"org.brandao.brutos.helper\" use-default-filters=\"false\">";
        content +="        <ns1:exclude-filter type=\"regex\" expression=\".*Test2.*\"/>";
        content +="        <ns1:include-filter type=\"regex\" expression=\".*Controller$\"/>";
        content +="</ns1:component-scan>";
        ConfigurableApplicationContext applicationContext = 
                super.getApplication(content);
        
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }

    public void test9(){
        String content = "";
        content +="<ns1:component-scan base-package=\"org.brandao.brutos.helper\" use-default-filters=\"false\">";
        content +="        <ns1:include-filter type=\"assignable\" expression=\"java.io.Serializable\"/>";
        content +="</ns1:component-scan>";
        ConfigurableApplicationContext applicationContext = 
                super.getApplication(content);
        
        Assert.assertNotNull(
            applicationContext.getControllerManager().getController(TestController.class));
        Assert.assertNull(
            applicationContext.getControllerManager().getController(Test2Controller.class));
    }
    
}
