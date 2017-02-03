

package org.brandao.brutos.type;

import org.brandao.brutos.MvcResponse;


public class BooleanType extends AbstractType{

    public BooleanType() {
    }

    public Class getClassType() {
        return Boolean.TYPE;
    }

    public Object convert(Object value) {
        if( value instanceof Boolean )
            return value;
        else
        if(value instanceof String)
            return ((String) value).isEmpty()? false : Boolean.valueOf((String)value);
        else
        if( value == null )
            return null;
        else
            throw new UnknownTypeException(value.getClass().getName());
    }

    public void show(MvcResponse response, Object value) {
        response.process(value);
    }

}
