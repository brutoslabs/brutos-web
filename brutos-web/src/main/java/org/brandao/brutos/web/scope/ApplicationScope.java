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

import javax.servlet.ServletContext;

import org.brandao.brutos.scope.Scope;

/**
 * 
 * @author Brandao
 */
public class ApplicationScope implements Scope{
    
    private ServletContext context;
    
    public ApplicationScope( ServletContext context ) {
        this.context = context;
    }

    public void put(String name, Object value) {
        context.setAttribute( name, value );
    }

    public Object get(String name) {
        return context.getAttribute( name );
    }

    public Object getCollection(String name) {
        return context.getAttribute( name );
    }

    public void remove( String name ){
        context.removeAttribute(name);
    }

	@SuppressWarnings("unchecked")
	public List<String> getNamesStartsWith(String value) {
		List<String> result = new ArrayList<String>();
		
		Enumeration<String> names = 
				this.context.getAttributeNames();
		
		while(names.hasMoreElements()){
			String name = names.nextElement();
			if(name.startsWith(value)){
				result.add(name);
			}
		}
		
		return result;
	}

}
