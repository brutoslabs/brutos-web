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
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.type.json.JSONDecoder;

/**
 *
 * @author Afonso Brandao
 */
public class JSONDecoderCharTest extends TestCase implements Test{

    public JSONDecoderCharTest(){
        super();
    }

    public void testCharUnicode() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"A\"" );
        char result = jse.decode( char.class );
        assertEquals( 'A', result );
    }

    public void testChar() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\uffff\"" );
        char result = jse.decode( char.class );
        assertEquals( '\uffff', result );
    }

    public void testCharSpace() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\" \"" );
        char result = jse.decode( char.class );
        assertEquals( ' ', result );
    }

    public void testQuantationMark() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\\"\"" );
        Character result = jse.decode( char.class );
        assertEquals( Character.valueOf( '\"' ), result );
    }

    public void testReverseSolidus() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\\\\"" );
        char result = jse.decode( char.class );
        assertEquals( '\\', result );
    }

    public void testSolidus() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\/\"" );
        char result = jse.decode( char.class );
        assertEquals( '/', result );
    }

    public void testSimpleSolidus() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"/\"" );
        char result = jse.decode( Character.class );
        assertEquals( '/', result );
    }

    public void testBackspace() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\b\"" );
        char result = jse.decode( char.class );
        assertEquals( '\b', result );
    }

    public void testFormfeed() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\f\"" );
        char result = jse.decode( char.class );
        assertEquals( '\f', result );
    }

    public void testNewLine() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\n\"" );
        char result = jse.decode( char.class );
        assertEquals( '\n', result );
    }

    public void testCarriageReturn() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\r\"" );
        char result = jse.decode( char.class );
        assertEquals( '\r', result );
    }

    public void testHorizontalTab() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\t\"" );
        char result = jse.decode( char.class );
        assertEquals( '\t', result );
    }

    public void testHex() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\uffff\"" );
        char result = jse.decode( char.class );
        assertEquals( '\uffff', result );
    }

}
