package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;


public class DoubleType 
    extends AbstractType implements Type{

	private static final double DEFAULT_VALUE = 0.0;
	
    public DoubleType() {
    }
    
    public Class getClassType() {
        return Double.TYPE;
    }

    public Object getValue(Object value) {
        return null;
    }
    
    public Object convert(Object value) {
        if( value instanceof Double )
            return value;
        else
        if( value instanceof String )
            return ((String) value).isEmpty()? DEFAULT_VALUE : Double.valueOf( (String)value );
        else
        if( value == null )
            return null;
        else
            throw new UnknownTypeException();
    }

    public void setValue(Object value) throws IOException {
    }
    
    public void show(MvcResponse response, Object value) throws IOException {
        response.process(value);
    }
    
}
