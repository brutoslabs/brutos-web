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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.InterceptorStack;

/**
 * Classe usada para configurar interceptadores.
 * O uso de interceptadores é necessário quando precisamos executar tarefas
 * antes e/ou depois do controlador ser executado, normalmente usado para
 * controle de acesso, validação de dados, controle de transação e geração
 * de log.
 * <p>É possível trabalhar com um ou mais interceptadores, podendo definir
 * quais recursos serão interceptados, a ordem com que serão executados e os
 * parâmetros necessários para a sua configuração.</p>
 * <p>Sua instância é controlada pelo container IOC, podendo assim, receber a
 * injeção de objetos por construtor ou método.</p>
 *
 * <pre>
 * Ex:
 *
 * public class MyInterceptor extends AbstractInterceptor{
 *
 *     public void intercepted( InterceptorStack stack,
 *        InterceptorHandler handler ) throws InterceptedException{
 *         ...
 *     }
 *
 * }
 * interceptorManager
 *     .addInterceptor( "myInterceptorName", MyInterceptor.class, false );
 * </pre>
 * 
 * <pre>
 * Ex2:
 * 
 * interceptorManager
 *     .addInterceptor( "myInterceptorName", MyInterceptor.class, false );
 * interceptorManager
 *     .addInterceptorStack( "myStack", false )
 *     .addInterceptor( "myInterceptorName" );
 * </pre>
 *
 * @author Afonso Brandao
 */
public class InterceptorManager {
    
    private Map<String, Interceptor> interceptors;
    private List<Interceptor> defaultInterceptors;
    
    public InterceptorManager() {
        this.interceptors = new HashMap();
        this.defaultInterceptors = new ArrayList();
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
        return new InterceptorBuilder( in, this );
        
    }
    
    public Interceptor getInterceptor( String name ){
        if( !interceptors.containsKey( name ) )
            throw new BrutosException( "interceptor not found: " + name );
        else
            return interceptors.get( name );
    }
    
    public List<Interceptor> getDefaultInterceptors(){
        return this.defaultInterceptors;
    }
    
}
