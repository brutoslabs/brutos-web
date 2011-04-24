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

/**
 *
 * @author Afonso Brandao
 */
public class ThreadScope implements Scope{

    private static final ThreadLocal threadLocal;

    static{
        threadLocal = new ThreadLocal();
    }

    public ThreadScope() {
    }

    public static boolean create(){
        
        if( threadLocal.get() == null ){
            threadLocal.set(new HashMap());
            return true;
        }
        else
            return false;


    }

    public static void destroy(){
        threadLocal.remove();
    }

    public void put(String name, Object value){
        HashMap map = (HashMap) threadLocal.get();
        map.put(name, value);
    }

    public Object get(String name) {
        HashMap map = (HashMap) threadLocal.get();
        return map.get(name);
    }

    public Object getCollection( String name ){
        return get( name );
    }

    public void remove( String name ){
        HashMap map = (HashMap) threadLocal.get();
        map.remove(name);
    }
    
}
