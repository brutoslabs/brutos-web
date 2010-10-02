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
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.type.json.JSONEncoder;

/**
 *
 * @author Afonso Brandao
 */
public class JSONEncoderCharTest extends TestCase implements Test{

    public JSONEncoderCharTest(){
        super();
    }

    public void testCharUnicode() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( 'A' );
        jse.close();
        assertEquals( "\"A\"", out.toString() );
    }

    public void testChar() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( '\uffff' );
        jse.close();
        assertEquals( "\"\\uffff\"", out.toString() );
    }

    public void testCharSpace() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( ' ' );
        jse.close();
        assertEquals( "\" \"", out.toString() );
    }

    public void testQuantationMark() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( '\"' );
        jse.close();
        assertEquals( "\"\\\"\"", out.toString() );
    }

    public void testReverseSolidus() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( '\\' );
        jse.close();
        assertEquals( "\"\\\\\"", out.toString() );
    }

    public void testSolidus() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( '/' );
        jse.close();
        assertEquals( "\"\\/\"", out.toString() );
    }

    public void testBackspace() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( '\b' );
        jse.close();
        assertEquals( "\"\\b\"", out.toString() );
    }

    public void testFormfeed() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( '\f' );
        jse.close();
        assertEquals( "\"\\f\"", out.toString() );
    }

    public void testNewLine() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( '\n' );
        jse.close();
        assertEquals( "\"\\n\"", out.toString() );
    }

    public void testCarriageReturn() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( '\r' );
        jse.close();
        assertEquals( "\"\\r\"", out.toString() );
    }

    public void testHorizontalTab() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( '\t' );
        jse.close();
        assertEquals( "\"\\t\"", out.toString() );
    }

    public void testHex() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONEncoder jse = new JSONEncoder( out );
        jse.writeObject( '\u0fff' );
        jse.close();
        assertEquals( "\"\\u0fff\"", out.toString() );
    }

}
