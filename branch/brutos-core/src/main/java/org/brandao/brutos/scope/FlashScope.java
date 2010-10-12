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

package org.brandao.brutos.scope;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ContextLoaderListener;

/**
 *
 * @author Afonso Brandao
 */
public class FlashScope implements Scope{
    
    public FlashScope() {
    }

    public void put(String name, Object value) {
        ServletRequest request = ContextLoaderListener.currentRequest.get();

        FlashInstrument instrument =
                getInstrument(
                    ((HttpServletRequest)request).getSession() );

        instrument.put(name, value);
    }

    public Object get(String name){
        ServletRequest request = ContextLoaderListener.currentRequest.get();

        FlashInstrument instrument =
                getInstrument(
                    ((HttpServletRequest)request).getSession() );
        
        return instrument.get( name );
    }

    public Object getCollection( String name ){
        return get(name);
    }

    public void remove( String name ){
        ServletRequest request = ContextLoaderListener.currentRequest.get();

        FlashInstrument instrument =
                getInstrument(
                    ((HttpServletRequest)request).getSession() );

        instrument.remove( name );
    }

    private FlashInstrument getInstrument( HttpSession session ){
        if( session.getAttribute( BrutosConstants.FLASH_INSTRUMENT ) == null ){
            FlashInstrument instrument = create();
            session.setAttribute( BrutosConstants.FLASH_INSTRUMENT , instrument);
            return instrument;
        }
        else
            return (FlashInstrument)
                    session.getAttribute( BrutosConstants.FLASH_INSTRUMENT );
    }

    private FlashInstrument create(){
        return new FlashInstrument();
    }
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