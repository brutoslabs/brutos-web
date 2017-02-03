package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;

public interface Type {
    
    Object convert(Object value);
    
    void show(MvcResponse response, Object value) throws IOException;
    
    Class getClassType();
    
    void setClassType(Class value);
    
    boolean isAlwaysRender();
    
}
