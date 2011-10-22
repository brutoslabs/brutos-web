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
import java.text.SimpleDateFormat;
import java.util.Date;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcResponse;

/**
 *
 * @author Afonso Brandao
 */
public class DefaultDateTimeType implements DateTimeType{

    SimpleDateFormat sdf;
    
    public DefaultDateTimeType() {
    }

    public void setMask( String value ){
        sdf = new SimpleDateFormat( value );
        sdf.setLenient( false );
    }
    
    public Object toValue( String value ){
        try{
            return sdf.parse( value );
        }
        catch( Exception e ){
            return null;
        }
    }
    
    public Class getClassType() {
        return Date.class;
    }

    public Object getValue(Object value) {
        if( value instanceof Date )
            return value;
        else
        if( value instanceof String )
            return toValue( (String)value );
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
