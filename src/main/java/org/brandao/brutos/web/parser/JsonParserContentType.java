/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2025 Afonso Brandao. (afonso.rbn@gmail.com)
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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.brandao.brutos.AbstractParserContentType;
import org.brandao.brutos.CodeGenerator;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.MutableRequestParserEvent;
import org.brandao.brutos.web.MediaType;
import org.brandao.brutos.web.RequestBody;
import org.brandao.brutos.web.RequestBodyInputStream;
import org.brandao.brutos.web.StreamCache;
import org.brandao.brutos.web.bean.JsonBeanDecoder;
import org.brandao.jbrgates.JSONDecoder;

/**
 * 
 * @author Brandao
 *
 */
public class JsonParserContentType extends AbstractParserContentType {

	private static final String DEFAULT_CHARSET     	= "ISO8859-1";

	//private static final String MAX_LENGTH_PROPERTY		= "org.brandao.brutos.request.max_length";

	//private static final String DEFAULT_MAX_LENGTH		= "3145728";

	private static final String BUFFER_LENGTH_VAR		= "org.brandao.brutos.request.buffer_length";

	private static final String DEFAULT_BUFFER_LENGTH	= "8192";
	
	@SuppressWarnings("unchecked")
	public void parserContentType(MutableMvcRequest request, 
    		MutableRequestParserEvent requestParserInfo, 
    		CodeGenerator codeGenerator, Properties config) throws org.brandao.brutos.RequestParserException {
		
		try{
        	MediaType requestDataType                     = (MediaType)request.getType();
        	Map<String,String> vars                       = requestDataType == null? null : requestDataType.getParams();
            //Long maxRequestBodyLength                     = Long.parseLong(config.getProperty(MAX_LENGTH_PROPERTY, DEFAULT_MAX_LENGTH));
            Integer bufferLength                          = Integer.parseInt(config.getProperty(BUFFER_LENGTH_VAR, DEFAULT_BUFFER_LENGTH));
        	String charsetName                            = vars != null? vars.get("charset") : DEFAULT_CHARSET;
        	charsetName                                   = charsetName == null? request.getEncoding() : charsetName;
        	charsetName                                   = charsetName == null? DEFAULT_CHARSET : charsetName;
        	InputStream stream                            = request.getStream();
			StreamCache streamCache                       = new StreamCache(bufferLength);
			
			Map<String,Object> data;
			
			try(RequestBodyInputStream requestBodyInputStream = new RequestBodyInputStream(stream, streamCache)){
		        JSONDecoder decoder = new JSONDecoder(requestBodyInputStream);
		        data = (Map<String, Object>) decoder.decode();
		        
		        if(data == null) {
		        	data = new HashMap<>();
		        }
		        
			}

			data.put(RequestBody.REQUEST_BODY_PROPERTY, new RequestBody(streamCache, requestParserInfo.getBytesRead(), charsetName));
			
	        JsonBeanDecoder beanDecoder = new JsonBeanDecoder();
	        beanDecoder.setCodeGenerator(codeGenerator);
	        super.parser(request, requestParserInfo, beanDecoder, config, data);
		}
		catch(org.brandao.brutos.RequestParserException e){
			throw e;
		}
		catch(Throwable e){
			throw new org.brandao.brutos.RequestParserException(e);
		}
		
	}
	
}
