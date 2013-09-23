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


package org.brandao.brutos.proxy;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.mapping.Controller;

/**
 *
 * @author Brand�o
 */
public class JavassistProxyFactory extends AbstractProxyFactory{
    
    private ClassPool pool = null;
    private ProxyFactory factory;
    
    public JavassistProxyFactory( Class superClass, ClassPool pool )
            throws Exception{
        super( superClass );
        this.pool = pool;
        pool.insertClassPath( new ClassClassPath( superClass ) );
        proxyClass = createProxyClass( superClass );
    }

    public Object getNewProxy(Object resource,Controller form,
            ConfigurableApplicationContext context, 
            Invoker invoker) throws BrutosException{

        MethodHandler handler = 
                new JavassistActionHandler(
                    resource,
                    form,
                    context,
                    invoker);

        try{
            ProxyObject instance = (ProxyObject)ClassUtil.getInstance(proxyClass);
            instance.setHandler(handler);
            return instance;
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }
    }

    private Class createProxyClass( Class clazz ) throws Exception{
        factory = new ProxyFactory();
        factory.setSuperclass(clazz);
        return factory.createClass();
    }

}