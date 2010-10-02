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


package org.brandao.brutos.type.json.jsonencoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.type.json.JSONEncoder;

/**
 *
 * @author Afonso Brandao
 */
public class JSONEncoderNumberTest extends TestCase implements Test{

    public JSONEncoderNumberTest(){
        super();
    }

    public void testInt() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( 100 );
        jse.close();
        assertEquals( "100", out.toString() );
    }

    public void testInteger() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( new Integer(100) );
        jse.close();
        assertEquals( "100", out.toString() );
    }

    public void testIntNegativo() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( -100 );
        jse.close();
        assertEquals( "-100", out.toString() );
    }

    public void testLong() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( 15632453214L );
        jse.close();
        assertEquals( "15632453214", out.toString() );
    }

    public void testLongObject() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( new Long(15632453214L) );
        jse.close();
        assertEquals( "15632453214", out.toString() );
    }

    public void testLongNegativo() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( -15632453214L );
        jse.close();
        assertEquals( "-15632453214", out.toString() );
    }

    public void testDouble() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( 0.2 );
        jse.close();
        assertEquals( "0.2", out.toString() );
    }

    public void testDoubleObject() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( new Double(0.2) );
        jse.close();
        assertEquals( "0.2", out.toString() );
    }

    public void testDoubleNegativo() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( -0.2 );
        jse.close();
        assertEquals( "-0.2", out.toString() );
    }

    public void testFloat() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( 0.2f );
        jse.close();
        assertEquals( "0.2", out.toString() );
    }

    public void testFloatObject() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( new Float(0.2) );
        jse.close();
        assertEquals( "0.2", out.toString() );
    }

    public void testFloatNegativo() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( -0.2f );
        jse.close();
        assertEquals( "-0.2", out.toString() );
    }

    public void testShort() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( (short)33 );
        jse.close();
        assertEquals( "33", out.toString() );
    }

    public void testShortObject() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( new Short((short)33) );
        jse.close();
        assertEquals( "33", out.toString() );
    }

    public void testShortNegativo() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( (short)-33 );
        jse.close();
        assertEquals( "-33", out.toString() );
    }

    public void testBigDecimal() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( new BigDecimal( 123.222 ) );
        jse.close();
        assertEquals( (new BigDecimal( 123.222 )).toString(), out.toString() );
    }

    public void testBigDecimalNegativo() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( new BigDecimal( -123.222 ) );
        jse.close();
        assertEquals( (new BigDecimal( -123.222 )).toString(), out.toString() );
    }

    public void testBigInteger() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( new BigInteger( "123", 10 ) );
        jse.close();
        assertEquals( "123", out.toString() );
    }

    public void testBigIntegerNegativo() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( new BigInteger( "-123", 10 ) );
        jse.close();
        assertEquals( "-123", out.toString() );
    }

    public void testByte() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( (byte)10 );
        jse.close();
        assertEquals( "10", out.toString() );
    }

    public void testByteNegativo() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( (byte)-22 );
        jse.close();
        assertEquals( "-22", out.toString() );
    }

}
