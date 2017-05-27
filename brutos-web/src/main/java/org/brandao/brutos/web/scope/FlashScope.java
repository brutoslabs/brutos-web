/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.web.scope;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.BrutosWebConstants;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.WebScopeType;

/**
 * 
 * @author Brandao
 */
public class FlashScope implements Scope{
    
    public FlashScope() {
    }

    public void put(String name, Object value) {
        WebApplicationContext context =
                ContextLoader.getCurrentWebApplicationContext();
        //ServletRequest request = ContextLoaderListener.currentRequest.get();

        Scope session = context.getScopes().get(WebScopeType.SESSION);

        FlashInstrument instrument =
                getInstrument( session );

        instrument.put(name, value);
    }

    public Object get(String name){
        WebApplicationContext context =
                ContextLoader.getCurrentWebApplicationContext();
        //ServletRequest request = ContextLoaderListener.currentRequest.get();

        Scope session = context.getScopes().get(WebScopeType.SESSION);

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

        Scope session = context.getScopes().get(WebScopeType.SESSION);

        FlashInstrument instrument =
                getInstrument( session );

        instrument.remove( name );
    }

	public List<String> getNamesStartsWith(String value) {
        WebApplicationContext context =
                ContextLoader.getCurrentWebApplicationContext();
        //ServletRequest request = ContextLoaderListener.currentRequest.get();

        Scope session = context.getScopes().get(WebScopeType.SESSION);

        FlashInstrument instrument =
                getInstrument( session );
        
        return instrument.getNamesStartsWith(value);
	}
    
    private FlashInstrument getInstrument( Scope session){
        if( session.get( BrutosWebConstants.FLASH_INSTRUMENT ) == null ){
            FlashInstrument instrument = create();
            session.put( BrutosWebConstants.FLASH_INSTRUMENT , instrument);
            return instrument;
        }
        else
            return (FlashInstrument)
                    session.get( BrutosWebConstants.FLASH_INSTRUMENT );
    }

    private FlashInstrument create(){
        return new FlashInstrument();
    }

    class FlashInstrument implements Scope{

        private final ConcurrentMap<String,Object> data;

        public FlashInstrument() {
            this.data = new ConcurrentHashMap<String, Object>();
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

    	public List<String> getNamesStartsWith(String value) {
    		List<String> result = new ArrayList<String>();
    		for(String k: data.keySet()){
    			if(k.startsWith(value)){
    				result.add(k);
    			}
    		}
    		return result;
    	}
        
    }

}

