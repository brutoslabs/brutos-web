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

import junit.framework.Assert;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.helper.componentscan.app1.App1TestController;
import org.brandao.brutos.annotation.helper.componentscan.app2.App2TestController;
import org.brandao.brutos.annotation.helper.componentscan.app3.App3TestController;
import org.brandao.brutos.annotation.helper.componentscan.app4.App4TestController;
import org.brandao.brutos.annotation.helper.componentscan.app5.App5TestController;
import org.brandao.brutos.annotation.helper.componentscan.app6.App6TestController;
import org.brandao.brutos.annotation.helper.componentscan.app7.App7TestController;

/**
 *
 * @author Brandao
 */
public class ComponentScanTest 
    extends AbstractWebAnnotationApplicationContextTest{
    
    public void test(){
        ConfigurableApplicationContext context = this.getApplication((String)null);
        
        Assert.assertNotNull(
            context.getControllerManager().getController(App1TestController.class));
    }

    public void testApp1(){
        String content =
        "<ns1:component-scan use-default-filters=\"false\" \n"
        + "base-package=\"org.brandao.brutos.annotation.helper.componentscan.app1\"> \n"
        + "<ns1:include-filter type=\"annotation\" expression=\"org.brandao.brutos.annotation.Configuration\"/> \n"
        + "</ns1:component-scan>";
        
        ConfigurableApplicationContext context = this.getApplication(content);
        
        Assert.assertNotNull(
            context.getControllerManager().getController(App1TestController.class));
        
    }

    public void testApp2(){
        String content =
        "<ns1:component-scan use-default-filters=\"false\" "
        + "base-package=\"org.brandao.brutos.annotation.helper.componentscan.app2\"/>";
        
        ConfigurableApplicationContext context = this.getApplication(content);
        
        Assert.assertNull(
            context.getControllerManager().getController(App2TestController.class));
        
    }

    public void testApp3(){
        String content =
        "<ns1:component-scan use-default-filters=\"false\" "
        + "base-package=\"org.brandao.brutos.annotation.helper.componentscan.app3\"/>";
        
        ConfigurableApplicationContext context = this.getApplication(content);
        
        Assert.assertNull(
            context.getControllerManager().getController(App3TestController.class));
        
    }

    public void testApp4(){
        String content =
        "<ns1:component-scan use-default-filters=\"false\" "
        + "base-package=\"org.brandao.brutos.annotation.helper.componentscan.app4\"/>";
        
        ConfigurableApplicationContext context = this.getApplication(content);
        
        Assert.assertNotNull(
            context.getControllerManager().getController(App4TestController.class));
        
    }

    public void testApp5(){
        String content =
        "<ns1:component-scan use-default-filters=\"false\" "
        + "base-package=\"org.brandao.brutos.annotation.helper.componentscan.app5\"/>";
        
        ConfigurableApplicationContext context = this.getApplication(content);
        
        Assert.assertNull(
            context.getControllerManager().getController(App5TestController.class));
        
    }

    public void testApp6(){
        String content =
        "<ns1:component-scan use-default-filters=\"false\" "
        + "base-package=\"org.brandao.brutos.annotation.helper.componentscan.app6\"/>";
        
        ConfigurableApplicationContext context = this.getApplication(content);
        
        Assert.assertNull(
            context.getControllerManager().getController(App6TestController.class));
        
    }

    public void testApp7(){
        String content =
        "<ns1:component-scan use-default-filters=\"false\" "
        + "base-package=\"org.brandao.brutos.annotation.helper.componentscan.app7\"/>";
        
        ConfigurableApplicationContext context = this.getApplication(content);
        
        Assert.assertNotNull(
            context.getControllerManager().getController(App7TestController.class));
        
    }

    public void testApp8(){
        String content =
        "<ns1:component-scan use-default-filters=\"false\" "
        + "base-package=\"org.brandao.brutos.annotation.helper.componentscan.app8\"/>";
        
        try{
            this.getApplication(content);
            Assert.fail("Expected BrutosException");
        }
        catch(BrutosException e){
            if(!"CustomScanner".equals(e.getCause().getMessage()))
                Assert.fail("Expected BrutosException");
        }
        catch(Throwable e){
            Assert.fail("Expected BrutosException");
        }
        
        
    }
    
}
