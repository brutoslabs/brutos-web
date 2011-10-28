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
        if( value instanceof Calendar )
            return value;
        else
        if( value == null || value instanceof String )
            return toValue( (String)value );
        else
            throw new UnknownTypeException();
    }

}
