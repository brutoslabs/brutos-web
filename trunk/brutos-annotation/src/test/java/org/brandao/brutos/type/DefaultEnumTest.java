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
        EnumTest e = (EnumTest) type.convert("0");
        TestCase.assertEquals(EnumTest.VALUE1, e);
    }

    public void test2(){
        DefaultEnumType type = new DefaultEnumType();
        type.setClassType(EnumTest.class);
        type.setEnumType(EnumerationType.ORDINAL);
        EnumTest e = (EnumTest) type.convert("1");
        TestCase.assertEquals(EnumTest.VALUE2, e);
    }

    public void test3(){
        DefaultEnumType type = new DefaultEnumType();
        type.setClassType(EnumTest.class);
        type.setEnumType(EnumerationType.STRING);
        EnumTest e = (EnumTest) type.convert("VALUE1");
        TestCase.assertEquals(EnumTest.VALUE1, e);
    }

    public void test4(){
        DefaultEnumType type = new DefaultEnumType();
        type.setClassType(EnumTest.class);
        type.setEnumType(EnumerationType.STRING);
        EnumTest e = (EnumTest) type.convert("VALUE2");
        TestCase.assertEquals(EnumTest.VALUE2, e);
    }

    public void test5(){
        DefaultEnumType type = new DefaultEnumType();
        type.setClassType(EnumTest.class);
        type.setEnumType(EnumerationType.STRING);
        EnumTest e = (EnumTest) type.convert(EnumTest.VALUE1);
        TestCase.assertEquals(EnumTest.VALUE1, e);
    }

    public void test6(){
        DefaultEnumType type = new DefaultEnumType();
        type.setClassType(EnumTest.class);
        type.setEnumType(EnumerationType.STRING);
        try{
            type.convert(new DefaultEnumType());
            TestCase.fail("expected UnknownTypeException");
        }
        catch( Exception e ){
            if(!(e instanceof UnknownTypeException))
                TestCase.fail("expected UnknownTypeException");
        }

    }

}
