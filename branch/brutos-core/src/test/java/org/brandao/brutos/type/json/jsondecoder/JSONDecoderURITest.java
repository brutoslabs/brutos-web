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
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.type.json.JSONDecoder;

/**
 *
 * @author Afonso Brandao
 */
public class JSONDecoderURITest extends TestCase implements Test{

    public JSONDecoderURITest(){
        super();
    }

    public void testURIString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"file://c:/teste/\"" );
        assertEquals( "file://c:/teste/", jse.decode() );
    }

    public void testURI() throws IOException, URISyntaxException{
        JSONDecoder jse = new JSONDecoder( "\"file://c:/teste/\"" );
        assertEquals( new URI("file://c:/teste/"), jse.decode( URI.class ) );
    }

    public void testURIStringUnicode() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"file\\u003a\\/\\/c\\u003a\\/teste\\/\"" );
        assertEquals( "file://c:/teste/", jse.decode() );
    }

    public void testURIUnicode() throws IOException, URISyntaxException{
        JSONDecoder jse = new JSONDecoder( "\"file\\u003a\\/\\/c\\u003a\\/teste\\/\"" );
        assertEquals( new URI("file://c:/teste/"), jse.decode( URI.class ) );
    }

}
