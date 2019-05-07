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

package org.brandao.brutos.web;

import java.util.HashMap;

/**
 * 
 * @author Brandao
 *
 */
public class RequestMethodType {

	public static final RequestMethodType DELETE	= new RequestMethodType("delete",	"delete"); 
    
	public static final RequestMethodType GET		= new RequestMethodType("get",		"get");
	           
	public static final RequestMethodType HEAD		= new RequestMethodType("head",		"head");
	           
	public static final RequestMethodType OPTIONS	= new RequestMethodType("options",	"options");
	           
	public static final RequestMethodType POST		= new RequestMethodType("post",		"post");
	           
	public static final RequestMethodType PUT		= new RequestMethodType("put",		"put");
	           
	public static final RequestMethodType TRACE		= new RequestMethodType("trace",	"trace");
	
	private static final HashMap<String, RequestMethodType> defaultTypes =
		new HashMap<String, RequestMethodType>();
	
	static{
		defaultTypes.put(DELETE.getId(),	DELETE);
		defaultTypes.put(GET.getId(),		GET);
		defaultTypes.put(HEAD.getId(),		HEAD);
		defaultTypes.put(OPTIONS.getId(),	OPTIONS);
		defaultTypes.put(POST.getId(),		POST);
		defaultTypes.put(PUT.getId(),		PUT);
		defaultTypes.put(TRACE.getId(),		TRACE);
	}
	
	public static RequestMethodType valueOf(String value){
		
		if(value == null){
			return null;
		}
		
		value = value.toLowerCase();
		
		RequestMethodType r = defaultTypes.get(value);
		return r == null? new RequestMethodType(value, value) : r;
	}
	
	private final String id;
	
	private final String name;

	public RequestMethodType(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestMethodType other = (RequestMethodType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String toString(){
		return this.id;
	}
	
}
