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

package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcResponse;

/**
 *
 * @author Afonso Brandao
 */
public class ByteWrapperType implements Type{

    public ByteWrapperType() {
    }

    public Object toValue( String value ){
        try{
            return Byte.valueOf( value );
        }
        catch( Exception e ){
            return new Byte((byte)0);
        }
    }

    public Class getClassType() {
        return Byte.class;
    }

    public Object getValue(Object value) {
        if( value instanceof Byte )
            return value;
        else
        if( value instanceof String )
            return Byte.valueOf((String)value);
        else
        if( value == null )
            return null;
        else
            throw new UnknownTypeException(value.getClass().getName());
    }

    public void setValue(Object value) throws IOException {
        ConfigurableApplicationContext app =
                (ConfigurableApplicationContext)Invoker.getApplicationContext();
        MvcResponse response = app.getMvcResponse();
        response.process(value);
    }
    
}
