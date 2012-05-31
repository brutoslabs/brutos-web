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
import org.brandao.brutos.BrutosConstants;
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

        prop.setProperty("org.brandao.brutos.view.prefix",
                "/WEB-INF/jsp/");

        prop.setProperty("org.brandao.brutos.view.suffix",
                ".jsp");

        prop.setProperty("org.brandao.brutos.view.index",
                "index");
        
        annotationApplicationContext.configure(prop);
        return annotationApplicationContext;
    }
    
    public void test1() throws NoSuchMethodException{
        
        Class clazz = ControllerTest1Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Assert.assertNotNull(controller.getAction());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest1/index.jsp",controller.getView());
        
        Action action = controller.getActionByName("myfirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myfirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest1/myfirst.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getMethodName());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }
    
    public void test2() throws NoSuchMethodException{
        
        Class clazz = ControllerTest2Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Assert.assertNotNull(controller.getAction());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest2/index.jsp",controller.getView());
        
        Action action = controller.getActionByName("myfirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("action",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest2/action.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getMethodName());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test3() throws NoSuchMethodException{
        
        Class clazz = ControllerTest3Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Assert.assertNotNull(controller.getAction());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest3/index.jsp",controller.getView());
        
        Action action = controller.getActionByName("myfirst");
        
        Assert.assertNull(action);
    }
    
    public void test4() throws NoSuchMethodException{
        
        Class clazz = ControllerTest4Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Assert.assertNotNull(controller.getAction());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest4/index.jsp",controller.getView());
        
        Action action = controller.getActionByName("myfirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirst"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest4/myfirst.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirst").getName(),action.getMethodName());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test5() throws NoSuchMethodException{
        
        Class clazz = ControllerTest5Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Assert.assertNotNull(controller.getAction());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest5/index.jsp",controller.getView());
        
        Action action = controller.getActionByName("myfirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest5/myfirst.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getMethodName());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals("actionResult",action.getReturnIn());
    }

    public void test6() throws NoSuchMethodException{
        
        Class clazz = ControllerTest6Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Assert.assertNotNull(controller.getAction());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest6/index.jsp",controller.getView());
        
        Action action = controller.getActionByName("myfirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertNull(action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getMethodName());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test7() throws NoSuchMethodException{
        
        Class clazz = ControllerTest7Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Assert.assertNotNull(controller.getAction());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertNull(controller.getView());
        
        Action action = controller.getActionByName("myfirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest7/myfirst.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getMethodName());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }
    
}
