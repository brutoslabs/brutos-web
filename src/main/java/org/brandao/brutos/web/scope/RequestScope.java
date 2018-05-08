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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.http.ParameterList;

/**
 * 
 * @author Brandao
 */
public class RequestScope implements Scope{

	private static final ThreadLocal<MutableMvcRequest> currentRequest =
			new ThreadLocal<MutableMvcRequest>();
	
    public static void setRequest(MutableMvcRequest value){
    	currentRequest.set(value);
    }

    public static void removeRequest(MutableMvcRequest value){
    	currentRequest.remove();
    }

    public void put(String name, Object value) {
    	MutableMvcRequest request = currentRequest.get();
        request.setProperty(name, value);
    }

    public Object get(String name) {
    	MutableMvcRequest request = currentRequest.get();
        return request.getProperty(name);
    }

    public Object getCollection( String name ){
    	MutableMvcRequest request = currentRequest.get();
        return new ParameterList(
                Arrays.asList(request.getProperty(name)));
    }

    public void remove( String name ){
    }

	public List<String> getNamesStartsWith(String value) {
    	MutableMvcRequest request = currentRequest.get();
		
		List<String> result = new ArrayList<String>();
		
		Set<String> names = 
				request.getPropertiesNames();
		
		for(String name: names){
			if(name.startsWith(value)){
				result.add(name);
			}
		}
		
		return result;
	}
	
}
