

package org.brandao.brutos.type;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class CalendarType extends DefaultDateType{

    public CalendarType(){
    }
    
    public CalendarType(String pattern) {
        super.setPattern(pattern);
    }

    private Object toValue( Object value ){
        Calendar cal =
                GregorianCalendar.getInstance();

        cal.setTime((Date)value);
        return cal;
    }

    public Class getClassType() {
        return Calendar.class;
    }

    public Object convert(Object value) {
        if( value instanceof Calendar )
            return value;
        else
        if( value instanceof String ){
            value = super.convert(value);
            return ((String) value).isEmpty()? null : toValue( value );
        }
        else
        if(value == null)
            return null;
        else
            throw new UnknownTypeException();
    }

}
