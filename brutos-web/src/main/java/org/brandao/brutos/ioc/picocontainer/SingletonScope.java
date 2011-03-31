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
public class SingletonScope implements Scope{

    private Map<String,Object> data;

    public SingletonScope() {
        this.data = new HashMap<String,Object>();
    }

    public void put(String name, Object value) {
        data.put( name, value );
    }

    public Object get(String name) {
        return data.get( name );
    }
    
}
