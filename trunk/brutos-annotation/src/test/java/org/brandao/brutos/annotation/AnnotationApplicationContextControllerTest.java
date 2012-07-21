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

import java.util.Date;
import junit.framework.Assert;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.helper.*;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.PropertyController;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.type.DateTimeType;
import org.brandao.brutos.EnumerationType;

/**
 *
 * @author Brandao
 */
public class AnnotationApplicationContextControllerTest 
    extends AbstractApplicationContextTest{
    
    public void test1() throws NoSuchMethodException{
        
        Class clazz = ControllerTest1Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest1/index.jsp",controller.getView());
        
        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest1/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }
    
    public void test2() throws NoSuchMethodException{
        
        Class clazz = ControllerTest2.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest2/index.jsp",controller.getView());
        
        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest2/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
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
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals("action",controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest3/index.jsp",controller.getView());
        
        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest3/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }
    
    public void test4() throws NoSuchMethodException{
        
        Class clazz = ControllerTest4Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals("myFirst",controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest4/index.jsp",controller.getView());
        
        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest4/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
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
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals("controllerTest5Controller",controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest5/index.jsp",controller.getView());
        
        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest5/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test6() throws NoSuchMethodException{
        
        Class clazz = ControllerTest6Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller);
    }

    public void test7() throws NoSuchMethodException{
        
        Class clazz = ControllerTest7Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertNull(controller.getView());
        
        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest7/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test8() throws NoSuchMethodException{
        
        Class clazz = ControllerTest8Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/jsp/controller9controller.jsp",controller.getView());
        
        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest8/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test9() throws NoSuchMethodException{
        
        Class clazz = ControllerTest9Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(DispatcherType.REDIRECT,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/jsp/controller9controller.jsp",controller.getView());
        
        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest9/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test10() throws NoSuchMethodException{
        
        Class clazz = ControllerTest10Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertEquals("/controller/{invoke}",controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
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
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertEquals("/controller/{invoke}",controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
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
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest12/index.jsp",controller.getView());
        
        ThrowableSafeData ex = controller.getThrowsSafe(Exception.class);
        Assert.assertNotNull(ex);
        Assert.assertNull(ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex.getDispatcher());
        Assert.assertEquals("/WEB-INF/controllertest12/exception.jsp",ex.getView());
        Assert.assertEquals(Exception.class,ex.getTarget());
        
        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest12/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test13() throws NoSuchMethodException{
        
        Class clazz = ControllerTest13Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest13/index.jsp",controller.getView());
        
        ThrowableSafeData ex = controller.getThrowsSafe(Exception.class);
        Assert.assertNotNull(ex);
        Assert.assertNull(ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex.getDispatcher());
        Assert.assertEquals("/WEB-INF/controllertest13/exception.jsp",ex.getView());
        Assert.assertEquals(Exception.class,ex.getTarget());

        ThrowableSafeData ex2 = controller.getThrowsSafe(RuntimeException.class);
        Assert.assertNotNull(ex2);
        Assert.assertNull(ex2.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex2.getDispatcher());
        Assert.assertEquals("/WEB-INF/controllertest13/runtimeexception.jsp",ex2.getView());
        Assert.assertEquals(RuntimeException.class,ex2.getTarget());
        
        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest13/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test14() throws NoSuchMethodException{
        
        Class clazz = ControllerTest14Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
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
        Assert.assertNull(ex2.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex2.getDispatcher());
        Assert.assertEquals("/WEB-INF/controllertest14/runtimeexception.jsp",ex2.getView());
        Assert.assertEquals(RuntimeException.class,ex2.getTarget());
        
        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest14/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test15() throws NoSuchMethodException{
        
        Class clazz = ControllerTest15Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
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

        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest15/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test16() throws NoSuchMethodException{
        
        Class clazz = ControllerTest16Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
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

        Action action = controller.getActionByName("myFirst");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("myFirst",action.getName());
        Assert.assertEquals(clazz.getMethod("myFirstAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/controllertest16/myfirstaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myFirstAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void test17() throws NoSuchMethodException{
        
        Class clazz = ControllerTest17Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/controllertest17/index.jsp",controller.getView());
        
        ThrowableSafeData ex = controller.getThrowsSafe(Exception.class);
        Assert.assertNull(ex);
    }

    public void test18() throws NoSuchMethodException{
        
        Class clazz = ControllerTest18Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyA");
        Assert.assertEquals(int.class, property.getBean().getClassType());
        Assert.assertEquals("propertyA", property.getName());
        Assert.assertEquals("propertyA", property.getBean().getNome());
        Assert.assertNull(property.getBean().getMapping());
        
        property = controller.getProperty("propertyB");
        Assert.assertEquals(String.class, property.getBean().getClassType());
        Assert.assertEquals("propertyB", property.getName());
        Assert.assertEquals("prop", property.getBean().getNome());
        Assert.assertNull(property.getBean().getMapping());
        
        property = controller.getProperty("propertyC");
        Assert.assertEquals(Date.class, property.getBean().getClassType());
        Assert.assertEquals("propertyC", property.getName());
        Assert.assertEquals("propertyC", property.getBean().getNome());
        Assert.assertEquals("dd/MM/yyyy", ((DateTimeType)property.getBean().getType()).getMask());
        Assert.assertNull(property.getBean().getMapping());
        
        property = controller.getProperty("propertyD");
        Assert.assertEquals(Date.class, property.getBean().getClassType());
        Assert.assertEquals("propertyD", property.getName());
        Assert.assertEquals("propertyD", property.getBean().getNome());
        Assert.assertEquals("yyyy-MM-dd", ((DateTimeType)property.getBean().getType()).getMask());
        Assert.assertNull(property.getBean().getMapping());
        
        property = controller.getProperty("propertyE");
        Assert.assertEquals(EnumTest.class, property.getBean().getClassType());
        Assert.assertEquals("propertyE", property.getName());
        Assert.assertEquals("propertyE", property.getBean().getNome());
        Assert.assertEquals(EnumerationType.ORDINAL, ((org.brandao.brutos.type.EnumType)property.getBean().getType()).getEnumType());
        Assert.assertNull(property.getBean().getMapping());

        property = controller.getProperty("propertyF");
        Assert.assertEquals(EnumTest.class, property.getBean().getClassType());
        Assert.assertEquals("propertyF", property.getName());
        Assert.assertEquals("propertyF", property.getBean().getNome());
        Assert.assertEquals(EnumerationType.ORDINAL, ((org.brandao.brutos.type.EnumType)property.getBean().getType()).getEnumType());
        Assert.assertNull(property.getBean().getMapping());

        property = controller.getProperty("propertyG");
        Assert.assertEquals(EnumTest.class, property.getBean().getClassType());
        Assert.assertEquals("propertyG", property.getName());
        Assert.assertEquals("propertyG", property.getBean().getNome());
        Assert.assertEquals(EnumerationType.STRING, ((org.brandao.brutos.type.EnumType)property.getBean().getType()).getEnumType());
        Assert.assertNull(property.getBean().getMapping());
    }

    public void test19() throws NoSuchMethodException{
        
        Class clazz = ControllerTest19Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        PropertyController property = controller.getProperty("propertyA");
        Assert.assertEquals(int.class, property.getBean().getClassType());
        Assert.assertEquals("propertyA", property.getName());
        Assert.assertEquals("propertyA", property.getBean().getNome());
        Assert.assertNull(property.getBean().getMapping());
        
        property = controller.getProperty("propertyB");
        Assert.assertEquals(String.class, property.getBean().getClassType());
        Assert.assertEquals("propertyB", property.getName());
        Assert.assertEquals("prop", property.getBean().getNome());
        Assert.assertNull(property.getBean().getMapping());
        
        property = controller.getProperty("propertyC");
        Assert.assertEquals(Date.class, property.getBean().getClassType());
        Assert.assertEquals("propertyC", property.getName());
        Assert.assertEquals("propertyC", property.getBean().getNome());
        Assert.assertEquals("dd/MM/yyyy", ((DateTimeType)property.getBean().getType()).getMask());
        Assert.assertNull(property.getBean().getMapping());
        
        property = controller.getProperty("propertyD");
        Assert.assertEquals(Date.class, property.getBean().getClassType());
        Assert.assertEquals("propertyD", property.getName());
        Assert.assertEquals("propertyD", property.getBean().getNome());
        Assert.assertEquals("yyyy-MM-dd", ((DateTimeType)property.getBean().getType()).getMask());
        Assert.assertNull(property.getBean().getMapping());
        
        property = controller.getProperty("propertyE");
        Assert.assertEquals(EnumTest.class, property.getBean().getClassType());
        Assert.assertEquals("propertyE", property.getName());
        Assert.assertEquals("propertyE", property.getBean().getNome());
        Assert.assertEquals(EnumerationType.ORDINAL, ((org.brandao.brutos.type.EnumType)property.getBean().getType()).getEnumType());
        Assert.assertNull(property.getBean().getMapping());

        property = controller.getProperty("propertyF");
        Assert.assertEquals(EnumTest.class, property.getBean().getClassType());
        Assert.assertEquals("propertyF", property.getName());
        Assert.assertEquals("propertyF", property.getBean().getNome());
        Assert.assertEquals(EnumerationType.ORDINAL, ((org.brandao.brutos.type.EnumType)property.getBean().getType()).getEnumType());
        Assert.assertNull(property.getBean().getMapping());

        property = controller.getProperty("propertyG");
        Assert.assertEquals(EnumTest.class, property.getBean().getClassType());
        Assert.assertEquals("propertyG", property.getName());
        Assert.assertEquals("propertyG", property.getBean().getNome());
        Assert.assertEquals(EnumerationType.STRING, ((org.brandao.brutos.type.EnumType)property.getBean().getType()).getEnumType());
        Assert.assertNull(property.getBean().getMapping());
    }
    
}
