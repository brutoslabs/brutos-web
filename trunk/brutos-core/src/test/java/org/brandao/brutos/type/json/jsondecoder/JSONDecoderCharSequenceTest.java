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
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.type.json.JSONDecoder;

/**
 *
 * @author Afonso Brandao
 */
public class JSONDecoderCharSequenceTest extends TestCase implements Test{

    static class CharArray implements CharSequence, Serializable{

        private StringBuffer value = new StringBuffer( "Test char sequence" );

        public int length() {
            return value.length();
        }

        public char charAt(int index) {
            return value.charAt(index);
        }

        public CharSequence subSequence(int start, int end) {
            return value.subSequence(start, end);
        }

        @Override
        public String toString(){
            return value.toString();
        }
    }

    public JSONDecoderCharSequenceTest(){
        super();
    }

    public void testCharSequenceString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"Test char sequence\"" );
        assertEquals( "Test char sequence", jse.decode() );
    }

    public void testCharSequence() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"Test char sequence\"" );
        assertEquals( (new CharArray()).toString(), jse.decode( CharSequence.class ).toString() );
    }
}
