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
