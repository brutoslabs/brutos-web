package org.brandao.brutos.type;

import java.io.IOException;
import java.math.BigInteger;
import org.brandao.brutos.MvcResponse;


public class BigIntegerType 
    extends AbstractType implements Type {

    public Object convert(Object value) {
        if(value instanceof BigInteger)
            return (BigInteger)value;
        else
        if(value instanceof String)
            return ((String) value).isEmpty()? null : new BigInteger((String)value);
        else
        if(value == null)
            return null;
        else
            throw new UnknownTypeException(value.getClass().toString());
    }

    public void show(MvcResponse response, Object value) throws IOException {
        response.process(value);
    }
    
}
