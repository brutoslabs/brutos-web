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

import java.util.Properties;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.brandao.brutos.annotation.helper.*;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.test.MockIOCProvider;
import org.brandao.brutos.test.MockViewProvider;

/**
 *
 * @author Brandao
 */
public class AnnotationApplicationContextTest extends TestCase{
    
    private AnnotationApplicationContext getApplication(Class[] clazz){
        AnnotationApplicationContext 
            annotationApplicationContext = 
                new AnnotationApplicationContext(
                        clazz
                );
        
        Properties prop = new Properties();
        prop.setProperty("org.brandao.brutos.ioc.provider",
                MockIOCProvider.class.getName());
        
        prop.setProperty("org.brandao.brutos.view.provider",
                MockViewProvider.class.getName());
        
        annotationApplicationContext.configure(prop);
        return annotationApplicationContext;
    }
    
    public void test1(){
        
        Class clazz = ControllerTest1Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Action action = controller.getMethodByName("myfirst");
        Assert.assertEquals("myfirst",action.getName());
    }
    
    public void test2(){
        
        Class clazz = ControllerTest2Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Action action = controller.getMethodByName("action");
        Assert.assertEquals("action",action.getName());
    }

    public void test3(){
        
        Class clazz = ControllerTest3Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Action action = controller.getMethodByName("myfirst");
        Assert.assertNull(action);
    }
    
    public void test4(){
        
        Class clazz = ControllerTest4Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Action action = controller.getMethodByName("myfirst");
        Assert.assertEquals("myfirst",action.getName());
        Assert.assertEquals("result",action.getReturnIn());
    }

    public void test5(){
        
        Class clazz = ControllerTest5Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Action action = controller.getMethodByName("myfirst");
        Assert.assertEquals("myfirst",action.getName());
        Assert.assertEquals("actionResult",action.getReturnIn());
    }
    
}
