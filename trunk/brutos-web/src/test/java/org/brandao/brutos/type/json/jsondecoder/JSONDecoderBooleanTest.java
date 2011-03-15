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
public class JSONDecoderBooleanTest extends TestCase implements Test{

    public JSONDecoderBooleanTest(){
        super();
    }

    public void testTrue() throws IOException{
        JSONDecoder jse = new JSONDecoder( "true" );
        assertEquals( Boolean.valueOf( true ), Boolean.valueOf( jse.decode( boolean.class ) ) );
    }

    public void testFalse() throws IOException{
        JSONDecoder jse = new JSONDecoder( "false" );
        assertEquals( Boolean.valueOf( false ), Boolean.valueOf( jse.decode( boolean.class ) ) );
    }

    public void testObjectTrue() throws IOException{
        JSONDecoder jse = new JSONDecoder( "true" );
        assertEquals( Boolean.valueOf( true ), Boolean.valueOf( jse.decode( Boolean.class ) ) );
    }

    public void testObjectFalse() throws IOException{
        JSONDecoder jse = new JSONDecoder( "false" );
        assertEquals( Boolean.valueOf( false ), Boolean.valueOf( jse.decode( Boolean.class ) ) );
    }

}
