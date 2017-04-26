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

package org.brandao.brutos.web.parser;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletInputStream;

import org.brandao.brutos.web.ParserContentType;
import org.brandao.brutos.web.RequestParserException;
import org.brandao.brutos.web.http.BrutosRequest;
import org.brandao.brutos.web.http.MutableUploadEvent;
import org.brandao.jbrgates.JSONDecoder;

/**
 * 
 * @author Brandao
 *
 */
@SuppressWarnings("unchecked")
public class JsonParserContentType implements ParserContentType{

	public void parserContentType(BrutosRequest request, 
    		MutableUploadEvent uploadEvent, Properties config, 
    		Map<String, String> params) throws RequestParserException {
		try{
			ServletInputStream stream = request.getInputStream();
			//String charset = params.get("charset");
			
	        JSONDecoder decoder = new JSONDecoder(stream);
	        
	        Map<String, Object> data = (Map<String, Object>)decoder.decode();
	        
	        if( data != null ){
	        	addValues(request, null, data);
	        }
		}
		catch(Throwable e){
			throw new RequestParserException(e);
		}
		
	}
	
	private void addValues(BrutosRequest request, String prefix, Map<String, Object> data){
		for(String fieldName: data.keySet()){
			
			String fullFieldName = 
					prefix == null? 
							fieldName : 
							prefix + "." + fieldName;
			
			Object value = data.get(fieldName);
			this.addValue(request, fullFieldName, value);
		}
	}

	private void addValue(BrutosRequest request, String fullFieldName, Object value){
		if(value instanceof Map){
			addValues(request, fullFieldName, (Map<String, Object>)value);
		}
		else
		if(value instanceof List){
			List<Object> list = (List<Object>)value;
			int index = 0;
			for(Object i: list){
				addValue(request, fullFieldName + "[" + index++ + "]", i);
			}
		}
		else
		if(value != null){
			request.setParameter(fullFieldName, String.valueOf(value));
		}
	}
}
