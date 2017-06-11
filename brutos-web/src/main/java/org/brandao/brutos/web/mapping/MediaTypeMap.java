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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DataType;
import org.brandao.brutos.mapping.DataTypeMap;
import org.brandao.brutos.web.MediaType;

public class MediaTypeMap extends DataTypeMap{

	private Map<String, Map<String, MediaType>> map;
	
	private boolean hasAll;
	
	private Set<DataType> set;

	private Set<DataType> publicSet;
	
	public MediaTypeMap(){
		this.map       = new HashMap<String, Map<String, MediaType>>();
		this.hasAll    = false;
		this.set       = new HashSet<DataType>();
		this.publicSet = Collections.unmodifiableSet(this.set);
	}

	public MediaTypeMap(Set<DataType> value){
		this.map    = new HashMap<String, Map<String, MediaType>>();
		this.hasAll = false;
		
		for(DataType v: value){
			this.add((MediaType)v);
		}
		
	}
	
	public boolean isEmpty(){
		return set.isEmpty();
	}
	
	public void clear(){
		set.clear();
		map.clear();
	}
	
	public void add(DataType value){
		MediaType mediaType = (MediaType)value;
		String type         = mediaType.getType();
		String subtype      = mediaType.getSubType();
		
		Map<String, MediaType> subtypes = this.map.get(type);
		
		if(subtypes == null){
			subtypes = new HashMap<String, MediaType>();
			subtypes.put(subtype, mediaType);
			this.set.add(mediaType);
		}
		else{
			if(subtypes.containsKey(subtype)){
				throw new BrutosException("media type has been added");
			}
			else{
				subtypes.put(subtype, mediaType);
				this.set.add(mediaType);
			}
		}

		if(type.equals("*") && subtype.equals("*")){
			this.hasAll = true;
		}
		
	}
	
	public void remove(DataType value){
		MediaType mediaType = (MediaType)value;
		String type         = mediaType.getType();
		String subtype      = mediaType.getSubType();
		
		Map<String, MediaType> subtypes = this.map.get(type);
		
		if(subtypes == null){
			throw new BrutosException("type not found: " + type);
		}
		else{
			if(!subtypes.containsKey(subtype)){
				throw new BrutosException("subtype not found: " + subtype);
			}
			else{
				subtypes.remove(subtype);
				if(subtypes.isEmpty()){
					this.map.remove(type);
					this.set.remove(mediaType);
				}
			}
		}

		if(type.equals("*") && subtype.equals("*")){
			this.hasAll = false;
		}
		
	}
	
	public MediaType getMatch(MediaType value){
		MediaType mediaType = (MediaType)value;
		String type         = mediaType.getType();
		String subtype      = mediaType.getSubType();
		
		if(type.equals("*")){
			
			if(!subtype.equals("*")){
				throw new IllegalStateException("invalid subtype: " + subtype);
			}
			
			return (MediaType)this.set.iterator().next();
		}
		
		Map<String, MediaType> subtypes = this.map.get(type);
		
		if(subtypes != null){
			
			if(!subtype.equals("*")){
				return subtypes.values().iterator().next();
			}
			else{
				return subtypes.get(subtype);
			}
			
		}
		
		return null;
		
	}
	
	public boolean accept(MediaType value){
		
		if(value == null){
			return false;
		}
		
		if(this.hasAll){
			return true;
		}

		MediaType mediaType = (MediaType)value;
		String type         = mediaType.getType();
		String subtype      = mediaType.getSubType();
		
		if(type.equals("*") && !this.map.isEmpty()){
			return true;
		}
		
		Map<String, MediaType> subtypes = this.map.get(type);
		
		if(subtypes == null){
			return false;
		}
		else{
			return subtype.equals("*") || subtypes.containsKey(subtype);
		}
		
	}
	
	public boolean contains(MediaType value){
		MediaType mediaType = (MediaType)value;
		String type         = mediaType.getType();
		String subtype      = mediaType.getSubType();
		
		Map<String, MediaType> subtypes = this.map.get(type);
		
		if(subtypes == null){
			return false;
		}
		else{
			return subtypes.containsKey(subtype);
		}
		
	}
	
	public Set<DataType> getSet(){
		return this.publicSet;
	}
	
}
