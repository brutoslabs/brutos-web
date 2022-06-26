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

package org.brandao.brutos.web.mapping;

import java.util.List;
import java.util.Map;

import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.StringPattern;

public class RequestMappingEntry{
	
	private String id;
	
	private RequestMethodType requestMethodType;
	
	private Controller controller;
	
	private Action action;
	
	private StringPattern pattern;
	
	public RequestMappingEntry(String id, RequestMethodType requestMethodType, 
			Controller controller, Action action, StringPattern pattern) {
		this.id = id;
		this.controller = controller;
		this.action = action;
		this.requestMethodType = requestMethodType;
		this.pattern = pattern;
	}

	public Controller getController() {
		return controller;
	}

	public Action getAction() {
		return action;
	}

	public RequestMethodType getRequestMethodType() {
		return requestMethodType;
	}

	public String getId() {
		return id;
	}

	public Map<String,List<String>> getRequestParameters(MutableMvcRequest request, String value){
		return this.pattern == null? null : this.pattern.getParameters(value);
	}
	
	public boolean matches(String value) {
		return pattern == null? false : pattern.matches(value);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((requestMethodType == null) ? 0 : requestMethodType.hashCode());
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
		RequestMappingEntry other = (RequestMappingEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (requestMethodType == null) {
			if (other.requestMethodType != null)
				return false;
		} else if (!requestMethodType.equals(other.requestMethodType))
			return false;
		return true;
	}

}
