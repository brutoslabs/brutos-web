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
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.annotation.helper.*;
import org.brandao.brutos.mapping.ParameterAction;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.type.*;
import org.brandao.brutos.validator.RestrictionRules;
import org.brandao.brutos.validator.Validator;

/**
 *
 * @author Brandao
 */
public class AnnotationApplicationContextActionTest 
    extends AbstractApplicationContextTest{
    
    public void testAction1() throws NoSuchMethodException{
        
        Class clazz = ActionTest1Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest1/index.jsp",controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
            controller.getActionByName( prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix + "my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest1/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction2() throws NoSuchMethodException{
        
        Class clazz = ActionTest2Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest2/index.jsp",controller.getView());
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        Assert.assertNull(action);
    }

    public void testAction3() throws NoSuchMethodException{
        
        Class clazz = ActionTest3Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest3/index.jsp",controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("my"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest3/my/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("my").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction4() throws NoSuchMethodException{
        
        Class clazz = ActionTest4Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest4/index.jsp",controller.getView());
        
        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest4/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction5() throws NoSuchMethodException{
        
        Class clazz = ActionTest5Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest5/index.jsp",controller.getView());
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("/myaction");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/myaction",action.getName());
        Assert.assertEquals(clazz.getMethod("my"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest5/my/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("my").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction6() throws NoSuchMethodException{
        
        Class clazz = ActionTest6Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest6/index.jsp",controller.getView());
        
        org.brandao.brutos.mapping.Action action = controller.getActionByName("/myaction2");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/myaction2",action.getName());
        
        Assert.assertEquals(clazz.getMethod("my"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest6/my/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("my").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        action = controller.getActionByName("/myaction3");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals("/myaction2",action.getName());
        
        Assert.assertEquals(clazz.getMethod("my"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest6/my/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("my").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
    }

    public void testAction7() throws NoSuchMethodException{
        
        Class clazz = ActionTest7Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest7/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        ThrowableSafeData ex = action.getThrowsSafe(RuntimeException.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex.getDispatcher());
        Assert.assertEquals("/WEB-INF/actiontest7/myaction/runtimeexception.jsp",ex.getView());
        Assert.assertEquals(RuntimeException.class,ex.getTarget());

        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest7/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction8() throws NoSuchMethodException{
        
        Class clazz = ActionTest8Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest8/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        ThrowableSafeData ex = action.getThrowsSafe(RuntimeException.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex.getParameterName());
        Assert.assertEquals(DispatcherType.REDIRECT,ex.getDispatcher());
        Assert.assertEquals("/view/exception.jsp",ex.getView());
        Assert.assertEquals(RuntimeException.class,ex.getTarget());

        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest8/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction9() throws NoSuchMethodException{
        
        Class clazz = ActionTest9Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest9/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        ThrowableSafeData ex = action.getThrowsSafe(RuntimeException.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex.getDispatcher());
        Assert.assertEquals("/WEB-INF/actiontest9/myaction/runtimeexception.jsp",ex.getView());
        Assert.assertEquals(RuntimeException.class,ex.getTarget());

        ex = action.getThrowsSafe(Exception.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex.getParameterName());
        Assert.assertEquals(DispatcherType.REDIRECT,ex.getDispatcher());
        Assert.assertEquals("/view/exception.jsp",ex.getView());
        Assert.assertEquals(Exception.class,ex.getTarget());
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest9/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction10() throws NoSuchMethodException{
        
        Class clazz = ActionTest10Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest10/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        ThrowableSafeData ex = action.getThrowsSafe(Exception.class);
        Assert.assertNotNull(ex);
        Assert.assertEquals(BrutosConstants.DEFAULT_EXCEPTION_NAME,ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex.getDispatcher());
        Assert.assertNull(ex.getView());
        Assert.assertEquals(Exception.class,ex.getTarget());

        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest10/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction11() throws NoSuchMethodException{
        
        Class clazz = ActionTest11Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest11/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        ThrowableSafeData ex = action.getThrowsSafe(Exception.class);
        Assert.assertNull(ex);

        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest11/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction12() throws NoSuchMethodException{
        
        Class clazz = ActionTest12Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest12/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        ThrowableSafeData ex = action.getThrowsSafe(Exception.class);
        Assert.assertNotNull(ex);
        Assert.assertNull(ex.getParameterName());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,ex.getDispatcher());
        Assert.assertEquals("/WEB-INF/actiontest12/myaction/exception.jsp",ex.getView());
        Assert.assertEquals(Exception.class,ex.getTarget());

        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest12/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction13() throws NoSuchMethodException{
        
        Class clazz = ActionTest13Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest13/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest13/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction14() throws NoSuchMethodException{
        
        Class clazz = ActionTest14Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest14/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest14/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(Object.class,action.getReturnClass());
        Assert.assertEquals("actionResult",action.getReturnIn());
    }

    public void testAction15() throws NoSuchMethodException{
        
        Class clazz = ActionTest15Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest15/index.jsp",controller.getView());

        org.brandao.brutos.mapping.Action action = controller.getActionByName("my");
        
        Assert.assertNull(action);
    }

    public void testAction16() throws NoSuchMethodException{
        
        Class clazz = ActionTest16Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest16/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/controller/view.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction17() throws NoSuchMethodException{
        
        Class clazz = ActionTest17Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest17/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(DispatcherType.REDIRECT,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertEquals("/controller/view.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction18() throws NoSuchMethodException{
        
        Class clazz = ActionTest18Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest18/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction"),action.getMethod());
        Assert.assertNull(action.getView());
        Assert.assertEquals(clazz.getMethod("myAction").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
    }

    public void testAction19() throws NoSuchMethodException{
        
        Class clazz = ActionTest19Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest19/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("my"),action.getMethod());
        Assert.assertEquals("/controller/view.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("my").getName(),action.getExecutor());
        Assert.assertEquals(0,action.getParameters().size());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals("actionResult",action.getReturnIn());
    }

    public void testAction20() throws NoSuchMethodException{
        
        Class clazz = ActionTest20Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest20/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction",int.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest20/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",int.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(1,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getBean().getNome());
        Assert.assertEquals(int.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(IntegerType.class, parameter.getBean().getType().getClass());
        Assert.assertNotNull(parameter.getBean().getValidate());
    }

    public void testAction21() throws NoSuchMethodException{
        
        Class clazz = ActionTest21Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest21/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction",Integer.class,String.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest21/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",Integer.class,String.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(2,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getBean().getNome());
        Assert.assertEquals(Integer.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(IntegerWrapperType.class, parameter.getBean().getType().getClass());
        Assert.assertNotNull(parameter.getBean().getValidate());
        
        parameter = action.getParameter(1);
        
        Assert.assertEquals("arg1", parameter.getBean().getNome());
        Assert.assertEquals(String.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(StringType.class, parameter.getBean().getType().getClass());
        Assert.assertNotNull(parameter.getBean().getValidate());
        
    }

    public void testAction22() throws NoSuchMethodException{
        
        Class clazz = ActionTest22Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest22/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction",Integer.class,String.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest22/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",Integer.class,String.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(2,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("param1", parameter.getBean().getNome());
        Assert.assertEquals(Integer.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.REQUEST, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(IntegerWrapperType.class, parameter.getBean().getType().getClass());
        Assert.assertNotNull(parameter.getBean().getValidate());
        
        parameter = action.getParameter(1);
        
        Assert.assertEquals("arg1", parameter.getBean().getNome());
        Assert.assertEquals(String.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.REQUEST, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(StringType.class, parameter.getBean().getType().getClass());
        Assert.assertNotNull(parameter.getBean().getValidate());
        
    }

    public void testAction23() throws NoSuchMethodException{
        
        Class clazz = ActionTest23Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest23/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction",EnumTest.class,EnumTest.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest23/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",EnumTest.class,EnumTest.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(2,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getBean().getNome());
        Assert.assertEquals(EnumTest.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.STRING, ((DefaultEnumType)parameter.getBean().getType()).getEnumType());
        Assert.assertNotNull(parameter.getBean().getValidate());
        
        parameter = action.getParameter(1);
        
        Assert.assertEquals("arg1", parameter.getBean().getNome());
        Assert.assertEquals(EnumTest.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(org.brandao.brutos.EnumerationType.ORDINAL, ((DefaultEnumType)parameter.getBean().getType()).getEnumType());
        Assert.assertNotNull(parameter.getBean().getValidate());
        
    }

    public void testAction24() throws NoSuchMethodException{
        
        Class clazz = ActionTest24Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest24/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction",String.class,int.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest24/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",String.class,int.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(2,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getBean().getNome());
        Assert.assertEquals(String.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(StringType.class, parameter.getBean().getType().getClass());
        Assert.assertNotNull(parameter.getBean().getValidate());
        
        Validator validator = parameter.getBean().getValidate();
        Properties config = validator.getConfiguration();
                
        Assert.assertEquals("true", config.get(RestrictionRules.REQUIRED.toString()));
        
        parameter = action.getParameter(1);
        
        Assert.assertEquals("arg1", parameter.getBean().getNome());
        Assert.assertEquals(int.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(IntegerType.class, parameter.getBean().getType().getClass());
        Assert.assertNotNull(parameter.getBean().getValidate());

        validator = parameter.getBean().getValidate();
        config = validator.getConfiguration();
                
        Assert.assertEquals("true", config.get(RestrictionRules.REQUIRED.toString()));
        Assert.assertEquals("10", config.get(RestrictionRules.MIN.toString()));
        Assert.assertEquals("100", config.get(RestrictionRules.MAX.toString()));
        
    }

    public void testAction25() throws NoSuchMethodException{
        
        Class clazz = ActionTest25Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest25/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction",String.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest25/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",String.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(1,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getBean().getNome());
        Assert.assertEquals(String.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(StringType.class, parameter.getBean().getType().getClass());
        Assert.assertNotNull(parameter.getBean().getValidate());
        
        Validator validator = parameter.getBean().getValidate();
        Properties config = validator.getConfiguration();
                
        Assert.assertEquals("true", config.get(RestrictionRules.REQUIRED.toString()));
        
    }

    public void testAction26() throws NoSuchMethodException{
        
        Class clazz = ActionTest26Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest26/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = 
                controller.getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction",
                Date.class,Date.class,Calendar.class,Calendar.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest26/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",
                Date.class,Date.class,Calendar.class,Calendar.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(4,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getBean().getNome());
        Assert.assertEquals(Date.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(DefaultDateTimeType.class, parameter.getBean().getType().getClass());
        Assert.assertNotNull(parameter.getBean().getValidate());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY,((DateTimeType)parameter.getBean().getType()).getMask());

        parameter = action.getParameter(1);
        
        Assert.assertEquals("arg1", parameter.getBean().getNome());
        Assert.assertEquals(Date.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(DefaultDateTimeType.class, parameter.getBean().getType().getClass());
        Assert.assertNotNull(parameter.getBean().getValidate());
        Assert.assertEquals("MM/dd/yyyy",((DateTimeType)parameter.getBean().getType()).getMask());

        parameter = action.getParameter(2);
        
        Assert.assertEquals("arg2", parameter.getBean().getNome());
        Assert.assertEquals(Calendar.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(CalendarType.class, parameter.getBean().getType().getClass());
        Assert.assertNotNull(parameter.getBean().getValidate());
        Assert.assertEquals(BrutosConstants.DEFAULT_TEMPORALPROPERTY,((DateTimeType)parameter.getBean().getType()).getMask());

        parameter = action.getParameter(3);
        
        Assert.assertEquals("arg3", parameter.getBean().getNome());
        Assert.assertEquals(Calendar.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(CalendarType.class, parameter.getBean().getType().getClass());
        Assert.assertNotNull(parameter.getBean().getValidate());
        Assert.assertEquals("yyyy-MM-dd",((DateTimeType)parameter.getBean().getType()).getMask());
        
    }

    public void testAction27() throws NoSuchMethodException{
        
        Class clazz = ActionTest27Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        Assert.assertNotNull(controller.getId());
        
        Assert.assertNotNull(controller.getActionListener());
        Assert.assertNull(controller.getDefaultAction());
        Assert.assertEquals(clazz.getSimpleName(),controller.getName());
        Assert.assertEquals(0,controller.getAlias().size());
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,controller.getDispatcherType());
        Assert.assertEquals(BrutosConstants.DEFAULT_ACTION_ID,controller.getActionId());
        Assert.assertEquals("/WEB-INF/actiontest27/index.jsp",controller.getView());

        String prefix = 
            "/" + clazz.getSimpleName().replaceAll("Controller$", "").toLowerCase() + "/";
        org.brandao.brutos.mapping.Action action = controller
                .getActionByName(prefix + "my");
        
        Assert.assertEquals(BrutosConstants.DEFAULT_DISPATCHERTYPE,action.getDispatcherType());
        Assert.assertEquals(prefix+"my",action.getName());
        Assert.assertEquals(clazz.getMethod("myAction",int.class),action.getMethod());
        Assert.assertEquals("/WEB-INF/actiontest27/myaction/index.jsp",action.getView());
        Assert.assertEquals(clazz.getMethod("myAction",int.class).getName(),action.getExecutor());
        Assert.assertEquals(void.class,action.getReturnClass());
        Assert.assertEquals(BrutosConstants.DEFAULT_RETURN_NAME,action.getReturnIn());
        
        Assert.assertEquals(1,action.getParameters().size());
        
        ParameterAction parameter = action.getParameter(0);
        
        Assert.assertEquals("arg0", parameter.getBean().getNome());
        Assert.assertEquals(int.class, parameter.getBean().getClassType());
        Assert.assertNull(parameter.getBean().getMapping());
        Assert.assertEquals(ScopeType.PARAM, parameter.getBean().getScopeType());
        Assert.assertNull(parameter.getBean().getStaticValue());
        Assert.assertEquals(TestType4.class, parameter.getBean().getType().getClass());
        Assert.assertNotNull(parameter.getBean().getValidate());
    }

    public void testAction28() throws NoSuchMethodException{

        Class clazz = ActionTest28Controller.class;

        try{
            getApplication(new Class[]{clazz});
            Assert.fail();
        }
        catch(BrutosException e){
            if(!e.getMessage().startsWith("expected @Bean"))
                Assert.fail();
        }
    }
    
}
