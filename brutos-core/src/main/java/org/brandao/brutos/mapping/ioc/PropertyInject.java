

package org.brandao.brutos.mapping.ioc;

import java.lang.reflect.Method;


public class PropertyInject {
    
    private Injectable property;
    
    private String name;
    
    private Method method;
    
    public PropertyInject() {
    }

    public PropertyInject( String name, Injectable property, Method method ) {
        this.name = name;
        this.property = property;
        this.method = method;
    }
    
    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Injectable getProperty() {
        return property;
    }

    public void setProperty(Injectable property) {
        this.property = property;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
