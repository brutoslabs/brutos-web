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

package org.brandao.brutos.ioc.picocontainer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Afonso Brandao
 */
public class PicoContainerScopes{

    private static Map<String,Scope> scopes;

    static{
        scopes = new HashMap<String,Scope>();
        register( "singleton", new SingletonScope() );
        register( "prototype", new ProtoTypeScope() );
    }
    
    public PicoContainerScopes() {
    }

    public static void register( String id, Scope scope ){
        scopes.put( id, scope );
    }

    public static void remove( String id ){
        scopes.remove( id );
    }

    public static Scope get( String id ){
        return scopes.get( id );
    }
}
