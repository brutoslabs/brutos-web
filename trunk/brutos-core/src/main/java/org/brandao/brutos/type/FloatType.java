package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;


public class FloatType 
    extends AbstractType implements Type{
 
	private static final float DEFAULT_VALUE = 0f;
	
    public FloatType() {
    }

    public Class getClassType() {
        return Float.TYPE;
    }

    public Object convert(Object value) {
        if( value instanceof Float )
            return value;
        else
        if( value instanceof String )
            return ((String) value).isEmpty()? DEFAULT_VALUE : Float.valueOf( (String)value );
        else
        if( value == null )
            return null;
        else
            throw new UnknownTypeException();
    }

    public void show(MvcResponse response, Object value) throws IOException {
        response.process(value);
    }
    
}
