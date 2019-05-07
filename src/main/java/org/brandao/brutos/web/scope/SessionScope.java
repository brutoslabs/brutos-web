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
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.brandao.brutos.scope.Scope;

/**
 * 
 * @author Brandao
 */
public class SessionScope implements Scope{
    
	private static final ThreadLocal<HttpServletRequest> currentRequest =
		new ThreadLocal<HttpServletRequest>();
	
    public SessionScope() {
    }

    public static void setServletRequest(HttpServletRequest value){
    	currentRequest.set(value);
    }

    public static void removeServletRequest(HttpServletRequest value){
    	currentRequest.remove();
    }
    
    public void put(String name, Object value) {
    	HttpServletRequest request = currentRequest.get();
        HttpSession session = request.getSession();
        session.setAttribute( name, value );
    }

    public Object get(String name) {
    	HttpServletRequest request = currentRequest.get();
        HttpSession session = request.getSession();
        return session.getAttribute( name );
    }

    public Object getCollection( String name ){
        return get( name );
    }

    public void remove( String name ){
    	HttpServletRequest request = currentRequest.get();
        HttpSession session = request.getSession();
        session.removeAttribute( name );
    }

	@SuppressWarnings("unchecked")
	public List<String> getNamesStartsWith(String value) {
    	HttpServletRequest request = currentRequest.get();
        HttpSession session = request.getSession();
        
		List<String> result = new ArrayList<String>();
		
		Enumeration<String> names = 
				session.getAttributeNames();
		
		while(names.hasMoreElements()){
			String name = names.nextElement();
			if(name.startsWith(value)){
				result.add(name);
			}
		}
		
		return result;
	}

}
