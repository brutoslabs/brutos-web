/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.type;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.brandao.brutos.MvcResponse;

/**
 *
 * @author Afonso Brandao
 */
public class DefaultDateTimeType implements DateTimeType{

    SimpleDateFormat sdf;
    
    public DefaultDateTimeType(String mask) {
        this.setMask(mask);
    }

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
        return null;
    }
    
    public Object convert(Object value) {
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
    }
    
    public void show(MvcResponse response, Object value) throws IOException {
        response.process(value);
    }

    public String getMask() {
        return this.sdf.toPattern();
    }
    
}
