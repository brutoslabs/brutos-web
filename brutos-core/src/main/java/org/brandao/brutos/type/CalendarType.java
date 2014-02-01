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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Implementação padão do tipo {@link java.util.Calendar}.
 * 
 * @author Brandao
 */
public class CalendarType extends DefaultDateType{

    /**
     * Cria um novo tipo.
     */
    public CalendarType(){
    }
    
    /**
     * Cria um novo tipo usando um formato de data específico.
     * 
     * @param pattern Formato da data.
     */
    public CalendarType(String pattern) {
        super.setPattern(pattern);
    }

    private Object toValue( Object value ){
        try{
            Calendar cal =
                    GregorianCalendar.getInstance();

            cal.setTime((Date)value);
            return cal;
        }
        catch( Exception e ){
            return null;
        }
    }

    public Class getClassType() {
        return Calendar.class;
    }

    /**
     * @see DateTimeType#convert(java.lang.Object) 
     * 
     */
    public Object convert(Object value) {
        if( value instanceof Calendar )
            return value;
        else
        if( value instanceof String )
            return toValue( super.convert(value) );
        else
        if(value == null)
            return null;
        else
            throw new UnknownTypeException();
    }

}
