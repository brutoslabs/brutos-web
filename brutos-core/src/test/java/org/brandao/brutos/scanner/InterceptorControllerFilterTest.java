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

package org.brandao.brutos.scanner;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 *
 * @author Brandao
 */
public class InterceptorControllerFilterTest extends TestCase{
    
    private InterceptorFilter filter = new InterceptorFilter();
    
    public void test1(){
        Assert.assertNull(filter.accepts("test/InterceptorController.class"));
    }
    
    public void test2(){
        Assert.assertNull(filter.accepts("InterceptorController.class"));
    }

    public void test3(){
        Assert.assertNull(filter.accepts("InterceptorController.java"));
    }

    public void test4(){
        Assert.assertNull(filter.accepts("tes1/test2/InterceptorController.class"));
    }

    public void test5(){
        Assert.assertNull(filter.accepts("tes1/test2/InterceptorController.java"));
    }

    public void test6(){
        Assert.assertTrue(filter.accepts("test/AInterceptorController.class").booleanValue());
    }
    
    public void test7(){
        Assert.assertTrue(filter.accepts("AInterceptorController.class").booleanValue());
    }

    public void test8(){
        Assert.assertNull(filter.accepts("AInterceptorController.java"));
    }

    public void test9(){
        Assert.assertTrue(filter.accepts("tes1/test2/AInterceptorController.class").booleanValue());
    }

    public void test10(){
        Assert.assertNull(filter.accepts("tes1/test2/AInterceptorController.java"));
    }

    public void test11(){
        Assert.assertTrue(filter.accepts("test/TestInterceptorController.class").booleanValue());
    }
    
    public void test12(){
        Assert.assertTrue(filter.accepts("TestInterceptorController.class").booleanValue());
    }

    public void test13(){
        Assert.assertNull(filter.accepts("TestInterceptorController.java"));
    }

    public void test14(){
        Assert.assertTrue(filter.accepts("tes1/test2/TestInterceptorController.class").booleanValue());
    }

    public void test15(){
        Assert.assertNull(filter.accepts("tes1/test2/TestInterceptorController.java"));
    }
    
}
