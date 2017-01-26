

package org.brandao.brutos.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class SetterProperty {

    private Field field;
    private Object object;
    private Method method;

    public SetterProperty( Field field, Object object ){
        this.field = field;
        this.object = object;
    }

    public SetterProperty( Method method, Object object ){
        this.method = method;
        this.object = object;
    }

    public void set( Object value ) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        set(object, value);
    }
    
    public void set( Object o, Object value ) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        
        if(field == null)
            method.invoke( o, new Object[]{value} );
        else
            field.set(o, value);
    }

    public Method getMethod(){
        return method;
    }

}
