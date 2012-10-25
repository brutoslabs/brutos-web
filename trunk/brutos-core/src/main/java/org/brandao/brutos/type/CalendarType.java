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
import java.util.GregorianCalendar;

/**
 *
 * @author Brandao
 */
public class CalendarType extends DefaultDateTimeType{

    public CalendarType(){
    }
    
    public CalendarType(String mask) {
        this.setMask(mask);
    }

    public Object toValue( String value ){
        try{
            Calendar cal =
                    GregorianCalendar.getInstance();

            cal.setTime(sdf.parse( value ));

            return cal;
        }
        catch( Exception e ){
            return null;
        }
    }

    public Class getClassType() {
        return Calendar.class;
    }

    public Object getValue(Object value) {
        return null;
    }
    
    public Object convert(Object value) {
        if( value instanceof Calendar )
            return value;
        else
        if( value == null || value instanceof String )
            return toValue( (String)value );
        else
            throw new UnknownTypeException();
    }

}
