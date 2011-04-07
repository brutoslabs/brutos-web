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

package org.brandao.brutos.web.scope;

import java.util.HashMap;
import java.util.Map;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.WebApplicationContext;

/**
 *
 * @author Afonso Brandao
 */
public class FlashScope implements Scope{
    
    public FlashScope() {
    }

    public void put(String name, Object value) {
        WebApplicationContext context =
                ContextLoader.getCurrentWebApplicationContext();
        //ServletRequest request = ContextLoaderListener.currentRequest.get();

        Scope session = context.getScopes().get(ScopeType.SESSION);

        FlashInstrument instrument =
                getInstrument( session );

        instrument.put(name, value);
    }

    public Object get(String name){
        WebApplicationContext context =
                ContextLoader.getCurrentWebApplicationContext();
        //ServletRequest request = ContextLoaderListener.currentRequest.get();

        Scope session = context.getScopes().get(ScopeType.SESSION);

        FlashInstrument instrument =
                getInstrument( session );
                    
        
        return instrument.get( name );
    }

    public Object getCollection( String name ){
        return get(name);
    }

    public void remove( String name ){
        WebApplicationContext context =
                ContextLoader.getCurrentWebApplicationContext();
        //ServletRequest request = ContextLoaderListener.currentRequest.get();

        Scope session = context.getScopes().get(ScopeType.SESSION);

        FlashInstrument instrument =
                getInstrument( session );

        instrument.remove( name );
    }

    private FlashInstrument getInstrument( Scope session){
        if( session.get( BrutosConstants.FLASH_INSTRUMENT ) == null ){
            FlashInstrument instrument = create();
            session.put( BrutosConstants.FLASH_INSTRUMENT , instrument);
            return instrument;
        }
        else
            return (FlashInstrument)
                    session.get( BrutosConstants.FLASH_INSTRUMENT );
    }

    private FlashInstrument create(){
        return new FlashInstrument();
    }

    class FlashInstrument implements Scope{

        private final Map<String,Object> data;

        public FlashInstrument() {
            this.data = new HashMap<String,Object>();
        }

        public void put(String name, Object value) {
            data.put( name, value );
        }

        public Object get(String name) {
            try{
                return data.get( name );
            }
            finally{
                data.remove(name);
            }
        }

        public Object getCollection( String name ){
            return get(name);
        }

        public void remove( String name ){
            data.remove(name);
        }
    }
}

