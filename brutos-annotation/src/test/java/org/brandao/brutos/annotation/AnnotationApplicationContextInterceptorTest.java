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
import org.brandao.brutos.annotation.helper.InterceptorTest1Controller;
import org.brandao.brutos.annotation.helper.InterceptorTest2Controller;
import org.brandao.brutos.annotation.helper.InterceptorTest3Controller;
import org.brandao.brutos.annotation.helper.InterceptorTest4Controller;
import org.brandao.brutos.annotation.helper.interceptor.*;
import org.brandao.brutos.mapping.Interceptor;

/**
 *
 * @author Brandao
 */
public class AnnotationApplicationContextInterceptorTest 
    extends AbstractApplicationContextTest{

    public void test1(){
        
        Class clazz = Test1InterceptorController.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Interceptor interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor(clazz);
        
        Assert.assertEquals("Test1", interceptor.getName());
        Assert.assertEquals(clazz, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(0,interceptor.getProperties().size());
        Assert.assertEquals(true,interceptor.isDefault());
    }
    
    public void test2(){
        
        Class clazz = Test2Interceptor.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Interceptor interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor(clazz);
        
        Assert.assertEquals("Test2Interceptor", interceptor.getName());
        Assert.assertEquals(clazz, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(0,interceptor.getProperties().size());
        Assert.assertEquals(true,interceptor.isDefault());
    }

    public void test3(){
        
        Class clazz = Test3Interceptor.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Interceptor interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor(clazz);
        
        Assert.assertEquals("testInterceptor", interceptor.getName());
        Assert.assertEquals(clazz, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(0,interceptor.getProperties().size());
        Assert.assertEquals(true,interceptor.isDefault());
    }

    public void test4(){
        
        Class clazz = Test4Interceptor.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Interceptor interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor(clazz);
        
        Assert.assertEquals("Test4Interceptor", interceptor.getName());
        Assert.assertEquals(clazz, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(0,interceptor.getProperties().size());
        Assert.assertEquals(false,interceptor.isDefault());
    }

    public void test5(){
        
        Class clazz = Test5Interceptor.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz});
        
        org.brandao.brutos.mapping.Interceptor interceptor = 
                annotationApplicationContext
                    .getInterceptorManager().getInterceptor(clazz);
        
        Assert.assertEquals("Test5Interceptor", interceptor.getName());
        Assert.assertEquals(clazz, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(true,interceptor.isDefault());
        
        Assert.assertEquals(2,interceptor.getProperties().size());
        Assert.assertEquals("value1",interceptor.getProperties().get("param1"));
        Assert.assertEquals("value2",interceptor.getProperties().get("param2"));
        
    }

    public void test6(){
        
        Class clazz = InterceptorTest1Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(new Class[]{clazz,Test1InterceptorController.class});
        
        org.brandao.brutos.mapping.Controller controller = 
                annotationApplicationContext
                    .getControllerManager().getController(clazz);
        
        List<Interceptor> interceptors = controller.getInterceptors();
        
        Assert.assertEquals(1, interceptors.size());
        
        Interceptor interceptor = interceptors.get(0);
        
        Assert.assertEquals("Test1", interceptor.getName());
        Assert.assertEquals(Test1InterceptorController.class, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(0,interceptor.getProperties().size());
        Assert.assertEquals(true,interceptor.isDefault());
    }

    public void test7(){
        
        Class clazz = InterceptorTest2Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
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
        Assert.assertEquals(true,interceptor.isDefault());
    }

    public void test8(){
        
        Class clazz = InterceptorTest3Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
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
        
        Class clazz = InterceptorTest4Controller.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
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
        
        Assert.assertEquals("Test1", interceptor.getName());
        Assert.assertEquals(Test1InterceptorController.class, interceptor.getType());
        Assert.assertNotNull(interceptor.getProperties());
        Assert.assertEquals(0,interceptor.getProperties().size());
    }

    public void test10(){
        
        Class clazz = Test6InterceptorController.class;
        
        AnnotationApplicationContext annotationApplicationContext = 
                getApplication(
                    new Class[]{
                        clazz,
                        Test3Interceptor.class,
                        Test1InterceptorController.class});
        
        try{
            annotationApplicationContext
                .getInterceptorManager().getInterceptor(clazz);
            Assert.fail();
        }
        catch(BrutosException e){
            if(!e.getMessage().startsWith("interceptor not found"))
                Assert.fail();
        }
    }
    
}
