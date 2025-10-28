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
import java.util.Map;
import java.util.Properties;

import org.brandao.brutos.AbstractParserContentType;
import org.brandao.brutos.CodeGenerator;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.MutableRequestParserEvent;
import org.brandao.brutos.RequestParserException;
import org.brandao.brutos.mapping.BeanDecoder;
import org.brandao.brutos.web.MediaType;
import org.brandao.brutos.web.bean.MultipartFormDataBeanDecoder;
import org.brandao.brutos.web.http.MultipartFormDataParser;
import org.brandao.brutos.web.http.MultipartFormDataParser.Field;

/**
 * 
 * @author Brandao
 *
 */
public class MultipartFormDataParserContentType extends AbstractParserContentType{

	private static final String BOUNDARY				= "boundary";
	
	private static final String DEFAULT_CHARSET     	= "ISO8859-1";
	
	private static final String MAX_LENGTH_PROPERTY		= "org.brandao.brutos.request.max_length";

	private static final String DEFAULT_MAX_LENGTH		= "3145728";

	private static final String BUFFER_LENGTH_VAR		= "org.brandao.brutos.request.buffer_length";

	private static final String DEFAULT_BUFFER_LENGTH	= "8192";
	
	//private static final String PATH_PROPERTY		= "org.brandao.brutos.request.path";

	//private static final String DEFAULT_PATH		= System.getProperty("java.io.tmpdir");
	
	public void parserContentType(MutableMvcRequest request,
			MutableRequestParserEvent requestParserInfo, 
			CodeGenerator codeGenerator, Properties config) throws RequestParserException {
		
        try{
        	MediaType requestDataType = (MediaType)request.getType();
        	Map<String,String> vars   = requestDataType == null? null : requestDataType.getParams();
        	String charsetName        = vars != null? vars.get("charset") : DEFAULT_CHARSET;
        	charsetName               = charsetName == null? request.getEncoding() : charsetName;
        	charsetName               = charsetName == null? DEFAULT_CHARSET : charsetName;
            String boundary           = (String)request.getHeader(BOUNDARY);
            boundary                  = boundary == null? ((MediaType)request.getType()).getParams().get(BOUNDARY) : boundary;
        	InputStream stream        = request.getStream();
            Long maxRequestBodyLength = Long.parseLong(config.getProperty(MAX_LENGTH_PROPERTY, DEFAULT_MAX_LENGTH));
            Integer bufferLength      = Integer.parseInt(config.getProperty(BUFFER_LENGTH_VAR, DEFAULT_BUFFER_LENGTH));
            //String path               = config.getProperty(PATH_PROPERTY, DEFAULT_PATH);
                
            MultipartFormDataParser mpfdp = new MultipartFormDataParser(stream, charsetName, boundary, maxRequestBodyLength, bufferLength, requestParserInfo);

            while(mpfdp.hasMoreElements()){
                Field field = mpfdp.nextElement();
                request.setParameter(field.getHeader().get("content-disposition").getParams().get("name"), field.getValue());
            }
            
        	BeanDecoder beanDecoder = new MultipartFormDataBeanDecoder();
        	beanDecoder.setCodeGenerator(codeGenerator);
            super.parser(request, requestParserInfo, beanDecoder, config, null);
        }
        catch(Throwable e){
        	throw new RequestParserException(e);
        }
		
	}
	
}
