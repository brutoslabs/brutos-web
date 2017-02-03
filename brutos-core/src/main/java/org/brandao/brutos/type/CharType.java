

package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;


public class CharType extends AbstractType{

    public CharType() {
    }

    public Class getClassType() {
        return Character.TYPE;
    }

    public Object convert(Object value) {
        if( value instanceof Character )
            return value;
        else
        if( value instanceof String )
            return ((String) value).isEmpty()? (char)0 : ((String)value).charAt(0);
        else
        if( value == null)
            return null;
        else
            throw new UnknownTypeException(value.getClass().getName());
    }

    public void show(MvcResponse response, Object value) throws IOException {
        response.process(value);
    }
    
}
