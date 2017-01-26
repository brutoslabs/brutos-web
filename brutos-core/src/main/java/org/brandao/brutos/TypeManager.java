

package org.brandao.brutos;

import org.brandao.brutos.type.*;
import java.util.*;


public interface TypeManager {
    
    
    void register(TypeFactory factory);

    
    void remove(Class type);
    
    
    void remove(TypeFactory factory);

    
    List getAllTypes();
    
    
    boolean isStandardType(Class clazz);

    
    Type getType(Object classType);

    
    TypeFactory getTypeFactory(Object classType);
    
    
    Type getType(Object classType, EnumerationType enumType, String pattern);

}
