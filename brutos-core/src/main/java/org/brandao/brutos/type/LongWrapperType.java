


package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;


public class LongWrapperType 
  extends AbstractType implements Type{

    public LongWrapperType() {
    }

    public Class getClassType() {
        return Long.class;
    }

    public Object convert(Object value) {
        if( value instanceof Long )
            return value;
        else
        if( value instanceof String )
            return Long.valueOf( (String)value );
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
