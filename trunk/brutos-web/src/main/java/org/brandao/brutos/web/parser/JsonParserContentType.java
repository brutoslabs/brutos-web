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

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.brandao.brutos.AbstractParserContentType;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.MutableRequestParserEvent;
import org.brandao.brutos.web.bean.JsonBeanDecoder;
import org.brandao.jbrgates.JSONDecoder;

/**
 * 
 * @author Brandao
 *
 */
public class JsonParserContentType extends AbstractParserContentType{

	public JsonParserContentType(){
		super.beanDecoder = new JsonBeanDecoder();
	}
	
	@SuppressWarnings("unchecked")
	public void parserContentType(MutableMvcRequest request, 
    		MutableRequestParserEvent requestParserInfo, 
    		Properties config) throws org.brandao.brutos.RequestParserException {
		
		try{
			InputStream stream = request.getStream();
			//String charset = params.get("charset");
	        JSONDecoder decoder     = new JSONDecoder(stream);
	        Map<String,Object> data = (Map<String,Object>)decoder.decode();
	        
            for(String p: data.keySet()){
            	request.setParameter(p, data.get(p) );
            }
	        
	        super.parser(request, requestParserInfo, config, data);
		}
		catch(Throwable e){
			throw new org.brandao.brutos.RequestParserException(e);
		}
		
	}
	/*
	public void parserContentType(MutableMvcRequest request, 
    		MutableRequestParserEvent requestParserInfo, 
    		Properties config) throws org.brandao.brutos.RequestParserException {
		try{
			InputStream stream = request.getStream();
			//String charset = params.get("charset");
			
	        JSONDecoder decoder = new JSONDecoder(stream);
	        
	        Map<String, Object> data = (Map<String, Object>)decoder.decode();
	        
	        if( data != null ){
	        	addValues((MutableMvcRequest)request, null, data);
	        }
		}
		catch(Throwable e){
			throw new org.brandao.brutos.RequestParserException(e);
		}
		
	}
	
	private void addValues(MutableMvcRequest request, String prefix, Map<String, Object> data){
		for(String fieldName: data.keySet()){
			
			String fullFieldName = 
					prefix == null? 
							fieldName : 
							prefix + "." + fieldName;
			
			Object value = data.get(fieldName);
			this.addValue(request, fullFieldName, value);
		}
	}

	private void addValue(MutableMvcRequest request, String fullFieldName, Object value){
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
	*/
}
