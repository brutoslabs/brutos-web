

package org.brandao.brutos.type;

public interface GenericType extends Type {

    void setRawClass(Class value);

    Class getRawClass();
    
    void setParameters(Object[] value);
    
    Object[] getParameters();
    
}
