/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.ioc.spring;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Afonso Brandao
 */
public class AbstractXMLInputStream extends InputStream{
    
    StringBuffer data;
    int pos;
    public AbstractXMLInputStream( StringBuffer data ) {
        this.data = data;
        this.pos = 0;
    }

    public int read() throws IOException {
        return -1;
    }
    
    public int read(byte b[], int off, int len) throws IOException {
        if( pos > data.length() )
            return -1;
        
        int newPos = len > data.length()? data.length() - pos : len;
        
        String sub = data.substring( pos, newPos );
        byte[] bytes = sub.getBytes();
        pos += newPos;
        
        System.arraycopy( bytes, 0, b, off, newPos );
        return newPos;
    }
    
}
