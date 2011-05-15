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
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.web.http.ParameterList;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.AbstractWebApplicationContext;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.WebMvcRequest;

/**
 *
 * @author Afonso Brandao
 */
public class RequestScope implements Scope{
    
    public RequestScope() {
    }

    public void put(String name, Object value) {
        ServletRequest request = getServletRequest();
        request.setAttribute(name, value);
    }

    public Object get(String name) {
        ServletRequest request = getServletRequest();
        return request.getAttribute(name);
    }

    public Object getCollection( String name ){
        ServletRequest request = getServletRequest();
        return new ParameterList(
                Arrays.asList(request.getParameterValues(name)));
    }

    public void remove( String name ){
        ServletRequest request = getServletRequest();
        request.removeAttribute(name);
    }

    private ServletRequest getServletRequest(){
        WebApplicationContext context =
                ContextLoader.getCurrentWebApplicationContext();

        MvcRequest request = context.getMvcRequest();
        if( !(request instanceof WebMvcRequest) )
            throw new BrutosException( "invalid web request: " +
                    request.getClass() );

        return ((WebMvcRequest)request).getServletRequest();
    }

}
