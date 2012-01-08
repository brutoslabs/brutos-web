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
 * Descreve os escopos.
 * 
 * @author Afonso Brandao
 */
public class ScopeType {

    public static final ScopeType REQUEST     = new ScopeType( "request" );
    public static final ScopeType SINGLETON   = new ScopeType( "singleton" );
    public static final ScopeType PARAM       = new ScopeType( "param" );
    public static final ScopeType THREAD      = new ScopeType( "thread" );
    public static final ScopeType IOC         = new ScopeType( "ioc" );

    protected final static Map defaultScopes = new HashMap();

    static{
        defaultScopes.put( REQUEST.toString(),   REQUEST );
        defaultScopes.put( THREAD.toString(),    THREAD );
        defaultScopes.put( PARAM.toString(),     PARAM );
        defaultScopes.put( SINGLETON.toString(), SINGLETON );
        defaultScopes.put( IOC.toString(),       IOC );
    }

    private String name;

    public ScopeType( String name ){
        this.name = name;
    }
            
    public String toString(){
        return this.name;
    }

    public static ScopeType valueOf( String value ){
        if( defaultScopes.containsKey(value) )
            return (ScopeType)defaultScopes.get( value );
        else
            return new ScopeType( value );
    }
}
