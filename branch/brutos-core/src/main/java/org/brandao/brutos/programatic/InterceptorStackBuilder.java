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

package org.brandao.brutos.programatic;

import java.util.HashMap;
import java.util.Set;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.InterceptorStack;

/**
 *
 * @author Afonso Brandao
 */
public class InterceptorStackBuilder {
    
    private Interceptor interceptor;
    private InterceptorManager manager;
    private Interceptor current;
    
    public InterceptorStackBuilder( Interceptor interceptor, InterceptorManager manager ) {
        this.interceptor = interceptor;
        this.manager     = manager;
        this.current     = interceptor;
    }
    
    public InterceptorStackBuilder addInterceptor( String interceptorName ){
        Interceptor in = manager.getInterceptor( interceptorName );
        
        current = new Interceptor( in );
        current.setProperties( new HashMap() );
        
        Set<String> keys = in.getProperties().keySet();
        
        for( String key: keys ){
            Object value = in.getProperties().get( key );
            current.getProperties().put( /*in.getName() + "." +*/ key, value );
        }
        
        ((InterceptorStack)interceptor).addInterceptor( current );
        return this;
    }
    
    public InterceptorStackBuilder addParameter( String name, String value ){
        
        if( current == null )
            throw new BrutosException( "addInterceptor() is not invoked!" );
        
        /*interceptor*/current.setProperty( /*current.getName() + "." +*/ name, value );
        return this;
    }
    
}
