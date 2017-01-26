

package org.brandao.brutos;

import java.util.*;
import org.brandao.brutos.mapping.Interceptor;


public interface InterceptorManager {
    
    
    InterceptorStackBuilder addInterceptorStack( String name, boolean isDefault );
    
    
    InterceptorBuilder addInterceptor( String name, Class<?> interceptor, boolean isDefault );

    
    Interceptor getInterceptor( String name );

    
    boolean containsInterceptor(String name);
    
    
    Interceptor getInterceptor( Class<?> clazz );

    
    boolean containsInterceptor(Class<?> clazz);
    
    
    List<Interceptor> getDefaultInterceptors();
    
    
    void setParent(InterceptorManager parent);
    
    
    InterceptorManager getParent();
    
    
}
