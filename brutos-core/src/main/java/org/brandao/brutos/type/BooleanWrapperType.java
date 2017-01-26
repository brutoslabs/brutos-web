

package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcResponse;


public class BooleanWrapperType extends AbstractType{

    public BooleanWrapperType() {
    }

    public Class getClassType() {
        return Boolean.class;
    }

    public Object convert(Object value) {
        if( value instanceof Boolean )
            return value;
        else
        if( value instanceof String )
            return Boolean.valueOf((String)value);
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
