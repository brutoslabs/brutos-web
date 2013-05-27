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

package org.brandao.brutos.type;

import junit.framework.TestCase;

/**
 *
 * @author Brandao
 */
public class BooleanTypeTest extends TestCase{

    private Type type = new BooleanType();

    private Object expected1 = Boolean.TRUE;
    private Object test1     = "true";

    private Object expected2 = Boolean.FALSE;
    private Object test2     = "false";

    private Object expected3 = Boolean.TRUE;
    private Object test3     = Boolean.TRUE;

    private Object expected4 = Boolean.FALSE;
    private Object test4     = Boolean.FALSE;

    private Object invalidType = new Integer(1);

    public void test1(){
        Object val = type.convert(test1);
        TestCase.assertEquals(expected1, val);
    }

    public void test2(){
        Object val = type.convert(test2);
        TestCase.assertEquals(expected2, val);
    }

    public void test3(){
        Object val = type.convert(test3);
        TestCase.assertEquals(expected3, val);
    }

    public void test4(){
        Object val = type.convert(test4);
        TestCase.assertEquals(expected4, val);
    }

    public void test5(){
        try{
            type.convert(invalidType);
            TestCase.fail("expected UnknownTypeException");
        }
        catch( UnknownTypeException e ){
        }
    }

    public void test6(){
        Object val = type.convert(null);
        TestCase.assertNull(val);
    }

}
