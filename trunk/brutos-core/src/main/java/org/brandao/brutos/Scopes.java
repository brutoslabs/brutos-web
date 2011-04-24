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

    private Logger logger =
        LoggerProvider.getCurrentLoggerProvider()
            .getLogger(Scopes.class.getName());
    
    private Map<String,Scope> scopes;

    public Scopes() {
        scopes = new HashMap<String,Scope>();
    }

    public void register( String id, Scope scope ){

        if( logger.isInfoEnabled() )
            logger.info(
                (scopes.containsKey(id)?
                    "override scope: " :
                    "registred scope: ") + id );
        
        scopes.put( id, scope );
    }

    public void remove( String id ){
        if( logger.isInfoEnabled() )
            logger.info( "removed scope: " + id );

        scopes.remove( id );
    }

    public Scope get( String id ){
        return scopes.get( id );
    }

    public Scope get( ScopeType scopeId ){
        return get( scopeId.toString() );
    }

    public Map getScopes(){
        return Collections.unmodifiableMap(scopes);
    }

    /*
    public static Scopes getScopesOfCurrentApplicationContext(){
        return Invoker
                .getCurrentApplicationContext().getScopes();
    }
    */
}
