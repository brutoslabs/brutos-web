

package org.brandao.brutos;

import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.mapping.Controller;


public interface ProxyFactory {

    Object getNewProxy(Object resource,Controller form, 
            ConfigurableApplicationContext app, Invoker invoker)
                throws BrutosException;
    
}
