

package org.brandao.brutos.type;

import org.brandao.brutos.BrutosException;


public class DefaultEnumTypeFactory implements TypeFactory{

    public DefaultEnumTypeFactory(){
    }
    
    public Type getInstance() {
        return new DefaultEnumType();
    }

    public Class getClassType() {
        return Enum.class;
    }

    public boolean matches(Class type) {
        return type.isEnum();
    }
    
}
