

package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;


public class IntegerWrapperType 
    extends AbstractType implements Type{

    public IntegerWrapperType() {
    }

    public Class getClassType() {
        return Integer.class;
    }

    public Object convert(Object value) {
        if( value instanceof Integer )
            return value;
        else
        if( value instanceof String )
            return ((String) value).isEmpty()? null : Integer.valueOf( (String)value );
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
