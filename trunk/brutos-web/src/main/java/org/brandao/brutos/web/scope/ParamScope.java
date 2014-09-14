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

import javax.servlet.ServletRequest;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.web.ContextLoaderListener;
import org.brandao.brutos.web.http.BrutosRequest;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.AbstractWebApplicationContext;
import org.brandao.brutos.web.RequestInfo;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.WebMvcRequest;

/**
 *
 * @author Afonso Brandao
 */
public class ParamScope implements Scope{
    
    public ParamScope() {
    }

    private ServletRequest getServletRequest(){
        RequestInfo requestInfo = RequestInfo.getCurrentRequestInfo();
        return requestInfo.getRequest();
        /*WebApplicationContext context =
                ContextLoader.getCurrentWebApplicationContext();

        MvcRequest request = context.getMvcRequest();
        if( !(request instanceof WebMvcRequest) )
            throw new BrutosException( "invalid web request: " +
                    request.getClass() );

        return ((WebMvcRequest)request).getServletRequest();
        */
    }

    public void put(String name, Object value) {
        BrutosRequest request = 
                (BrutosRequest)getServletRequest();
        request.setObject(name, value);

        //ServletRequest request = ContextLoaderListener.currentRequest.get();
        //request.setAttribute(name, value);
    }

    public Object get(String name) {
        BrutosRequest request =
                (BrutosRequest)getServletRequest();
        return request.getObject(name);
    }

    public Object getCollection( String name ){
        BrutosRequest request =
                (BrutosRequest)getServletRequest();
        return request.getObjects(name);
    }

    public void remove( String name ){
        //ServletRequest request = ContextLoaderListener.currentRequest.get();
        //request.removeAttribute(name);
    }
}
