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

import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Brandao
 */
public class TypeTest  extends TestCase{

    public void testGetClassType(){
        Class type = Types.getRawType(int.class);
        TestCase.assertEquals(int.class,type);
    }

    public void testgetRawType(){
        GenericTypeImp genericType =
                new GenericTypeImp(List.class,new Class[]{Integer.class});
        Class type = Types.getRawType(genericType);
        TestCase.assertEquals(List.class, type);
    }

    public void testgetListTypeWithError(){
        try{
            ListType type = (ListType) Types.getType(List.class);
            type.setGenericType(List.class);
            TestCase.fail("expected UnknownTypeException");
        }
        catch( UnknownTypeException e ){
        }
    }

    public void testgetListType(){
        GenericTypeImp genericType =
                new GenericTypeImp(List.class,new Class[]{Integer.class});
        ListType type = (ListType) Types.getType(genericType);
        type.setGenericType(genericType);

        TestCase.assertEquals(Integer.class, type.getGenericType());
    }

}
