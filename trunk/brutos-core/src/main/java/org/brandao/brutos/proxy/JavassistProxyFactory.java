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


package org.brandao.brutos.proxy;

import javassist.*;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.mapping.Controller;

/**
 *
 * @author Brand�o
 */
public class JavassistProxyFactory extends AbstractProxyFactory{
    
    private ClassPool pool = null;
    private ProxyFactory factory;
    
    public JavassistProxyFactory( Class<?> superClass, ClassPool pool )
            throws Exception{
        super( superClass );
        this.pool = pool;
        pool.insertClassPath( new ClassClassPath( superClass ) );
        proxyClass = createProxyClass( superClass );
    }

    public Object getNewProxy(Object resource,Controller form,
            ApplicationContext app, Invoker invoker) throws BrutosException{
        MethodHandler handler = 
                new JavassistActionHandler(resource,form,app,invoker);

        try{
            ProxyObject instance = (ProxyObject)proxyClass.newInstance();
            instance.setHandler(handler);
            return instance;
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }
    }

    private Class<?> createProxyClass( Class clazz ) throws Exception{
        factory = new ProxyFactory();
        factory.setSuperclass(clazz);
        //factory.setInterfaces(new Class[]{ObjectProxy.class});
        return factory.createClass();
    }

}
