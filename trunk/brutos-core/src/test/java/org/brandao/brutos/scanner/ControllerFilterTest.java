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
public class ControllerFilterTest extends TestCase{
    
    private ControllerFilter filter = new ControllerFilter();
    
    public void test1(){
        Assert.assertNull(filter.accepts("test/Controller.class"));
    }
    
    public void test2(){
        Assert.assertNull(filter.accepts("Controller.class"));
    }

    public void test3(){
        Assert.assertNull(filter.accepts("Controller.java"));
    }

    public void test4(){
        Assert.assertNull(filter.accepts("tes1/test2/Controller.class"));
    }

    public void test5(){
        Assert.assertNull(filter.accepts("tes1/test2/Controller.java"));
    }

    public void test6(){
        Assert.assertTrue(filter.accepts("test/AController.class").booleanValue());
    }
    
    public void test7(){
        Assert.assertTrue(filter.accepts("AController.class").booleanValue());
    }

    public void test8(){
        Assert.assertNull(filter.accepts("AController.java"));
    }

    public void test9(){
        Assert.assertTrue(filter.accepts("tes1/test2/AController.class").booleanValue());
    }

    public void test10(){
        Assert.assertNull(filter.accepts("tes1/test2/AController.java"));
    }

    public void test11(){
        Assert.assertTrue(filter.accepts("test/TestController.class").booleanValue());
    }
    
    public void test12(){
        Assert.assertTrue(filter.accepts("TestController.class").booleanValue());
    }

    public void test13(){
        Assert.assertNull(filter.accepts("TestController.java"));
    }

    public void test14(){
        Assert.assertTrue(filter.accepts("tes1/test2/TestController.class").booleanValue());
    }

    public void test15(){
        Assert.assertNull(filter.accepts("tes1/test2/TestController.java"));
    }
    
}
