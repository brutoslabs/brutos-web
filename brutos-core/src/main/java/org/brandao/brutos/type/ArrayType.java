

package org.brandao.brutos.type;


public interface ArrayType extends Type{

    void setComponentType(Type type);

    Type getComponentType();
    
    void setRawClass(Class value);

    Class getRawClass();

}
