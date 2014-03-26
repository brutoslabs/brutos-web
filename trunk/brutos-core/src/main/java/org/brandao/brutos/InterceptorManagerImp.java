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

package org.brandao.brutos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.InterceptorStack;

/**
 *
 * @author Brandao
 */
public class InterceptorManagerImp implements InterceptorManager{

    private Map interceptors;
    private Map reverseInterceptors;
    private List defaultInterceptors;
    private InterceptorManager parent;
    
    public InterceptorManagerImp() {
        this(null);
    }

    public InterceptorManagerImp(InterceptorManager parent) {
        this.interceptors = new HashMap();
        this.reverseInterceptors = new HashMap();
        this.defaultInterceptors = new ArrayList();
        this.parent = parent;
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
        
        in.setType( interceptor );
        in.setName( name );
        in.setProperties( new HashMap() );
        in.setDefault( isDefault );
        interceptors.put( name, in );
        reverseInterceptors.put(interceptor, in);
        
        getLogger().info("created interceptor: " + interceptor.getName());
        return new InterceptorBuilder( in, this );
        
    }

    public Interceptor getInterceptor( String name ){
        if( !interceptors.containsKey( name ) ){
            if(parent != null)
                return (Interceptor) parent.getInterceptor( name );
            else
                throw new BrutosException( "interceptor not found: " + name );
        }
        else
            return (Interceptor) interceptors.get( name );
    }

    public Interceptor getInterceptor( Class clazz ){
        if( !reverseInterceptors.containsKey( clazz ) ){
            if(parent != null)
                return (Interceptor) parent.getInterceptor( clazz );
            else
                throw new BrutosException( "interceptor not found: " + clazz.getName() );
        }
        else
            return (Interceptor) reverseInterceptors.get( clazz );
    }
    
    public List getDefaultInterceptors(){
        List tmp;
        
        if(parent != null){
            tmp = new ArrayList(parent.getDefaultInterceptors());
            tmp.addAll(this.defaultInterceptors);
        }
        else
            tmp = this.defaultInterceptors;
            
        return Collections.unmodifiableList(tmp);
    }
    
    public void setParent(InterceptorManager parent){
        this.parent = parent;
    }
    
    public InterceptorManager getParent(){
        return this.parent;
    }
    
    protected Logger getLogger(){
        return LoggerProvider.getCurrentLoggerProvider()
                .getLogger(InterceptorManager.class);
    }
    
}
