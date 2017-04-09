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

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import junit.framework.Assert;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.annotation.helper.*;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.ParameterAction;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.type.CalendarType;
import org.brandao.brutos.type.DateTimeType;
import org.brandao.brutos.type.DefaultDateType;
import org.brandao.brutos.type.DefaultEnumType;
import org.brandao.brutos.type.IntegerType;
import org.brandao.brutos.type.IntegerWrapperType;
import org.brandao.brutos.type.StringType;
import org.brandao.brutos.validator.RestrictionRules;
import org.brandao.brutos.validator.Validator;

/**
 *
 * @author Brandao
 */
public class AnnotationApplicationContextActionTest 
    extends AbstractWebAnnotationApplicationContextTest{
    
    public void testAction1() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest1Controller.class;
        
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
        Assert.assertEquals("/WEB-INF/views/actiontest1controller/index.jsp",controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
            controller.getActionByName( "/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest1controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction2() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest2Controller.class;
        
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
        Assert.assertEquals("/WEB-INF/views/actiontest2controller/index.jsp",controller.getView());
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        Assert.assertNull(action);
    }

    public void testAction3() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest3Controller.class;
        
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
        Assert.assertEquals("/WEB-INF/views/actiontest3controller/index.jsp",controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("my"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest3controller/my/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("my").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction4() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest4Controller.class;
        
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
        Assert.assertEquals("/WEB-INF/views/actiontest4controller/index.jsp",controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest4controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction5() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest5Controller.class;
        
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
        Assert.assertEquals("/WEB-INF/views/actiontest5controller/index.jsp",controller.getView());
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("/myaction");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/myaction",action.getName());
        Assert.assertEquals(clazz.getMethod("my"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest5controller/my/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("my").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction6() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest6Controller.class;
        
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
        Assert.assertEquals("/WEB-INF/views/actiontest6controller/index.jsp",controller.getView());
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("/myaction2");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/myaction2",action.getName());
        
        Assert.assertEquals(clazz.getMethod("my"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest6controller/my/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("my").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        action = controller.getActionByName("/myaction3");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/myaction2",action.getName());
        
        Assert.assertEquals(clazz.getMethod("my"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest6controller/my/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("my").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
    }

    public void testAction7() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest7Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest7controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ThrowableSafeData ex = action.getThrowsSafe(RuntimeException.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex.getDispatcher());
        Assert.assertEquals("/WEB-INF/views/actiontest7controller/myaction/runtimeexception.jsp",ex.getView());
        Assert.assertEquals(RuntimeException.class,ex.getTarget());

        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest7controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction8() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest8Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest8controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ThrowableSafeData ex = action.getThrowsSafe(RuntimeException.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex.getParameterName());
        Assert.assertEquals(DispatcherType.REDIRECT,ex.getDispatcher());
        Assert.assertEquals("/view/exception.jsp",ex.getView());
        Assert.assertEquals(RuntimeException.class,ex.getTarget());

        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest8controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction9() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest9Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest9controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ThrowableSafeData ex = action.getThrowsSafe(RuntimeException.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex.getDispatcher());
        Assert.assertEquals("/WEB-INF/views/actiontest9controller/myaction/runtimeexception.jsp",ex.getView());
        Assert.assertEquals(RuntimeException.class,ex.getTarget());

        ex = action.getThrowsSafe(Exception.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex.getParameterName());
        Assert.assertEquals(DispatcherType.REDIRECT,ex.getDispatcher());
        Assert.assertEquals("/view/exception.jsp",ex.getView());
        Assert.assertEquals(Exception.class,ex.getTarget());
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest9controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction10() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest10Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest10controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ThrowableSafeData ex = action.getThrowsSafe(Exception.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex.getDispatcher());
        Assert.assertNull(ex.getView());
        Assert.assertEquals(Exception.class,ex.getTarget());

        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest10controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction11() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest11Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest11controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ThrowableSafeData ex = action.getThrowsSafe(Exception.class);
        Assert.assertNull(ex);

        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest11controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction12() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest12Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest12controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        ThrowableSafeData ex = action.getThrowsSafe(Exception.class);
        Assert.assertNotNull(ex);
        Assert.assertNull(ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex.getDispatcher());
        Assert.assertEquals("/WEB-INF/views/actiontest12controller/myaction/exception.jsp",ex.getView());
        Assert.assertEquals(Exception.class,ex.getTarget());

        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest12controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction13() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest13Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest13controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest13controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction14() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest14Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest14controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest14controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals("actionResult",action.getReturnIn());
    }

    public void testAction15() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest15Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest15controller/index.jsp",controller.getView());

        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        Assert.assertNull(action);
    }

    public void testAction16() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest16Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest16controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/controller/view.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction17() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest17Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest17controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(DispatcherType.REDIRECT,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/controller/view.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction18() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest18Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest18controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertNull(action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction19() throws NoSuchMethodException{
        
    	try{
	        Class<?> clazz = ActionTest19Controller.class;
	        getApplication(new Class[]{clazz});
            Assert.fail("expected: {the action not return any value: my}");
    	}
    	catch(Throwable e){
            Assert.assertNotNull(e);
            Throwable ex = e;
            boolean hasError = false;
            do{
                if(ex.getMessage().equals("the action not return any value: my")){
                	hasError = true;
                    break;
                }
            }while((ex = ex.getCause()) != null);
            
            if(!hasError){
            	e.printStackTrace();
                Assert.fail("expected: {the action not return any value: my}");
            }
    	}
        
    }

    public void testAction20() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest20Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest20controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction",int.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest20controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",int.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(1,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getName());
        Assert.assertEquals(int.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(IntegerType.class, parameter.getType().getClass());
        Assert.assertNotNull(parameter.getValidate());
    }

    public void testAction21() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest21Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest21controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction",Integer.class,String.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest21controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",Integer.class,String.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(2,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getName());
        Assert.assertEquals(Integer.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(IntegerWrapperType.class, parameter.getType().getClass());
        Assert.assertNotNull(parameter.getValidate());
        
        parameter = action.getParameter(1);
        
        Assert.assertEquals("arg1", parameter.getName());
        Assert.assertEquals(String.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(StringType.class, parameter.getType().getClass());
        Assert.assertNotNull(parameter.getValidate());
        
    }

    public void testAction22() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest22Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest22controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction",Integer.class,String.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest22controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",Integer.class,String.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(2,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("param1", parameter.getName());
        Assert.assertEquals(Integer.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.REQUEST, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(IntegerWrapperType.class, parameter.getType().getClass());
        Assert.assertNotNull(parameter.getValidate());
        
        parameter = action.getParameter(1);
        
        Assert.assertEquals("arg1", parameter.getName());
        Assert.assertEquals(String.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.REQUEST, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(StringType.class, parameter.getType().getClass());
        Assert.assertNotNull(parameter.getValidate());
        
    }

    public void testAction23() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest23Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest23controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction",EnumTest.class,EnumTest.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest23controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",EnumTest.class,EnumTest.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(2,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getName());
        Assert.assertEquals(EnumTest.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.STRING, ((DefaultEnumType)parameter.getType()).getEnumerationType());
        Assert.assertNotNull(parameter.getValidate());
        
        parameter = action.getParameter(1);
        
        Assert.assertEquals("arg1", parameter.getName());
        Assert.assertEquals(EnumTest.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.ORDINAL, ((DefaultEnumType)parameter.getType()).getEnumerationType());
        Assert.assertNotNull(parameter.getValidate());
        
    }

    public void testAction24() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest24Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest24controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction",String.class,int.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest24controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",String.class,int.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(2,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getName());
        Assert.assertEquals(String.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(StringType.class, parameter.getType().getClass());
        Assert.assertNotNull(parameter.getValidate());
        
        Validator validator = parameter.getValidate();
        Properties config = validator.getConfiguration();
                
        Assert.assertEquals("true", config.get(RestrictionRules.REQUIRED.toString()));
        
        parameter = action.getParameter(1);
        
        Assert.assertEquals("arg1", parameter.getName());
        Assert.assertEquals(int.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(IntegerType.class, parameter.getType().getClass());
        Assert.assertNotNull(parameter.getValidate());

        validator = parameter.getValidate();
        config = validator.getConfiguration();
                
        Assert.assertEquals("true", config.get(RestrictionRules.REQUIRED.toString()));
        Assert.assertEquals("10", config.get(RestrictionRules.MIN.toString()));
        Assert.assertEquals("100", config.get(RestrictionRules.MAX.toString()));
        
    }

    public void testAction25() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest25Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest25controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction",String.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest25controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",String.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(1,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getName());
        Assert.assertEquals(String.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(StringType.class, parameter.getType().getClass());
        Assert.assertNotNull(parameter.getValidate());
        
        Validator validator = parameter.getValidate();
        Properties config = validator.getConfiguration();
                
        Assert.assertEquals("true", config.get(RestrictionRules.REQUIRED.toString()));
        
    }

    public void testAction26() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest26Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest26controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction",
                Date.class,Date.class,Calendar.class,Calendar.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest26controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",
                Date.class,Date.class,Calendar.class,Calendar.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(4,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getName());
        Assert.assertEquals(Date.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(DefaultDateType.class, parameter.getType().getClass());
        Assert.assertNotNull(parameter.getValidate());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY,((DateTimeType)parameter.getType()).getPattern());

        parameter = action.getParameter(1);
        
        Assert.assertEquals("arg1", parameter.getName());
        Assert.assertEquals(Date.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(DefaultDateType.class, parameter.getType().getClass());
        Assert.assertNotNull(parameter.getValidate());
        Assert.assertEquals("MM/dd/yyyy",((DateTimeType)parameter.getType()).getPattern());

        parameter = action.getParameter(2);
        
        Assert.assertEquals("arg2", parameter.getName());
        Assert.assertEquals(Calendar.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(CalendarType.class, parameter.getType().getClass());
        Assert.assertNotNull(parameter.getValidate());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY,((DateTimeType)parameter.getType()).getPattern());

        parameter = action.getParameter(3);
        
        Assert.assertEquals("arg3", parameter.getName());
        Assert.assertEquals(Calendar.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(CalendarType.class, parameter.getType().getClass());
        Assert.assertNotNull(parameter.getValidate());
        Assert.assertEquals("yyyy-MM-dd",((DateTimeType)parameter.getType()).getPattern());
        
    }

    public void testAction27() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest27Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest27controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = controller
                .getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertEquals(clazz.getMethod("myAction",int.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/views/actiontest27controller/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",int.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(1,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getName());
        Assert.assertEquals(int.class, parameter.getClassType());
        Assert.assertNull(parameter.getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getScopeType());
        Assert.assertNull(parameter.getStaticValue());
        Assert.assertEquals(TestType4.class, parameter.getType().getClass());
        Assert.assertNotNull(parameter.getValidate());
    }

    //Não Aplicável
    public void testAction28() throws NoSuchMethodException, Throwable{

        Class<?> clazz = ActionTest28Controller.class;

        try{
            getApplication(new Class[]{clazz});
            Assert.fail();
        }
        catch(Throwable e){
            boolean beanException = false;
            while(e != null){
                if(e.getMessage().startsWith("expected @Bean")){
                   beanException = true;
                   break;
                }
                e = e.getCause();
            }
            
            //if(!beanException)
            //    throw e;
        }
    }

    public void testAction29() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest29Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest29controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = controller
                .getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertNull(action.getMethod());
        Assert.assertEquals("/my.jsp",action.getView());
        Assert.assertNull(action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(0,action.getParameters().size());
    }

    public void testAction30() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest30Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest30controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = controller
                .getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertNull(action.getMethod());
        Assert.assertEquals("/index.jsp",action.getView());
        Assert.assertNull(action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(0,action.getParameters().size());
    }

    public void testAction31() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest31Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals("ActionTest31Controller", controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest31controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = controller
                .getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertNull(action.getMethod());
        Assert.assertEquals("/my.jsp",action.getView());
        Assert.assertNull(action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(0,action.getParameters().size());

        action = controller
                .getActionByName("/my2");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my2",action.getName());
        Assert.assertEquals("/my2",action.getId());
        Assert.assertNull(action.getMethod());
        Assert.assertEquals("/my2.jsp",action.getView());
        Assert.assertNull(action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(0,action.getParameters().size());
        
    }

    public void testAction32() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest32Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals("ActionTest32Controller", controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest32controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = controller
                .getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertNull(action.getMethod());
        Assert.assertEquals("/my.jsp",action.getView());
        Assert.assertNull(action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(0,action.getParameters().size());
    }

    public void testAction33() throws NoSuchMethodException, Throwable{
        
        Class<?> clazz = ActionTest33Controller.class;

        try{
            getApplication(new Class[]{clazz});
            Assert.fail();
        }
        catch(Throwable e){
            boolean expectedException = false;
            while(e != null){
                if(e.getMessage().startsWith("view must be informed")){
                   expectedException = true;
                   break;
                }
                e = e.getCause();
            }
            
            if(!expectedException)
                throw e;
        }
        
    }

    public void testAction34() throws NoSuchMethodException{
        
        Class<?> clazz = ActionTest34Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(), controller.getName());
        Assert.assertEquals("/"+clazz.getSimpleName().replaceAll("Controller$",""),controller.getId());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/views/actiontest34controller/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "") + "/";
        org.brandao.brutos.mapping.Action action = controller
                .getActionByName("/my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my",action.getName());
        Assert.assertEquals("/my",action.getId());
        Assert.assertNull(action.getMethod());
        Assert.assertEquals("/my.jsp",action.getView());
        Assert.assertNull(action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(0,action.getParameters().size());

        action = controller
                .getActionByName("/my2");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/my2",action.getName());
        Assert.assertEquals("/my2",action.getId());
        Assert.assertNull(action.getMethod());
        Assert.assertEquals("/my2.jsp",action.getView());
        Assert.assertNull(action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(0,action.getParameters().size());
        
    }
    
}
