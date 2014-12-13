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

import java.util.*;
import junit.framework.Assert;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.helper.*;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.PropertyController;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.type.DateTimeType;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.annotation.helper.bean.BeanConstructorTest;
import org.brandao.brutos.annotation.helper.bean.CustomArrayList;
import org.brandao.brutos.mapping.*;
import org.brandao.brutos.type.IntegerWrapperType;
import org.brandao.brutos.type.StringType;
import org.brandao.brutos.TypeManager;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.type.DefaultTypeFactory;
import org.brandao.brutos.type.ObjectType;
import org.brandao.brutos.type.TypeUtil;

/**
 *
 * @author Brandao
 */
public class AnnotationApplicationContextControllerTest 
    extends AbstractWebAnnotationApplicationContextTest{

    public AnnotationApplicationContextControllerTest(){
        //TypeManager.remove(List.class);
    }
    
    @Override
    public ConfigurableApplicationContext getApplication(Class[] clazz, String complement){
        ConfigurableApplicationContext context = super.getApplication(clazz, complement);
        context.getTypeManager().remove(List.class);
        return context;
    }
    
    
    public void test1() throws NoSuchMethodException{
        
        Class clazz = ControllerTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest1/index.jsp",controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        Action action = controller.getActionByName("/myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "myFirst",action.getId());
        Assert.assertEquals("/myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest1/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }
    
    public void test2() throws NoSuchMethodException{
        
        Class clazz = ControllerTest2.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest2/index.jsp",controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        Action action = controller.getActionByName("/myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "myFirst",action.getId());
        Assert.assertEquals("/myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest2/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test3() throws NoSuchMethodException{
        
        Class clazz = ControllerTest3Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals("action",controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest3/index.jsp",controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        Action action = controller.getActionByName("/myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "myFirst",action.getId());
        Assert.assertEquals("/myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest3/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }
    
    public void test4() throws NoSuchMethodException{
        
        Class clazz = ControllerTest4Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals("myFirst",controller.getDefaultAction());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest4/index.jsp",controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        Action action = controller.getActionByName("/myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "myFirst",action.getId());
        Assert.assertEquals("/myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest4/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test5() throws NoSuchMethodException{
        
        Class clazz = ControllerTest5Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals("controllerTest5Controller",controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest5/index.jsp",controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        Action action = controller.getActionByName("/myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "myFirst",action.getId());
        Assert.assertEquals("/myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest5/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test6() throws NoSuchMethodException{
        
        Class clazz = ControllerTest6Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller);
    }

    public void test7() throws NoSuchMethodException{
        
        Class clazz = ControllerTest7Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertNull(controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        Action action = controller.getActionByName("/myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "myFirst",action.getId());
        Assert.assertEquals("/myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest7/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test8() throws NoSuchMethodException{
        
        Class clazz = ControllerTest8Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/jsp/controller9controller.jsp",controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        Action action = controller.getActionByName("/myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "myFirst",action.getId());
        Assert.assertEquals("/myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest8/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test9() throws NoSuchMethodException{
        
        Class clazz = ControllerTest9Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(DispatcherType.REDIRECT,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/jsp/controller9controller.jsp",controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        Action action = controller.getActionByName("/myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "myFirst",action.getId());
        Assert.assertEquals("/myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest9/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test10() throws NoSuchMethodException{
        
        Class clazz = ControllerTest10Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertEquals("/controller/{invoke}",controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertNull(controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest10/index.jsp",controller.getView());
        
        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest10/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test11() throws NoSuchMethodException{
        
        Class clazz = ControllerTest11Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertEquals("/controller/{invoke}",controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertNull(controller.getName());
        Assert.assertEquals(1,controller.getAlias().size());
        
        Assert.assertEquals("/controller", controller.getAlias().get(0));
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest11/index.jsp",controller.getView());
        
        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest11/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test12() throws NoSuchMethodException{
        
        Class clazz = ControllerTest12Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest12/index.jsp",controller.getView());
        
        ThrowableSafeData ex = controller.getThrowsSafe(Exception.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex.getDispatcher());
        Assert.assertEquals("/WEB-INF/controllertest12/exception.jsp",ex.getView());
        Assert.assertEquals(Exception.class,ex.getTarget());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        Action action = controller.getActionByName("/myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "myFirst",action.getId());
        Assert.assertEquals("/myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest12/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test13() throws NoSuchMethodException{
        
        Class clazz = ControllerTest13Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest13/index.jsp",controller.getView());
        
        ThrowableSafeData ex = controller.getThrowsSafe(Exception.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex.getDispatcher());
        Assert.assertEquals("/WEB-INF/controllertest13/exception.jsp",ex.getView());
        Assert.assertEquals(Exception.class,ex.getTarget());

        ThrowableSafeData ex2 = controller.getThrowsSafe(RuntimeException.class);
        Assert.assertNotNull(ex2);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex2.getDispatcher());
        Assert.assertEquals("/WEB-INF/controllertest13/runtimeexception.jsp",ex2.getView());
        Assert.assertEquals(RuntimeException.class,ex2.getTarget());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        Action action = controller.getActionByName("/myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "myFirst",action.getId());
        Assert.assertEquals("/myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest13/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test14() throws NoSuchMethodException{
        
        Class clazz = ControllerTest14Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest14/index.jsp",controller.getView());
        
        ThrowableSafeData ex = controller.getThrowsSafe(Exception.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals("ex",ex.getParameterName());
        Assert.assertEquals(DispatcherType.REDIRECT,ex.getDispatcher());
        Assert.assertEquals("/WEB-INF/exception.jsp",ex.getView());
        Assert.assertEquals(Exception.class,ex.getTarget());

        ThrowableSafeData ex2 = controller.getThrowsSafe(RuntimeException.class);
        Assert.assertNotNull(ex2);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex2.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex2.getDispatcher());
        Assert.assertEquals("/WEB-INF/controllertest14/runtimeexception.jsp",ex2.getView());
        Assert.assertEquals(RuntimeException.class,ex2.getTarget());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        Action action = controller.getActionByName("/myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "myFirst",action.getId());
        Assert.assertEquals("/myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest14/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test15() throws NoSuchMethodException{
        
        Class clazz = ControllerTest15Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest15/index.jsp",controller.getView());
        
        ThrowableSafeData ex = controller.getThrowsSafe(Exception.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals("ex",ex.getParameterName());
        Assert.assertEquals(DispatcherType.REDIRECT,ex.getDispatcher());
        Assert.assertEquals("/WEB-INF/exception.jsp",ex.getView());
        Assert.assertEquals(Exception.class,ex.getTarget());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        Action action = controller.getActionByName("/myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "myFirst",action.getId());
        Assert.assertEquals("/myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest15/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test16() throws NoSuchMethodException{
        
        Class clazz = ControllerTest16Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest16/index.jsp",controller.getView());
        
        ThrowableSafeData ex = controller.getThrowsSafe(Exception.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals("ex",ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex.getDispatcher());
        Assert.assertNull(ex.getView());
        Assert.assertEquals(Exception.class,ex.getTarget());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        Action action = controller.getActionByName("/myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "myFirst",action.getId());
        Assert.assertEquals("/myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest16/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test17() throws NoSuchMethodException{
        
        Class clazz = ControllerTest17Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest17/index.jsp",controller.getView());
        
        ThrowableSafeData ex = controller.getThrowsSafe(Exception.class);
        Assert.assertNull(ex);
    }

    public void test18() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyA");
        Assert.assertEquals(int.class, property.getClassType());
        Assert.assertEquals("propertyA", property.getPropertyName());
        Assert.assertEquals("propertyA", property.getName());
        Assert.assertNull(property.getMapping());
    }

    public void test19() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyB");
        Assert.assertEquals(String.class, property.getClassType());
        Assert.assertEquals("propertyB", property.getPropertyName());
        Assert.assertEquals("prop", property.getName());
        Assert.assertNull(property.getMapping());
    }

    public void test20() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyC");
        Assert.assertEquals(Date.class, property.getClassType());
        Assert.assertEquals("propertyC", property.getPropertyName());
        Assert.assertEquals("propertyC", property.getName());
        Assert.assertEquals("dd/MM/yyyy", ((DateTimeType)property.getType()).getPattern());
        Assert.assertNull(property.getMapping());
    }

    public void test21() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyD");
        Assert.assertEquals(Date.class, property.getClassType());
        Assert.assertEquals("propertyD", property.getPropertyName());
        Assert.assertEquals("propertyD", property.getName());
        Assert.assertEquals("yyyy-MM-dd", ((DateTimeType)property.getType()).getPattern());
        Assert.assertNull(property.getMapping());
    }

    public void test22() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyE");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyE", property.getPropertyName());
        Assert.assertEquals("propertyE", property.getName());
        Assert.assertEquals(EnumerationType.ORDINAL, ((org.brandao.brutos.type.EnumType)property.getType()).getEnumerationType());
        Assert.assertNull(property.getMapping());
    }

    public void test23() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyF");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyF", property.getPropertyName());
        Assert.assertEquals("propertyF", property.getName());
        Assert.assertEquals(EnumerationType.ORDINAL, ((org.brandao.brutos.type.EnumType)property.getType()).getEnumerationType());
        Assert.assertNull(property.getMapping());
    }

    public void test24() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyG");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyG", property.getPropertyName());
        Assert.assertEquals("propertyG", property.getName());
        Assert.assertEquals(EnumerationType.STRING, ((org.brandao.brutos.type.EnumType)property.getType()).getEnumerationType());
        Assert.assertNull(property.getMapping());
    }

    public void test25() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyH");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("myKey", key.getParameterName());
        Assert.assertEquals(Integer.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(EnumerationType.STRING, key.getEnumProperty());
        Assert.assertEquals("yyyy-MM-dd", key.getTemporalType());
        Assert.assertEquals(IntegerWrapperType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(String.class, element.getClassType());
        Assert.assertEquals(ScopeType.REQUEST, element.getScopeType());
        Assert.assertEquals(EnumerationType.STRING, element.getEnumProperty());
        Assert.assertEquals("yyyy-MM-dd", element.getTemporalType());
        Assert.assertEquals(StringType.class,element.getType().getClass());
        Assert.assertNull(element.getValue());
    }

    public void test26() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyI");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(LinkedHashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertEquals(StringType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertEquals(IntegerWrapperType.class,element.getType().getClass());
        Assert.assertNull(element.getValue());
    }

    public void test27() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyJ");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNotNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertEquals(IntegerWrapperType.class,element.getType().getClass());
        Assert.assertNull(element.getValue());
    }

    public void test28() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyK");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertEquals(StringType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
    }

    public void test29() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyL");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertEquals(StringType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
    }

    public void test30() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyM");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertEquals(IntegerWrapperType.class,element.getType().getClass());
        Assert.assertNull(element.getValue());
    }

    public void test31() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyN");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(LinkedList.class, beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertEquals(IntegerWrapperType.class,element.getType().getClass());
        Assert.assertNull(element.getValue());
    }

    public void test32() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyO");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertEquals(IntegerWrapperType.class,element.getType().getClass());
        Assert.assertNull(element.getValue());
    }

    public void test33() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyP");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
    }

    public void test34() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyQ");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertNotNull(element.getType());
        Assert.assertNull(element.getValue());
    }
    
    public void test35() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyR");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("myKey", key.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNotNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
    }

    public void test36() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyS");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
    }

    public void test37() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyT");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertEquals(StringType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(TypeUtil.getDefaultListType(), element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        Assert.assertEquals("element", subElement.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, subElement.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        Assert.assertNotNull(subElement.getMapping());
        Assert.assertNull(subElement.getType());
        Assert.assertNull(subElement.getValue());
    }

    public void test38() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyU");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertEquals(StringType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(CustomArrayList.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        Assert.assertEquals("myElement", subElement.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, subElement.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        Assert.assertNotNull(subElement.getMapping());
        Assert.assertNull(subElement.getType());
        Assert.assertNull(subElement.getValue());
    }

    public void test39() throws NoSuchMethodException{
        Class clazz = ControllerTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyV");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertEquals(StringType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement2", element.getParameterName());
        Assert.assertEquals(CustomArrayList.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        Assert.assertEquals("myElement", subElement.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, subElement.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        Assert.assertNotNull(subElement.getMapping());
        Assert.assertNull(subElement.getType());
        Assert.assertNull(subElement.getValue());
    }
    
    public void test40() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyA");
        Assert.assertEquals(int.class, property.getClassType());
        Assert.assertEquals("propertyA", property.getPropertyName());
        Assert.assertEquals("propertyA", property.getName());
        Assert.assertNull(property.getMapping());
    }

    public void test41() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyB");
        Assert.assertEquals(String.class, property.getClassType());
        Assert.assertEquals("propertyB", property.getPropertyName());
        Assert.assertEquals("prop", property.getName());
        Assert.assertNull(property.getMapping());
    }

    public void test42() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyC");
        Assert.assertEquals(Date.class, property.getClassType());
        Assert.assertEquals("propertyC", property.getPropertyName());
        Assert.assertEquals("propertyC", property.getName());
        Assert.assertEquals("dd/MM/yyyy", ((DateTimeType)property.getType()).getPattern());
        Assert.assertNull(property.getMapping());
    }

    public void test43() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyD");
        Assert.assertEquals(Date.class, property.getClassType());
        Assert.assertEquals("propertyD", property.getPropertyName());
        Assert.assertEquals("propertyD", property.getName());
        Assert.assertEquals("yyyy-MM-dd", ((DateTimeType)property.getType()).getPattern());
        Assert.assertNull(property.getMapping());
    }

    public void test44() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyE");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyE", property.getPropertyName());
        Assert.assertEquals("propertyE", property.getName());
        Assert.assertEquals(EnumerationType.ORDINAL, ((org.brandao.brutos.type.EnumType)property.getType()).getEnumerationType());
        Assert.assertNull(property.getMapping());
    }

    public void test45() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyF");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyF", property.getPropertyName());
        Assert.assertEquals("propertyF", property.getName());
        Assert.assertEquals(EnumerationType.ORDINAL, ((org.brandao.brutos.type.EnumType)property.getType()).getEnumerationType());
        Assert.assertNull(property.getMapping());
    }

    public void test46() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyG");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyG", property.getPropertyName());
        Assert.assertEquals("propertyG", property.getName());
        Assert.assertEquals(EnumerationType.STRING, ((org.brandao.brutos.type.EnumType)property.getType()).getEnumerationType());
        Assert.assertNull(property.getMapping());
    }

    public void test47() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyH");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("myKey", key.getParameterName());
        Assert.assertEquals(Integer.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(EnumerationType.STRING, key.getEnumProperty());
        Assert.assertEquals("yyyy-MM-dd", key.getTemporalType());
        Assert.assertEquals(IntegerWrapperType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(String.class, element.getClassType());
        Assert.assertEquals(ScopeType.REQUEST, element.getScopeType());
        Assert.assertEquals(EnumerationType.STRING, element.getEnumProperty());
        Assert.assertEquals("yyyy-MM-dd", element.getTemporalType());
        Assert.assertEquals(StringType.class,element.getType().getClass());
        Assert.assertNull(element.getValue());
    }

    public void test48() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyI");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(LinkedHashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertEquals(StringType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertEquals(IntegerWrapperType.class,element.getType().getClass());
        Assert.assertNull(element.getValue());
    }

    public void test49() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyJ");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNotNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertEquals(IntegerWrapperType.class,element.getType().getClass());
        Assert.assertNull(element.getValue());
    }

    public void test50() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyK");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertEquals(StringType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
    }

    public void test51() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyL");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(HashMap.class, beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertEquals(StringType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
    }

    public void test52() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyM");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertEquals(IntegerWrapperType.class,element.getType().getClass());
        Assert.assertNull(element.getValue());
    }

    public void test53() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyN");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(LinkedList.class, beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertEquals(IntegerWrapperType.class,element.getType().getClass());
        Assert.assertNull(element.getValue());
    }

    public void test54() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyO");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(Integer.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertEquals(IntegerWrapperType.class,element.getType().getClass());
        Assert.assertNull(element.getValue());
    }

    public void test55() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyP");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
    }

    public void test56() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyQ");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNull(element.getMapping());
        Assert.assertNotNull(element.getType());
        Assert.assertNull(element.getValue());
    }
    
    public void test57() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyR");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("myKey", key.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNotNull(key.getMapping());
        Assert.assertNull(key.getType());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
    }

    public void test58() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = null;
        annotationApplicationContext = getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyS");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultListType(), beanProperty.getClassType());
        
        DependencyBean element = ((CollectionBean)beanProperty).getCollection();
        Assert.assertEquals("myElement", element.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
    }

    public void test59() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyT");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertEquals(StringType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(TypeUtil.getDefaultListType(), element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        Assert.assertEquals("element", subElement.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, subElement.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        Assert.assertNotNull(subElement.getMapping());
        Assert.assertNull(subElement.getType());
        Assert.assertNull(subElement.getValue());
    }

    public void test60() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyU");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertEquals(StringType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("element", element.getParameterName());
        Assert.assertEquals(CustomArrayList.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        Assert.assertEquals("myElement", subElement.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, subElement.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        Assert.assertNotNull(subElement.getMapping());
        Assert.assertNull(subElement.getType());
        Assert.assertNull(subElement.getValue());
    }

    public void test61() throws NoSuchMethodException{
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyV");
        org.brandao.brutos.mapping.Bean beanProperty = property.getMapping();
        Assert.assertEquals(TypeUtil.getDefaultMapType(), beanProperty.getClassType());
        
        DependencyBean key = ((MapBean)beanProperty).getKey();
        Assert.assertEquals("key", key.getParameterName());
        Assert.assertEquals(String.class, key.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, key.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, key.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, key.getTemporalType());
        Assert.assertNull(key.getMapping());
        Assert.assertEquals(StringType.class,key.getType().getClass());
        Assert.assertNull(key.getValue());

        DependencyBean element = ((MapBean)beanProperty).getCollection();
        Assert.assertEquals("myElement2", element.getParameterName());
        Assert.assertEquals(CustomArrayList.class, element.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, element.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, element.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, element.getTemporalType());
        Assert.assertNotNull(element.getMapping());
        Assert.assertNull(element.getType());
        Assert.assertNull(element.getValue());
        
        DependencyBean subElement = ((CollectionBean)element.getBean()).getCollection();
        Assert.assertEquals("myElement", subElement.getParameterName());
        Assert.assertEquals(BeanConstructorTest.class, subElement.getClassType());
        Assert.assertEquals(BrutosConstants.DEFAULT_SCOPETYPE, subElement.getScopeType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ENUMERATIONTYPE, subElement.getEnumProperty());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY, subElement.getTemporalType());
        Assert.assertNotNull(subElement.getMapping());
        Assert.assertNull(subElement.getType());
        Assert.assertNull(subElement.getValue());
    }
    
    /*
    public void test19() throws NoSuchMethodException{
        
        Class clazz = ControllerTest19Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyA");
        Assert.assertEquals(int.class, property.getClassType());
        Assert.assertEquals("propertyA", property.getPropertyName());
        Assert.assertEquals("propertyA", property.getName());
        Assert.assertNull(property.getMapping());
        
        property = controller.getProperty("propertyB");
        Assert.assertEquals(String.class, property.getClassType());
        Assert.assertEquals("propertyB", property.getPropertyName());
        Assert.assertEquals("prop", property.getName());
        Assert.assertNull(property.getMapping());
        
        property = controller.getProperty("propertyC");
        Assert.assertEquals(Date.class, property.getClassType());
        Assert.assertEquals("propertyC", property.getPropertyName());
        Assert.assertEquals("propertyC", property.getName());
        Assert.assertEquals("dd/MM/yyyy", ((DateTimeType)property.getType()).getMask());
        Assert.assertNull(property.getMapping());
        
        property = controller.getProperty("propertyD");
        Assert.assertEquals(Date.class, property.getClassType());
        Assert.assertEquals("propertyD", property.getPropertyName());
        Assert.assertEquals("propertyD", property.getName());
        Assert.assertEquals("yyyy-MM-dd", ((DateTimeType)property.getType()).getMask());
        Assert.assertNull(property.getMapping());
        
        property = controller.getProperty("propertyE");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyE", property.getPropertyName());
        Assert.assertEquals("propertyE", property.getName());
        Assert.assertEquals(EnumerationType.ORDINAL, ((org.brandao.brutos.type.EnumType)property.getType()).getEnumType());
        Assert.assertNull(property.getMapping());

        property = controller.getProperty("propertyF");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyF", property.getPropertyName());
        Assert.assertEquals("propertyF", property.getName());
        Assert.assertEquals(EnumerationType.ORDINAL, ((org.brandao.brutos.type.EnumType)property.getType()).getEnumType());
        Assert.assertNull(property.getMapping());

        property = controller.getProperty("propertyG");
        Assert.assertEquals(EnumTest.class, property.getClassType());
        Assert.assertEquals("propertyG", property.getPropertyName());
        Assert.assertEquals("propertyG", property.getName());
        Assert.assertEquals(EnumerationType.STRING, ((org.brandao.brutos.type.EnumType)property.getType()).getEnumType());
        Assert.assertNull(property.getMapping());
    }
    */
}
