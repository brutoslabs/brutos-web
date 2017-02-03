package org.brandao.brutos.type;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.brandao.brutos.MvcResponse;


public class DefaultDateType 
    extends AbstractType implements DateTimeType{

    private SimpleDateFormat sdf;
    
    public DefaultDateType(String pattern) {
        this.setPattern(pattern);
    }

    public DefaultDateType() {
    }

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

    public Object convert(Object value) {
        if( value instanceof Date )
            return value;
        else
        if( value instanceof String )
            return ((String) value).isEmpty()? null : toValue( (String)value );
        else
        if( value == null )
            return null;
        else
            throw new UnknownTypeException(value.getClass().getName());
    }

    public void show(MvcResponse response, Object value) throws IOException {
        response.process(value);
    }

    public String getPattern() {
        return this.sdf.toPattern();
    }
    
}
