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
import org.brandao.brutos.type.json.JSONException;

/**
 *
 * @author Afonso Brandao
 */
public class JSONDecoderStringTest extends TestCase implements Test{

    public JSONDecoderStringTest(){
        super();
    }

    public void testCharUnicode() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"A\"" );
        assertEquals( "A", jse.decode() );
    }

    public void testChar() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\uffff\"" );
        assertEquals( "\uffff", jse.decode() );
    }

    public void testCharSpace() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\" \"" );
        assertEquals( " ", jse.decode() );
    }

    public void testQuantationMark() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\\"\"" );
        assertEquals( "\"", jse.decode() );
    }

    public void testReverseSolidus() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\\\\"" );
        assertEquals( "\\", jse.decode() );
    }

    public void testUniqueReverseSolidus() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "\"\\\"" );
            Object result = jse.decode();
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testUniqueReverseSolidusMore() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "\"c:\\kk\\nn\"" );
            char result = jse.decode( char.class );
        }
        catch( JSONException e ){
            return;
        }
        fail( "expected JSONException" );
    }

    public void testSolidus() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\/\"" );
        assertEquals( "/", jse.decode() );
    }

    public void testUniqueSolidus() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"/\"" );
        assertEquals( "/", jse.decode() );
    }

    public void testUniqueSolidusMore() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"c:/kk/nn\"" );
        assertEquals( "c:/kk/nn", jse.decode() );
    }

    public void testBackspace() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\b\"" );
        assertEquals( "\b", jse.decode() );
    }

    public void testFormfeed() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\f\"" );
        assertEquals( "\f", jse.decode() );
    }

    public void testNewLine() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\n\"" );
        assertEquals( "\n", jse.decode() );
    }

    public void testCarriageReturn() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\r\"" );
        assertEquals( "\r", jse.decode() );
    }

    public void testHorizontalTab() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\t\"" );
        assertEquals( "\t", jse.decode() );
    }

    public void testHex() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\uffff\"" );
        assertEquals( "\uffff", jse.decode() );
    }


}
