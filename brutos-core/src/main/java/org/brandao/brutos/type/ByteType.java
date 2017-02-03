


package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;


public class ByteType extends AbstractType{

    public ByteType() {
    }

    public Class getClassType() {
        return Byte.TYPE;
    }

    public Object convert(Object value) {
        if( value instanceof Byte )
            return value;
        else
        if( value instanceof String )
            return ((String) value).isEmpty()? (byte)0 : Byte.valueOf((String)value );
        else
        if( value == null )
            return null;
        else
            throw new UnknownTypeException(value.getClass().getName());
    }

    public void show(MvcResponse response, Object value) throws IOException {
        response.process(value);
    }
    
}
