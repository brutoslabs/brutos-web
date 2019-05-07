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

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.WebMvcRequest;
import org.brandao.brutos.web.http.BrutosRequest;

/**
 * 
 * @author Brandao
 */
public class OldRequestScope implements Scope{
    
    public OldRequestScope() {
    }

    public void put(String name, Object value) {
        ServletRequest request = getServletRequest();
        request.setAttribute(name, value);
    }

    public Object get(String name) {
        BrutosRequest request = (BrutosRequest)getServletRequest();
        Object value = request.getObject(name);
        value = value == null? ((HttpServletRequest)request).getAttribute(name) : value;
        return value;
    }

    public Object getCollection( String name ){
        BrutosRequest request = (BrutosRequest)getServletRequest();
        return request.getObjects(name);
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

	public List<String> getNamesStartsWith(String value) {
		// TODO Auto-generated method stub
		return null;
	}

}
