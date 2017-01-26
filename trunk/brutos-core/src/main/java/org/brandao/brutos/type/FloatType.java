

package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;


public class FloatType 
    extends AbstractType implements Type{
 
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
            return Float.valueOf( (String)value );
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
