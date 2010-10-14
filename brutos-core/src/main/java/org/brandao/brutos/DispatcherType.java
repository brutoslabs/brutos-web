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

package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Afonso Brandao
 */
public class DispatcherType {

    public static final DispatcherType INCLUDE  = new DispatcherType( "include" );
    public static final DispatcherType FORWARD  = new DispatcherType( "forward" );
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
        if( defaultDispatcher.containsKey(value) )
            return (DispatcherType)defaultDispatcher.get( value );
        else
            return new DispatcherType( value );
    }
}
