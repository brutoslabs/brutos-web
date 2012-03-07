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

import org.brandao.brutos.scope.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;

/**
 *
 * @author Afonso Brandao
 */
public class Scopes{

    private Map scopes;

    public Scopes() {
        scopes = new HashMap();
    }

    public void register( String id, Scope scope ){

        if( getLogger().isInfoEnabled() )
            getLogger().info(
                (scopes.containsKey(id)?
                    "override scope: " :
                    "registred scope: ") + id );
        
        scopes.put( id, scope );
    }

    public void remove( String id ){
        if( getLogger().isInfoEnabled() )
            getLogger().info( "removed scope: " + id );

        scopes.remove( id );
    }

    public Scope get( String id ){
        return (Scope) scopes.get( id );
    }

    public Scope get( ScopeType scopeId ){
        return get( scopeId.toString() );
    }

    public Map getScopes(){
        return Collections.unmodifiableMap(scopes);
    }

    void clear(){
        scopes.clear();
    }
    
    public Logger getLogger(){
        return 
            LoggerProvider.getCurrentLoggerProvider()
                .getLogger(Scopes.class.getName());
    }
    
    /*
    public static Scopes getScopesOfCurrentApplicationContext(){
        return Invoker
                .getCurrentApplicationContext().getScopes();
    }
    */
}
