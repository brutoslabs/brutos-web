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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.InterceptorStack;

/**
 * Classe usada para construir uma pilha de interceptadores. Com essa classe �
 * poss�vel determinar os par�metros de configura��o de cada interceptador da
 * pilha.
 * <pre>
 * Ex:
 *
 * interceptorManager
 *     .addInterceptor( "myInterceptorName", MyInterceptor.class, false );
 *
 * interceptorManager
 *     .addInterceptor( "myInterceptorName2", MyInterceptor2.class, false )
 *          .addParameter( "param", "value2" );
 *
 * interceptorManager.addInterceptorStack( "myStack", false )
 *      .addInterceptor( "myInterceptorName" )
 *          .addParameter( "param", "value" );
 *      .addInterceptor( "myInterceptorName2" )
 *          .addParameter( "param", "value" );
 *
 * </pre>
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

    /**
     * Inclui um novo interceptador na pilha. O interceptador deve ser previamente
     * criado.
     * @param interceptorName Nome do interceptador.
     * @return Construtor da pilha de interceptadores.
     */
    public InterceptorStackBuilder addInterceptor( String interceptorName ){
        Interceptor in = manager.getInterceptor( interceptorName );
        
        current = new Interceptor( in );
        current.setProperties( new HashMap() );
        
        Set keys = in.getProperties().keySet();
        Iterator iKeys = keys.iterator();
        while( iKeys.hasNext() ){
            String key = (String) iKeys.next();
        //for( String key: keys ){
            Object value = in.getProperties().get( key );
            current.getProperties().put( /*in.getName() + "." +*/ key, value );
        }
        
        ((InterceptorStack)interceptor).addInterceptor( current );
        return this;
    }
    
    /**
     * Inclui um novo par�metro.
     * @param name Nome do par�metro.
     * @param value Valor do Par�metro
     * @return Construtor da pilha de interceptadores.
     */
    public InterceptorStackBuilder addParameter( String name, String value ){
        
        if( current == null )
            throw new BrutosException( "addInterceptor() is not invoked!" );
        
        /*interceptor*/current.setProperty( /*current.getName() + "." +*/ name, value );
        return this;
    }
    
}
