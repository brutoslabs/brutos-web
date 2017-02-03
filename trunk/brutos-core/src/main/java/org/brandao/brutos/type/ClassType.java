

package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;


public class ClassType extends AbstractType{

    public ClassType() {
    }

    public Object toValue( String value ){
        try{
            return Class.forName( value, true, 
            		Thread.currentThread().getContextClassLoader() );
        }
        catch( Exception e ){
            return null;
        }
    }
    
    public Class getClassType() {
        return Class.class;
    }

    public Object getValue(Object value) {
        return null;
    }
    
    public Object convert(Object value) {
        if( value instanceof Class )
            return value;
        else
        if(value instanceof String )
            return ((String) value).isEmpty()? null : toValue( (String)value );
        else
        if(value == null){
        	return null;
        }
        else
            throw new UnknownTypeException();
    }

    public void setValue(Object value) throws IOException {
    }
    
    public void show(MvcResponse response, Object value) throws IOException {
        response.process(value);
    }
    
}
