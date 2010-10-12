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
import java.net.URL;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.type.json.JSONDecoder;

/**
 *
 * @author Afonso Brandao
 */
public class JSONDecoderURLTest extends TestCase implements Test{

    public JSONDecoderURLTest(){
        super();
    }

    public void testURLString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"http://www.mysite.com/\"" );
        assertEquals( "http://www.mysite.com/", jse.decode() );
    }

    public void testURL() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"http://www.mysite.com/\"" );
        assertEquals( new URL("http://www.mysite.com/"), jse.decode( URL.class ) );
    }

    public void testURLStringUnicode() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"http\\u003a\\/\\/www\\u002emysite\\u002ecom\\/\"" );
        assertEquals( "http://www.mysite.com/", jse.decode() );
    }

    public void testURLUnicode() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"http\\u003a\\/\\/www\\u002emysite\\u002ecom\\/\"" );
        assertEquals( new URL("http://www.mysite.com/"), jse.decode( URL.class ) );
    }
}
