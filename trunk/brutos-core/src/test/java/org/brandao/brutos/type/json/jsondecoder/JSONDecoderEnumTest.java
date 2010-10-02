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
 * @author Thayna Pinheiro
 */
public class JSONDecoderEnumTest extends TestCase implements Test{

    static enum EnumTest{
        VALUE1,
        VALUE2,
        VALUE3
    }

    public JSONDecoderEnumTest(){
        super();
    }

    public void testEnum() throws IOException{
        JSONDecoder jse = new JSONDecoder(String.valueOf( EnumTest.VALUE2.ordinal()));
        Object value = jse.decode(EnumTest.class);
        assertEquals( EnumTest.VALUE2, value );
    }

    public void testEnumString() throws IOException{
        JSONDecoder jse = new JSONDecoder(String.valueOf( EnumTest.VALUE2.ordinal()));
        Object value = jse.decode();
        assertEquals( String.valueOf( EnumTest.VALUE2.ordinal()), value );
    }
}
