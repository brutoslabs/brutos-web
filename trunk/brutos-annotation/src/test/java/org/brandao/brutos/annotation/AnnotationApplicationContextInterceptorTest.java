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

import java.util.List;
import junit.framework.Assert;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.helper.InterceptorTest1Controller;
import org.brandao.brutos.annotation.helper.InterceptorTest2Controller;
import org.brandao.brutos.annotation.helper.InterceptorTest3Controller;
import org.brandao.brutos.annotation.helper.InterceptorTest4Controller;
import org.brandao.brutos.annotation.helper.InterceptorTest5Controller;
import org.brandao.brutos.annotation.helper.interceptor.*;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.InterceptorStack;

/**
 *
 * @author Brandao
 */
public class AnnotationApplicationContextInterceptorTest 
    extends AbstractWebAnnotationApplicationContextTest{

    public void test1(){
        
        Class<?> clazz = Test1InterceptorController.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Interceptor interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor(clazz);
        
        Assert.assertEquals("test1", interceptor.getName());
        Assert.assertEquals(clazz, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(0,interceptor.getProperties().size());
        Assert.assertEquals(false,interceptor.isDefault());
    }
    
    public void test2(){
        
        Class<?> clazz = Test2Interceptor.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Interceptor interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor(clazz);
        
        Assert.assertEquals("test2Interceptor", interceptor.getName());
        Assert.assertEquals(clazz, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(0,interceptor.getProperties().size());
        Assert.assertEquals(true,interceptor.isDefault());
    }

    public void test3(){
        
        Class<?> clazz = Test3Interceptor.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Interceptor interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor(clazz);
        
        Assert.assertEquals("testInterceptor", interceptor.getName());
        Assert.assertEquals(clazz, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(0,interceptor.getProperties().size());
        Assert.assertEquals(false,interceptor.isDefault());
    }

    public void test4(){
        
        Class<?> clazz = Test4Interceptor.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Interceptor interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor(clazz);
        
        Assert.assertEquals("test4Interceptor", interceptor.getName());
        Assert.assertEquals(clazz, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(0,interceptor.getProperties().size());
        Assert.assertEquals(false,interceptor.isDefault());
    }

    public void test5(){
        
        Class<?> clazz = Test5Interceptor.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Interceptor interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor(clazz);
        
        Assert.assertEquals("test5Interceptor", interceptor.getName());
        Assert.assertEquals(clazz, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(true,interceptor.isDefault());
        
        Assert.assertEquals(2,interceptor.getProperties().size());
        Assert.assertEquals("value1",interceptor.getProperties().get("param1"));
        Assert.assertEquals("value2",interceptor.getProperties().get("param2"));
        
    }

    public void test6(){
        
        Class<?> clazz = InterceptorTest1Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz,Test1InterceptorController.class});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        List<Interceptor> interceptors = controller.getInterceptors();
        
        Assert.assertEquals(1, interceptors.size());
        
        Interceptor interceptor = interceptors.get(0);
        
        Assert.assertEquals("test1", interceptor.getName());
        Assert.assertEquals(Test1InterceptorController.class, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(0,interceptor.getProperties().size());
        Assert.assertEquals(false,interceptor.isDefault());
    }

    public void test7(){
        
        Class<?> clazz = InterceptorTest2Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz,Test3Interceptor.class});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        List<Interceptor> interceptors = controller.getInterceptors();
        
        Assert.assertEquals(1, interceptors.size());
        
        Interceptor interceptor = interceptors.get(0);
        
        Assert.assertEquals("testInterceptor", interceptor.getName());
        Assert.assertEquals(Test3Interceptor.class, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(0,interceptor.getProperties().size());
        Assert.assertEquals(false,interceptor.isDefault());
    }

    public void test8(){
        
        Class<?> clazz = InterceptorTest3Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz,Test3Interceptor.class});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        List<Interceptor> interceptors = controller.getInterceptors();
        
        Assert.assertEquals(1, interceptors.size());
        
        Interceptor interceptor = interceptors.get(0);
        
        Assert.assertEquals("testInterceptor", interceptor.getName());
        Assert.assertEquals(Test3Interceptor.class, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(2,interceptor.getProperties().size());
        
        Assert.assertEquals("value1",interceptor.getProperties().get("param1"));
        Assert.assertEquals("value2",interceptor.getProperties().get("param2"));
        
    }

    public void test9(){
        
        Class<?> clazz = InterceptorTest4Controller.class;
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(
                    new Class[]{
                        clazz,
                        Test3Interceptor.class,
                        Test1InterceptorController.class});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        List<Interceptor> interceptors = controller.getInterceptors();
        
        Assert.assertEquals(2, interceptors.size());
        
        Interceptor interceptor = interceptors.get(0);
        
        Assert.assertEquals("testInterceptor", interceptor.getName());
        Assert.assertEquals(Test3Interceptor.class, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(2,interceptor.getProperties().size());
        Assert.assertEquals("value1",interceptor.getProperties().get("param1"));
        Assert.assertEquals("value2",interceptor.getProperties().get("param2"));

        interceptor = interceptors.get(1);
        
        Assert.assertEquals("test1", interceptor.getName());
        Assert.assertEquals(Test1InterceptorController.class, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(0,interceptor.getProperties().size());
    }

    public void test10(){
        
        Class<?> clazz = Test6InterceptorController.class;
        
        try{
            getApplication(
                new Class[]{
            		InterceptorTest5Controller.class,
                    clazz,
                    Test3Interceptor.class,
                    Test1InterceptorController.class});
            Assert.fail("expected: {interceptor not found: org.brandao.brutos.annotation.helper.interceptor.Test6InterceptorController}");
        }
        catch(BrutosException e){
            Assert.assertNotNull(e);
            Throwable ex = e;
            do{
                if(ex.getMessage().equals("interceptor not found: org.brandao.brutos.annotation.helper.interceptor.Test6InterceptorController"))
                    return;
            }while((ex = ex.getCause()) != null);
            e.printStackTrace();
            
            Assert.fail("expected: {interceptor not found: org.brandao.brutos.annotation.helper.interceptor.Test6InterceptorController}");
        }
    }

    public void testInterceptorstack1(){
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(
                    new Class[]{
                        org.brandao.brutos.annotation.helper.interceptor.stacka.InterceptorAInterceptorController.class,
                        org.brandao.brutos.annotation.helper.interceptor.stacka.InterceptorB.class,
                        org.brandao.brutos.annotation.helper.interceptor.stacka.InterceptorC.class
                });
        
        org.brandao.brutos.mapping.Interceptor interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor("stackA");
     
        Assert.assertEquals("stackA", interceptor.getName());
        Assert.assertTrue(interceptor instanceof InterceptorStack);
        
        InterceptorStack stack = (InterceptorStack)interceptor;
        List<Interceptor> list = stack.getInterceptors();
        Assert.assertEquals(list.size(),3);
        Assert.assertEquals(
                org.brandao.brutos.annotation.helper.interceptor.stacka.InterceptorAInterceptorController.class,
                list.get(0).getType());
        Assert.assertEquals(
                org.brandao.brutos.annotation.helper.interceptor.stacka.InterceptorB.class,
                list.get(1).getType());
        Assert.assertEquals(
                org.brandao.brutos.annotation.helper.interceptor.stacka.InterceptorC.class,
                list.get(2).getType());
        
    }

    public void testInterceptorstack2(){
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(
                    new Class[]{
                        org.brandao.brutos.annotation.helper.interceptor.stackb.InterceptorAInterceptorController.class,
                        org.brandao.brutos.annotation.helper.interceptor.stackb.InterceptorB.class,
                        org.brandao.brutos.annotation.helper.interceptor.stackb.InterceptorC.class
                });
        
        org.brandao.brutos.mapping.Interceptor interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor("stackB");
     
        Assert.assertEquals("stackB", interceptor.getName());
        Assert.assertTrue(interceptor instanceof InterceptorStack);
        
        InterceptorStack stack = (InterceptorStack)interceptor;
        List<Interceptor> list = stack.getInterceptors();
        Assert.assertEquals(list.size(),3);
        Assert.assertEquals(
                org.brandao.brutos.annotation.helper.interceptor.stackb.InterceptorAInterceptorController.class,
                list.get(0).getType());
        Assert.assertEquals(
                org.brandao.brutos.annotation.helper.interceptor.stackb.InterceptorB.class,
                list.get(1).getType());
        
        Assert.assertNotNull(stack.getProperties());
        Assert.assertEquals(2,stack.getProperties().size());
        Assert.assertEquals("value1",stack.getProperties().get("interceptorB.param1"));
        Assert.assertEquals("value2",stack.getProperties().get("interceptorB.param2"));
        
        Assert.assertEquals(
                org.brandao.brutos.annotation.helper.interceptor.stackb.InterceptorC.class,
                list.get(2).getType());
        
    }

    public void testInterceptorstack3(){
        
        ConfigurableApplicationContext annotationApplicationContext = 
                getApplication(
                    new Class[]{
                        org.brandao.brutos.annotation.helper.interceptor.stackc.InterceptorAInterceptorController.class,
                        org.brandao.brutos.annotation.helper.interceptor.stackc.InterceptorB.class,
                        org.brandao.brutos.annotation.helper.interceptor.stackc.InterceptorC.class
                });

        org.brandao.brutos.mapping.Interceptor interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor("stackA");
     
        Assert.assertEquals("stackA", interceptor.getName());
        Assert.assertTrue(interceptor instanceof InterceptorStack);
        
        InterceptorStack stack = (InterceptorStack)interceptor;
        List<Interceptor> list = stack.getInterceptors();
        Assert.assertEquals(list.size(),3);
        Assert.assertEquals(
                org.brandao.brutos.annotation.helper.interceptor.stackc.InterceptorAInterceptorController.class,
                list.get(0).getType());
        Assert.assertEquals(
                org.brandao.brutos.annotation.helper.interceptor.stackc.InterceptorB.class,
                list.get(1).getType());
        Assert.assertEquals(
                org.brandao.brutos.annotation.helper.interceptor.stackc.InterceptorC.class,
                list.get(2).getType());
        
        interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor("stackB");
     
        Assert.assertEquals("stackB", interceptor.getName());
        Assert.assertTrue(interceptor instanceof InterceptorStack);
        
        stack = (InterceptorStack)interceptor;
        list = stack.getInterceptors();
        Assert.assertEquals(list.size(),3);
        Assert.assertEquals(
                org.brandao.brutos.annotation.helper.interceptor.stackc.InterceptorAInterceptorController.class,
                list.get(0).getType());
        Assert.assertEquals(
                org.brandao.brutos.annotation.helper.interceptor.stackc.InterceptorB.class,
                list.get(1).getType());
        
        Assert.assertNotNull(stack.getProperties());
        Assert.assertEquals(2,stack.getProperties().size());
        Assert.assertEquals("value1",stack.getProperties().get("interceptorB.param1"));
        Assert.assertEquals("value2",stack.getProperties().get("interceptorB.param2"));
        
        Assert.assertEquals(
                org.brandao.brutos.annotation.helper.interceptor.stackc.InterceptorC.class,
                list.get(2).getType());
        
    }

    public void testInterceptorstack4(){
        
        try{
            getApplication(
                new Class[]{
                    org.brandao.brutos.annotation.helper.interceptor.stackd.InterceptorAInterceptorController.class,
                    org.brandao.brutos.annotation.helper.interceptor.stackd.InterceptorB.class,
                    org.brandao.brutos.annotation.helper.interceptor.stackd.InterceptorC.class
            });
            Assert.fail();
        }
        catch(BrutosException e){
            if(!e.getMessage().startsWith("does not compose the interceptor stack"))
                Assert.fail();
        }
        
    }

    public void testInterceptorstack5(){
        
        try{
            getApplication(
                new Class[]{
                    org.brandao.brutos.annotation.helper.interceptor.stacke.InterceptorAInterceptorController.class,
                    org.brandao.brutos.annotation.helper.interceptor.stacke.InterceptorB.class,
                    org.brandao.brutos.annotation.helper.interceptor.stacke.InterceptorC.class
            });
            Assert.fail();
        }
        catch(BrutosException e){
            if(!e.getMessage().startsWith("does not compose the interceptor stack"))
                Assert.fail();
        }
        
    }

    public void testInterceptorstack6(){
        
        try{
            getApplication(
                new Class[]{
                    org.brandao.brutos.annotation.helper.interceptor.stackf.InterceptorAInterceptorController.class,
                    org.brandao.brutos.annotation.helper.interceptor.stackf.InterceptorB.class,
                    org.brandao.brutos.annotation.helper.interceptor.stackf.InterceptorC.class
            });
            Assert.fail();
        }
        catch(BrutosException e){
            if(!e.getMessage().startsWith("first interceptor not found"))
                Assert.fail();
        }
        
    }
    /*
    public void testInterceptorstack7(){
        
        try{
            getApplication(
                new Class[]{
                    org.brandao.brutos.annotation.helper.interceptor.stackg.InterceptorAInterceptorController.class,
                    org.brandao.brutos.annotation.helper.interceptor.stackg.InterceptorB.class,
                    org.brandao.brutos.annotation.helper.interceptor.stackg.InterceptorC.class
            });
            Assert.fail();
        }
        catch(BrutosException e){
            if(!e.getMessage().startsWith("detected circular reference in interceptor stack"))
                Assert.fail();
        }
        
    }
    */    
}
