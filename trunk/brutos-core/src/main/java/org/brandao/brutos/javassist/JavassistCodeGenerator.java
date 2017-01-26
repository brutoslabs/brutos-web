


package org.brandao.brutos.javassist;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javassist.ClassPool;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.CodeGenerator;
import org.brandao.brutos.ProxyFactory;


public class JavassistCodeGenerator implements CodeGenerator{
    
    private Map proxyFactory = new HashMap();
    private ClassPool pool   = ClassPool.getDefault();
    
    public JavassistCodeGenerator() {
    }

    public ProxyFactory getProxyFactory(Class clazz ) throws BrutosException {
        try{
            if( proxyFactory.containsKey( clazz ) )
                return (ProxyFactory)proxyFactory.get( clazz );
            else
                return createProxyFactory( clazz );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    private synchronized ProxyFactory createProxyFactory( Class clazz )
            throws Exception{
        if( proxyFactory.containsKey( clazz ) )
            return (ProxyFactory)proxyFactory.get( clazz );
        else{
            ProxyFactory pxf = new JavassistProxyFactory( clazz, pool );
            proxyFactory.put( clazz, pxf );
            return pxf;
        }
    }

    public void configure(Properties properties) {
    }

    public void destroy() {
    }

}
