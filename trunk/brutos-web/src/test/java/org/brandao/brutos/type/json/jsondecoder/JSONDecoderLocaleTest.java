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
import java.util.Locale;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.type.json.JSONDecoder;

/**
 *
 * @author Afonso Brandao
 */
public class JSONDecoderLocaleTest extends TestCase implements Test{

    public JSONDecoderLocaleTest(){
        super();
    }

    public void testLocaleString() throws IOException{
        String locale =
                String.format(
                        "%s\\u002d%s",
                        Locale.US.getLanguage(),
                        Locale.US.getCountry() );

        JSONDecoder jse = new JSONDecoder( String.format( "\"%s\"", locale ) );
        assertEquals( locale.replace( "\\u002d" , "-"), jse.decode() );
    }

    public void testLocale() throws IOException{
        String locale =
                String.format(
                        "%s\\u002d%s",
                        Locale.US.getLanguage(),
                        Locale.US.getCountry() );

        JSONDecoder jse = new JSONDecoder( String.format( "\"%s\"", locale ) );
        assertEquals( Locale.US, jse.decode( Locale.class ) );
    }

}
