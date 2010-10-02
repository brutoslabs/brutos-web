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

import javax.servlet.ServletRequest;
import org.brandao.brutos.ContextLoaderListener;
import org.brandao.brutos.http.BrutosRequest;
import org.brandao.brutos.http.MutableRequest;

/**
 *
 * @author Afonso Brandao
 */
public class RequestScope implements Scope{
    
    public RequestScope() {
    }

    public void put(String name, Object value) {
        ServletRequest request = ContextLoaderListener.currentRequest.get();
        request.setAttribute(name, value);
    }

    public Object get(String name) {
        BrutosRequest request = (BrutosRequest) ContextLoaderListener.currentRequest.get();
        return request.getObject(name);
    }

    public Object getCollection( String name ){
        BrutosRequest request = (BrutosRequest) ContextLoaderListener.currentRequest.get();
        return request.getObjects(name);
    }

    public void remove( String name ){
        ServletRequest request = ContextLoaderListener.currentRequest.get();
        request.removeAttribute(name);
    }
}
