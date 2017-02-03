


package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;


public class ShortType 
    extends AbstractType implements Type{

	private static final short DEFAULT_VALUE = 0;
	
    public ShortType() {
    }

    public Class getClassType() {
        return Short.TYPE;
    }

    public Object convert(Object value) {
        if( value instanceof Double )
            return value;
        else
        if( value instanceof String )
            return ((String) value).isEmpty()? DEFAULT_VALUE : Short.valueOf((String)value);
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
