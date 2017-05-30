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

package org.brandao.brutos.mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DataType;

public class DataTypeMap {

	private Map<String, DataType> map;
	
	private Set<DataType> set;
	
	public DataTypeMap(){
		this.map    = new HashMap<String, DataType>();
		this.set    = new HashSet<DataType>();
	}

	public DataTypeMap(Set<DataType> value){
		this.map    = new HashMap<String, DataType>();
		
		for(DataType v: value){
			this.add(v);
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
		
		if(this.map.containsKey(value.getName())){
			throw new BrutosException("media type has been added: " + value.getName());
		}
		this.map.put(value.getName(), value);
	}
	
	public void remove(DataType value){
		
		if(!this.map.containsKey(value.getName())){
			throw new BrutosException("subtype not found: " + value.getName());
		}

	}
	
	public boolean accept(DataType value){
		return this.map.containsKey(value.getName());
	}

	public DataType accept(DataTypeMap map){
		
		Set<DataType> types = map.set;
		
		for(DataType type: types){
			if(this.accept(type)){
				return type;
			}
		}
		
		return null;
	}
	
	public boolean contains(DataType value){
		return this.map.containsKey(value.getName());
	}
	
	public Set<DataType> getSet(){
		return Collections.unmodifiableSet(this.set);
	}
	
}
