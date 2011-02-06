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

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.HandlerApplicationContext;

/**
 *
 * @author Afonso Brandao
 */
public class IOCScope implements Scope{

    private HandlerApplicationContext app;
    
    public IOCScope( HandlerApplicationContext app ) {
        this.app = app;
    }

    public void put(String name, Object value){
    }

    public Object get(String name) {
        return app.getIocProvider().getBean(name);
    }

    public Object getCollection( String name ){
        return get( name );
    }

    public void remove( String name ){
    }
    
}
