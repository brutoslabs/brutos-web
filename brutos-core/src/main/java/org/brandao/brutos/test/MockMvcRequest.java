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
package org.brandao.brutos.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.brandao.brutos.MvcRequest;

/**
 *
 * @author Brandao
 */
public class MockMvcRequest implements MvcRequest{

    private Map values;
    private Map property;
    private InputStream input;
    private String type;
    private int length;
    private String characterEncoding;
    private Locale locale;
    
    public MockMvcRequest(){
        this( new HashMap(), new HashMap() );
    }

    public MockMvcRequest( Map values, Map property ){
        this(values,property,null);
    }

    public MockMvcRequest( Map values, Map property, InputStream input ){
        this.values = values;
        this.property = property;
        this.input = input;
    }
    
    public Object getValue(String name) {
        return values.get(name);
    }

    public Object getProperty(String name) {
        return this.property.get(name);
    }

    public InputStream getStream() throws IOException {
        return input;
    }

    public String getType() {
        return this.type;
    }

    public int getLength() {
        return length;
    }

    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
