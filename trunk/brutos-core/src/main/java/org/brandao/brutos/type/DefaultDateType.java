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
 * Implementação padão do tipo {@link java.util.Date}.
 * 
 * @author Afonso Brandao
 */
public class DefaultDateType implements DateTimeType{

    private SimpleDateFormat sdf;
    
    /**
     * Cria um novo tipo usando um formato de data específico.
     * 
     * @param pattern Formato da data.
     */
    public DefaultDateType(String pattern) {
        this.setPattern(pattern);
    }

    /**
     * Cria um novo tipo.
     */
    public DefaultDateType() {
    }

    /**
     * @see DateTimeType#setPattern(java.lang.String) 
     */
    public void setPattern( String value ){
        sdf = new SimpleDateFormat( value );
        sdf.setLenient( false );
    }
    
    private Object toValue( String value ){
        try{
            return sdf.parse( value );
        }
        catch( Throwable e ){
            return null;
        }
    }
    
    public Class getClassType() {
        return Date.class;
    }

    /**
     * @see DateTimeType#convert(java.lang.Object) 
     * 
     */
    public Object convert(Object value) {
        if( value instanceof Date )
            return value;
        else
        if( value instanceof String )
            return toValue( (String)value );
        else
        if( value == null )
            return null;
        else
            throw new UnknownTypeException(value.getClass().getName());
    }

    /**
     * @see DateTimeType#show(org.brandao.brutos.MvcResponse, java.lang.Object) 
     * 
     */
    public void show(MvcResponse response, Object value) throws IOException {
        response.process(value);
    }

    /**
     * @see DateTimeType#getPattern() 
     * 
     */
    public String getPattern() {
        return this.sdf.toPattern();
    }
    
}
