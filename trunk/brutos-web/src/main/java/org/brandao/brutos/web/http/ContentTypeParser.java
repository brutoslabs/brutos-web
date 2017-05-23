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

package org.brandao.brutos.web.http;

import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.web.RequestParserException;

/**
 * 
 * @author Brandao
 *
 */
@Deprecated
public class ContentTypeParser {

	public static final String TYPE_FIELD = "type";
	
	public Map<String,String> parse(String value) throws RequestParserException{
	
		Map<String,String> result = new HashMap<String, String>();
		
		if(value == null){
			return result;
		}
		
		String[] parts = value.split(";");
		
		if(parts.length < 1){
			throw new RequestParserException("expected type");
		}
		
		String type = this.prepareValue(parts[0]);
		
		if(type == null){
			throw new RequestParserException("type is null");
		}
		
		result.put(TYPE_FIELD, type);
		
		for(int i=1;i<parts.length;i++){
			String part = parts[i];
			String[] keyValue = part.split("\\=");
			
			if(keyValue.length != 2){
				throw new RequestParserException("expected <key>=<value>");
			}
			
			String key = this.prepareValue(keyValue[0]);
			String val = this.prepareValue(keyValue[1]);
			
			result.put(key, val);
		}
		
		return result;
	}
	
	private String prepareValue(String value){
		return value == null? null : value.trim();
	}
}
