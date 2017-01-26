

package org.brandao.brutos.proxy;

import org.brandao.brutos.ProxyFactory;


public abstract class AbstractProxyFactory implements ProxyFactory{
    
    protected Class superClass = null;
    protected Class proxyClass = null;
    
    public AbstractProxyFactory( Class superClass ) {
        this.superClass = superClass;
    }
    
}
