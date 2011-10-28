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
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.annotation.helper.EnumTest;

/**
 *
 * @author Brandao
 */
public class DefaultEnumTest extends TestCase{

    public void test1(){
        DefaultEnumType type = new DefaultEnumType();
        type.setClassType(EnumTest.class);
        type.setEnumType(EnumerationType.ORDINAL);
        EnumTest e = (EnumTest) type.getValue("0");
        TestCase.assertEquals(EnumTest.VALUE1, e);
    }

    public void test2(){
        DefaultEnumType type = new DefaultEnumType();
        type.setClassType(EnumTest.class);
        type.setEnumType(EnumerationType.ORDINAL);
        EnumTest e = (EnumTest) type.getValue("1");
        TestCase.assertEquals(EnumTest.VALUE2, e);
    }

    public void test3(){
        DefaultEnumType type = new DefaultEnumType();
        type.setClassType(EnumTest.class);
        type.setEnumType(EnumerationType.STRING);
        EnumTest e = (EnumTest) type.getValue("VALUE1");
        TestCase.assertEquals(EnumTest.VALUE1, e);
    }

    public void test4(){
        DefaultEnumType type = new DefaultEnumType();
        type.setClassType(EnumTest.class);
        type.setEnumType(EnumerationType.STRING);
        EnumTest e = (EnumTest) type.getValue("VALUE2");
        TestCase.assertEquals(EnumTest.VALUE2, e);
    }

    public void test5(){
        DefaultEnumType type = new DefaultEnumType();
        type.setClassType(EnumTest.class);
        type.setEnumType(EnumerationType.STRING);
        EnumTest e = (EnumTest) type.getValue(EnumTest.VALUE1);
        TestCase.assertEquals(EnumTest.VALUE1, e);
    }

    public void test6(){
        DefaultEnumType type = new DefaultEnumType();
        type.setClassType(EnumTest.class);
        type.setEnumType(EnumerationType.STRING);
        try{
            type.getValue(new DefaultEnumType());
            TestCase.fail("expected UnknownTypeException");
        }
        catch( UnknownTypeException e ){
        }

    }

}
