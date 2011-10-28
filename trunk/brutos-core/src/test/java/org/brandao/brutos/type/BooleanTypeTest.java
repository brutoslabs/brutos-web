/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
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
        Object val = type.getValue(test1);
        TestCase.assertEquals(expected1, val);
    }

    public void test2(){
        Object val = type.getValue(test2);
        TestCase.assertEquals(expected2, val);
    }

    public void test3(){
        Object val = type.getValue(test3);
        TestCase.assertEquals(expected3, val);
    }

    public void test4(){
        Object val = type.getValue(test4);
        TestCase.assertEquals(expected4, val);
    }

    public void test5(){
        try{
            type.getValue(invalidType);
            TestCase.fail("expected UnknownTypeException");
        }
        catch( UnknownTypeException e ){
        }
    }

    public void test6(){
        Object val = type.getValue(null);
        TestCase.assertNull(val);
    }

}
