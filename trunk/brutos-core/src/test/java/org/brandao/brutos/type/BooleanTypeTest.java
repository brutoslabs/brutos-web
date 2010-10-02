/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.type;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 *
 * @author Neto
 */
public class BooleanTypeTest extends TestCase implements Test{

    public BooleanTypeTest(){
        super();
    }

    public void testGettrueValue(){
        Type type = Types.getType( boolean.class );
        Object value = type.getValue(null, null, "true");
        assertEquals( true, value );
    }

    public void testGetfalseValue(){
        Type type = Types.getType( boolean.class );
        Object value = type.getValue(null, null, "false");
        assertEquals( false, value );
    }

    public void testGetNullValue(){
        Type type = Types.getType( boolean.class );
        Object value = type.getValue(null, null, null);
        assertEquals( false, value );
    }

    public void testGetTrueValue(){
        Type type = Types.getType( Boolean.class );
        Object value = type.getValue(null, null, "true");
        assertEquals( true, value );
    }

    public void testGetFalseValue(){
        Type type = Types.getType( Boolean.class );
        Object value = type.getValue(null, null, "false");
        assertEquals( false, value );
    }

    public void testGetInvalidValue(){
        Type type = Types.getType( Boolean.class );
        Object value = type.getValue(null, null, "fake");
        assertEquals( false, value );
    }

    public void testGetInvalidObject(){
        Type type = Types.getType( Boolean.class );
        Object value = type.getValue(null, null, 1);
        assertEquals( false, value );
    }

    public void testGetTrueObject(){
        Type type = Types.getType( Boolean.class );
        Object value = type.getValue(null, null, true);
        assertEquals( true, value );
    }

    public void testGetFalseObject(){
        Type type = Types.getType( Boolean.class );
        Object value = type.getValue(null, null, false);
        assertEquals( false, value );
    }

}
