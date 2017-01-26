


package org.brandao.brutos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.brandao.brutos.mapping.Action;


public interface ResourceAction {

    
    public Object invoke( Object source, Object[] args )
            throws IllegalAccessException, IllegalArgumentException,
                InvocationTargetException;

    
    public Action getMethodForm();

    
    public Method getMethod();

    
    public Class returnType();

    
    public Class[] getParametersType();

    
    public Class getResourceClass();

    
    public boolean isAbstract();
}
