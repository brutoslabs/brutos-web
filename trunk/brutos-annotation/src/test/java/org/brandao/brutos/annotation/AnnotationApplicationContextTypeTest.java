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

import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.helper.MyBean;
import org.brandao.brutos.annotation.helper.TestTypeFactory;
import org.brandao.brutos.annotation.helper.TestType2;
import org.brandao.brutos.annotation.helper.TestType3;
import org.brandao.brutos.mapping.MappingException;

/**
 *
 * @author Brandao
 */
public class AnnotationApplicationContextTypeTest  
    extends AbstractWebAnnotationApplicationContextTest{
    
    public void test1() throws NoSuchMethodException{
        Class clazz = TestTypeFactory.class;
        
        ConfigurableApplicationContext app = null;
        try{
            app = getApplication(new Class[]{clazz});
            Assert.assertTrue(app.getTypeManager().isStandardType(MyBean.class));
        }
        finally{
            if(app != null)
                app.getTypeManager().remove(MyBean.class);
        }
    }

    public void test2() throws NoSuchMethodException{
        Class clazz = TestType2.class;
        
        ConfigurableApplicationContext app = null;
        try{
            app = getApplication(new Class[]{clazz});
            Assert.assertTrue(app.getTypeManager().isStandardType(MyBean.class));
        }
        finally{
            if(app != null)
                app.getTypeManager().remove(TestType2.class);
        }
    }

    public void test3() throws NoSuchMethodException, Throwable{
        Class clazz = TestType3.class;
        
        try{
            getApplication(new Class[]{clazz});
            Assert.fail();
        }
        catch(Throwable e){
        	e.printStackTrace();
            boolean beanException = false;
            while(e != null){
                if(e instanceof MappingException && e.getMessage().equals("must implement TypeFactory: TestType3")){
                   beanException = true;
                   break;
                }
                e = e.getCause();
            }
            
            if(!beanException)
                throw e;
        }
    }
    
}
