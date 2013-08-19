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
import org.brandao.brutos.web.http.BrutosFile;

/**
 *
 * @author Brandao
 */
public class BrutosFileTypeTest extends TestCase{

    private Type type = new BrutosFileType();

    private Object expected1 = null;//new BrutosFile(null);
    private Object test1     = expected1;

    private Object invalidType = new Integer(1);

    public void test1(){
        Object val = type.convert(test1);
        TestCase.assertEquals(expected1, val);
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
