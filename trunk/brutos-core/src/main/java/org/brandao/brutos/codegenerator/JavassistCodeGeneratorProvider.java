/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
