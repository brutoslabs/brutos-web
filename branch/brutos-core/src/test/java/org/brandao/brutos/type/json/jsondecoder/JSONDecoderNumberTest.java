/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandão. (afonso.rbn@gmail.com)
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


package org.brandao.brutos.type.json.jsondecoder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.type.json.JSONDecoder;
import org.brandao.brutos.type.json.JSONException;

/**
 *
 * @author Afonso Brandao
 */
public class JSONDecoderNumberTest extends TestCase implements Test{

    public JSONDecoderNumberTest(){
        super();
    }

    public void testNumberWithoutFirstDisgit() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( ".100" );
            jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testNumberWithoutDecimalDigit() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "1." );
            jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testNumberWithoutDecimalDigitE() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "0.e" );
            jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testNumberDecimalDigitEMore() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "0.e+" );
            jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testNumberDecimalDigitESub() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "0.e-" );
            jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testNumberDecimalDigitEMoreDigit() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "0.e+10" );
            jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testNumberDecimalDigitESubDigit() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "0.e-10" );
            jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testNumberDecimalEDigit() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "0.e10" );
            jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }



    public void testNumberEMore() throws IOException{
        JSONDecoder jse = new JSONDecoder( "01111e10" );
        jse.decode();
    }

    public void testNumberE() throws IOException{
        JSONDecoder jse = new JSONDecoder( "1e10" );
        jse.decode();
    }

    public void testNumberESub() throws IOException{
        JSONDecoder jse = new JSONDecoder( "-100e10" );
        jse.decode();
    }



    public void testNumberEWithoutDigit() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "e10" );
            jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testNumberEMoreWithoutDigit() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "+e10" );
            jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testNumberESubWithoutDigit() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "-e10" );
            jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testIntString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 10 ) );
        Object value = jse.decode();
        assertEquals( "10", value );
    }

    public void testInt() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 10 ) );
        Object value = jse.decode( int.class );
        assertEquals( 10, value );
    }

    public void testInteger() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 10 ) );
        Object value = jse.decode( Integer.class );
        assertEquals( 10, value );
    }

    public void testIntNegativoString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -100 ) );
        Object value = jse.decode();
        assertEquals( "-100", value );
    }

    public void testIntNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -100 ) );
        Object value = jse.decode( int.class );
        assertEquals( -100, value );
    }

    public void testIntegerNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -100 ) );
        Object value = jse.decode( Integer.class );
        assertEquals( -100, value );
    }


    public void testLongString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 99999999999L ) );
        Object value = jse.decode();
        assertEquals( "99999999999", value );
    }

    public void testLong() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 99999999999L ) );
        Object value = jse.decode( long.class );
        assertEquals( 99999999999L, value );
    }

    public void testLongObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 99999999999L ) );
        Object value = jse.decode( Long.class );
        assertEquals( 99999999999L, value );
    }


    public void testLongNegativoString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -99999999999L ) );
        Object value = jse.decode();
        assertEquals( "-99999999999", value );
    }

    public void testLongNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -99999999999L ) );
        Object value = jse.decode( long.class );
        assertEquals( -99999999999L, value );
    }

    public void testLongNegativoObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -99999999999L ) );
        Object value = jse.decode( Long.class );
        assertEquals( -99999999999L, value );
    }



    public void testDoubleString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 2.03 ) );
        Object value = jse.decode();
        assertEquals( "2.03", value );
    }

    public void testDouble() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 2.03 ) );
        Object value = jse.decode( double.class );
        assertEquals( 2.03, value );
    }

    public void testDoubleObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 2.03 ) );
        Object value = jse.decode( Double.class );
        assertEquals( 2.03, value );
    }




    public void testDoubleNegativoString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -2.03 ) );
        Object value = jse.decode();
        assertEquals( "-2.03", value );
    }

    public void testDoubleNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -2.03 ) );
        Object value = jse.decode( double.class );
        assertEquals( -2.03, value );
    }

    public void testDoubleNegativoObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -2.03 ) );
        Object value = jse.decode( Double.class );
        assertEquals( -2.03, value );
    }


    public void testFloatString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 2.03f ) );
        Object value = jse.decode();
        assertEquals( "2.03", value );
    }

    public void testFloat() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 2.03f ) );
        Object value = jse.decode( float.class );
        assertEquals( 2.03f, value );
    }

    public void testFloatObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 2.03f ) );
        Object value = jse.decode( Float.class );
        assertEquals( 2.03f, value );
    }



    public void testFloatNegativoString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -2.03f ) );
        Object value = jse.decode();
        assertEquals( "-2.03", value );
    }

    public void testFloatNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -2.03f ) );
        Object value = jse.decode( float.class );
        assertEquals( -2.03f, value );
    }

    public void testFloatNegativoObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -2.03f ) );
        Object value = jse.decode( Float.class );
        assertEquals( -2.03f, value );
    }


    public void testShortString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( (short)2 ) );
        Object value = jse.decode();
        assertEquals( "2", value );
    }

    public void testShort() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( (short)2 ) );
        Object value = jse.decode( short.class );
        assertEquals( (short)2, value );
    }

    public void testShortObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( (short)2 ) );
        Object value = jse.decode( Short.class );
        assertEquals( (short)2, value );
    }



    public void testShortNegativoString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( (short)-2 ) );
        Object value = jse.decode();
        assertEquals( "-2", value );
    }

    public void testShortNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( (short)-2 ) );
        Object value = jse.decode( short.class );
        assertEquals( (short)-2, value );
    }

    public void testShortNegativoObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( (short)-2 ) );
        Object value = jse.decode( Short.class );
        assertEquals( (short)-2, value );
    }




    public void testBigDecimalString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "123.222" );
        assertEquals( "123.222", jse.decode() );
    }

    public void testBigDecimal() throws IOException{
        JSONDecoder jse = new JSONDecoder( (new BigDecimal(123.222)).toString() );
        assertEquals( new BigDecimal(123.222), jse.decode( BigDecimal.class ) );
    }

    public void testBigDecimalNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( (new BigDecimal(-123.222)).toString() );
        assertEquals( new BigDecimal(-123.222), jse.decode( BigDecimal.class ) );
    }

    public void testBigIntegerString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "123" );
        assertEquals( "123", jse.decode() );
    }

    public void testBigInteger() throws IOException{
        JSONDecoder jse = new JSONDecoder( "123" );
        assertEquals( new BigInteger("123",10), jse.decode( BigInteger.class ) );
    }

    public void testBigIntegerNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( "-123" );
        assertEquals( new BigInteger("-123",10), jse.decode( BigInteger.class ) );
    }

    public void testByteString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "10" );
        assertEquals( "10", jse.decode() );
    }

    public void testByte() throws IOException{
        JSONDecoder jse = new JSONDecoder( "10" );
        assertEquals( Byte.valueOf(  (byte)10 ), Byte.valueOf( jse.decode(byte.class) ) );
    }

    public void testByteObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( "10" );
        assertEquals( Byte.valueOf((byte)10), jse.decode(Byte.class) );
    }

    public void testByteNegativoString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "-10" );
        assertEquals( "-10",jse.decode() );
    }

    public void testByteNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( "-10" );
        assertEquals( Byte.valueOf(  (byte)-10 ), Byte.valueOf( jse.decode(byte.class) ) );
    }

    public void testByteNegativoObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( "-10" );
        assertEquals( Byte.valueOf((byte)-10), jse.decode(Byte.class) );
    }

}
