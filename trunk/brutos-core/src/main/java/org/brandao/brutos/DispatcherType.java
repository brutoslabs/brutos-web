/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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
import java.util.Map;

/**
 * Descreve as op��es de direcionamento de fluxo para a vis�o.
 * 
 * @author Afonso Brandao
 */
public class DispatcherType {

    /**
     * Inclui a vis�o no resultado.
     */
    public static final DispatcherType INCLUDE  = new DispatcherType( "include" );

    /**
     *  A requisi��o � encaminhada para a vis�o.
     */
    public static final DispatcherType FORWARD  = new DispatcherType( "forward" );

    /**
     *  A requisi��o � redirecionada para a vis�o.
     */
    public static final DispatcherType REDIRECT = new DispatcherType( "redirect" );

    private static final Map defaultDispatcher = new HashMap();

    static{
        defaultDispatcher.put( INCLUDE.toString() , INCLUDE );
        defaultDispatcher.put( FORWARD.toString() , FORWARD );
        defaultDispatcher.put( REDIRECT.toString() , REDIRECT );
    }

    private String name;

    public DispatcherType( String name ){
        this.name = name;
    }
            
    public String toString(){
        return this.name;
    }

    public static DispatcherType valueOf( String value ){
        if(value == null)
            return null;
        else
        if( defaultDispatcher.containsKey(value) )
            return (DispatcherType)defaultDispatcher.get( value );
        else
            return new DispatcherType( value );
    }
}
