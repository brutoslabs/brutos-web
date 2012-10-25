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

package org.brandao.brutos.old.programatic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.InterceptorStack;
import org.brandao.brutos.InterceptorBuilder;
import org.brandao.brutos.InterceptorStackBuilder;

/**
 *
 * @author Afonso Brandao
 */
public class InterceptorManager extends org.brandao.brutos.InterceptorManager{

    private Map interceptors;
    private List defaultInterceptors;
    private IOCManager iocManager;

    public InterceptorManager( IOCManager iocManager ) {
        this.interceptors = new HashMap();
        this.defaultInterceptors = new ArrayList();
        this.iocManager = iocManager;
    }

    public InterceptorStackBuilder addInterceptorStack( String name, boolean isDefault ){
        Interceptor in;

        name = name == null || name.length() == 0? null : name;

        if( interceptors.containsKey( name ) )
            throw new BrutosException( "conflict interceptor name: " + name );

        if( name == null )
            throw new BrutosException( "interceptor name is required!" );

        in = new InterceptorStack();

        if( isDefault )
            defaultInterceptors.add( in );

        in.setName( name );
        in.setDefault( isDefault );
        in.setProperties( new HashMap() );
        interceptors.put( name, in );

        return new InterceptorStackBuilder( in, this );
    }

    public InterceptorBuilder addInterceptor( String name, Class interceptor, boolean isDefault ){
        Interceptor in;

        name = name == null || name.length() == 0? null : name;

        if( interceptors.containsKey( name ) )
            throw new BrutosException( "conflict interceptor name: " + name );

        if( interceptor == null )
            throw new BrutosException( "interceptor class is required!" );

        if( name == null )
            throw new BrutosException( "interceptor name is required!" );

        if( !org.brandao.brutos.interceptor.Interceptor.class.isAssignableFrom( interceptor ) )
            throw new BrutosException( "is not a interceptor: " + interceptor.getName() );

        in = new Interceptor();

        if( isDefault )
            defaultInterceptors.add( in );

        //IOC/DI
        iocManager.addBean( name, interceptor, ScopeType.valueOf("prototype") );

        in.setType( interceptor );
        in.setName( name );
        in.setProperties( new HashMap() );
        in.setDefault( isDefault );
        interceptors.put( name, in );
        return new InterceptorBuilder( in, this );

    }

    public Interceptor getInterceptor( String name ){
        if( !interceptors.containsKey( name ) )
            throw new BrutosException( "interceptor not found: " + name );
        else
            return (Interceptor) interceptors.get( name );
    }

    public List getDefaultInterceptors(){
        return this.defaultInterceptors;
    }

}