

package org.brandao.brutos.type;

import java.lang.reflect.Array;


public class DefaultArrayTypeFactory implements TypeFactory{

    public DefaultArrayTypeFactory(){
    }
    
    public Type getInstance() {
        return new DefaultArrayType();
    }

    public Class getClassType() {
        return Array.class;
    }

    public boolean matches(Class type) {
        return type.isArray();
    }
    
}
