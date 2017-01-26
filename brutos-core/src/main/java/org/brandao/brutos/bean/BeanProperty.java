

package org.brandao.brutos.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public interface BeanProperty {
    
    void set( Object o, Object value ) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException;
    
    Object get(Object o) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException;

    Object getDeclaredGenericType();
    
    Class getDeclaredType();
    
    Object getGenericType();
    
    Class getType();
    
    Field getField();

    void setField(Field field);

    Method getSet();

    void setSet(Method set);

    Method getGet();

    void setGet(Method get);
    
    String getName();

    void setName(String name);
    
}
