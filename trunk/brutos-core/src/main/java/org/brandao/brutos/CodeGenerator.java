

package org.brandao.brutos;

import java.util.Properties;


public interface CodeGenerator {
    
    
    void configure( Properties properties );

    
    void destroy();
    
    
    ProxyFactory getProxyFactory( Class componentClass )
            throws BrutosException;
    
}
