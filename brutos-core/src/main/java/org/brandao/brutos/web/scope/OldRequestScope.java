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

import java.util.Arrays;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.web.ContextLoaderListener;
import org.brandao.brutos.http.BrutosRequest;
import org.brandao.brutos.http.MutableRequest;
import org.brandao.brutos.http.ParameterList;
import org.brandao.brutos.scope.Scope;

/**
 * @deprecated
 * @author Afonso Brandao
 */
public class OldRequestScope implements Scope{
    
    public OldRequestScope() {
    }

    public void put(String name, Object value) {
        ServletRequest request = ContextLoaderListener.currentRequest.get();
        request.setAttribute(name, value);
    }

    public Object get(String name) {
        BrutosRequest request = (BrutosRequest) ContextLoaderListener.currentRequest.get();
        Object value = request.getObject(name);
        value = value == null? ((HttpServletRequest)request).getAttribute(name) : value;
        return value;
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
