package org.brandao.brutos.mapping.ioc;

import java.beans.PropertyEditor;


public class ValueInject extends Injectable{
    
    private Object value;
    private boolean converted;
    
    public ValueInject() {
        this.converted = false;
    }

    public ValueInject( Class target, Object value ) {
        super( target, null, null, false, null );
        this.value     = value;
        this.converted = false;
    }
    
    public Object getValue() {
        return converted? value : converter(value);
    }

    public void setValue(Object value) {
        this.value = value;
    }

    private Object converter( Object value ){
        
        this.converted = true;

        if( value instanceof String && getTarget() != String.class ){
            
            return null;
        }
        else
            return value;
    }

    public String toString(){
        return String.valueOf( value );
    }
}
