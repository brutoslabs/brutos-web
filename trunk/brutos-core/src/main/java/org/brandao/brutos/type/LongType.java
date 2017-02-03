


package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;


public class LongType 
 extends AbstractType implements Type{

    public LongType() {
    }

    public Class getClassType() {
        return Long.TYPE;
    }

    public Object convert(Object value) {
        if( value instanceof Long )
            return value;
        else
        if( value instanceof String )
            return ((String) value).isEmpty()? 0L : Long.valueOf( (String)value );
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
