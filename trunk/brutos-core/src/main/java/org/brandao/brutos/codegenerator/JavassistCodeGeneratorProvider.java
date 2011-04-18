/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */


package org.brandao.brutos.codegenerator;

import java.util.HashMap;
import java.util.Map;
import javassist.ClassPool;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.proxy.JavassistProxyFactory;
import org.brandao.brutos.proxy.ProxyFactory;

/**
 *
 * @author Brandao
 */
public class JavassistCodeGeneratorProvider extends CodeGeneratorProvider{
    
    private Map proxyFactory = new HashMap();
    private ClassPool pool   = ClassPool.getDefault();
    
    public JavassistCodeGeneratorProvider() {
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

}
