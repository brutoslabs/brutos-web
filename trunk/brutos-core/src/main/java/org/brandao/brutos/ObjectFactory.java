

package org.brandao.brutos;

import java.util.Properties;


public interface ObjectFactory {
    
    
    Object getBean( String name );
    
    
    Object getBean( Class clazz );

    
    void configure( Properties properties );

    
    void destroy();
    
}
