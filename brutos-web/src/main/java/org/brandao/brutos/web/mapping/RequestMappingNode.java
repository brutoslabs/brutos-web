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

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.StringPattern;

public class RequestMappingNode{
	
	private String value;

	private StringPattern pattern;
	
	private boolean staticValue;
	
	private Map<String, RequestMappingNode> staticNext;
	
	private Set<RequestMappingNode> dynamicNext;
	
	private Map<RequestMethodType, RequestMappingEntry> requestMethodTypes;
	
	public RequestMappingNode(){
		this.dynamicNext = new HashSet<RequestMappingNode>();
		this.staticNext  = new HashMap<String, RequestMappingNode>();
		this.pattern     = null;
	}
	
	public Map<String,List<String>> getRequestParameters(MutableMvcRequest request, String value){
		return this.pattern == null? null : this.pattern.getParameters(value);

		/*
        for(String key: params.keySet() ){
        	for(String v: params.get(key)){
        		request.setParameter(key, v);
        	}
        }
		*/
	}
	
	public RequestMappingNode add(String value, 
			RequestMappingEntry requestEntry) throws MalformedURLException{
		
		RequestMappingNode node = new RequestMappingNode();
		node.setValue(value);
		node.setStaticValue(value == null || value.indexOf("{") == -1);
		
		if(node.isStaticValue()){
			this.staticNext.put(value, node);
		}
		else{
			node.setPattern(new StringPattern(value));
			this.dynamicNext.add(node);
		}
		
		return node;
	}

	public void remove(String value){
		
		RequestMappingNode node = new RequestMappingNode();
		node.setValue(value);
		
		if(value == null || value.startsWith("{")){
			this.staticNext.remove(value);
		}
		else{
			this.dynamicNext.remove(node);
		}
		
	}

	public RequestMappingNode getNextNode(String value){
		
		RequestMappingNode next = this.staticNext.get(value);
		
		if(next != null){
			return next;
		}
		
		for(RequestMappingNode dynamicNode: this.dynamicNext){
			if(dynamicNode.value.equals(value)){
				return dynamicNode;
			}
		}
		
		return null;
	}
	
	public RequestMappingNode getNext(String value){
		
		if(!this.staticNext.isEmpty()){
			RequestMappingNode next = this.staticNext.get(value);
			if(next != null){
				return next;
			}
		}
		
		for(RequestMappingNode dynamicNode: this.dynamicNext){
			if(dynamicNode.pattern.matches(value)){
				return dynamicNode;
			}
		}
		
		return null;
	}

	public boolean isEmpty(){
		return this.dynamicNext.isEmpty() && this.staticNext.isEmpty();
	}
	
	public StringPattern getPattern() {
		return pattern;
	}

	public void setPattern(StringPattern pattern) {
		this.pattern = pattern;
	}

	public RequestMappingEntry getRequestEntry(RequestMethodType value) {
		return requestMethodTypes == null? null : requestMethodTypes.get(value);
	}

	public boolean putRequestEntry(RequestMethodType requestMethodType, 
			RequestMappingEntry value) {
		if(this.requestMethodTypes == null){
			this.requestMethodTypes = 
					new HashMap<RequestMethodType, RequestMappingEntry>();
		}
		
		if(this.requestMethodTypes.containsKey(requestMethodType)){
			return false; 
		}
		
		this.requestMethodTypes.put(requestMethodType, value);
		return true;
	}

	public boolean removeRequestEntry(RequestMethodType value) {
		
		if(requestMethodTypes == null){
			return false;
		}

		if(!this.requestMethodTypes.containsKey(value)){
			return false;
		}
		
		this.requestMethodTypes.remove(value);
		return true;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isStaticValue() {
		return staticValue;
	}

	public void setStaticValue(boolean staticValue) {
		this.staticValue = staticValue;
	}

	public Map<String, RequestMappingNode> getStaticNext() {
		return staticNext;
	}

	public void setStaticNext(Map<String, RequestMappingNode> staticNext) {
		this.staticNext = staticNext;
	}

	public Set<RequestMappingNode> getDynamicNext() {
		return dynamicNext;
	}

	public void setDynamicNext(Set<RequestMappingNode> dynamicNext) {
		this.dynamicNext = dynamicNext;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		RequestMappingNode other = (RequestMappingNode) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}