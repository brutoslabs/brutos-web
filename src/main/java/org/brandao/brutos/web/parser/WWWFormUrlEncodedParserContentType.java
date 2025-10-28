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
import org.brandao.brutos.web.bean.WWWFormUrlEncodedBeanDecoder;
import org.brandao.brutos.web.http.WWWFormUrlEncodedParser;
import org.brandao.brutos.web.http.WWWFormUrlEncodedParser.Field;

/**
 * 
 * @author Brandao
 *
 */
public class WWWFormUrlEncodedParserContentType 
	extends AbstractParserContentType{
	
	private static final String DEFAULT_CHARSET         = "ISO8859-1";
	
	private static final String MAX_LENGTH_PROPERTY		= "org.brandao.brutos.request.max_length";

	private static final String DEFAULT_MAX_LENGTH		= "3145728";
	
	private static final String BUFFER_LENGTH_VAR		= "org.brandao.brutos.request.buffer_length";

	private static final String DEFAULT_BUFFER_LENGTH	= "8192";
	
	public void parserContentType(MutableMvcRequest request,
			MutableRequestParserEvent requestParserInfo, 
			CodeGenerator codeGenerator, Properties config)
			throws RequestParserException {
	
        try{
        	MediaType requestDataType = (MediaType)request.getType();
        	Map<String,String> vars   = requestDataType == null? null : requestDataType.getParams();
        	String charsetName        = vars != null? vars.get("charset") : DEFAULT_CHARSET;
        	charsetName               = charsetName == null? request.getEncoding() : charsetName;
        	charsetName               = charsetName == null? DEFAULT_CHARSET : charsetName;
        	InputStream stream        = request.getStream();
        	Long maxRequestBodyLength = Long.parseLong(config.getProperty(MAX_LENGTH_PROPERTY, DEFAULT_MAX_LENGTH));
            Integer bufferLength      = Integer.parseInt(config.getProperty(BUFFER_LENGTH_VAR, DEFAULT_BUFFER_LENGTH));
        	
            WWWFormUrlEncodedParser wfuep = new WWWFormUrlEncodedParser(stream, charsetName, maxRequestBodyLength, bufferLength, requestParserInfo);
            
            while(wfuep.hasMoreElements()){
                Field field = wfuep.nextElement();
                request.setParameter(field.getName(), field.getValue());
            }
            
        	BeanDecoder beanDecoder = new WWWFormUrlEncodedBeanDecoder();
        	beanDecoder.setCodeGenerator(codeGenerator);
            super.parser(request, requestParserInfo, beanDecoder, config, null);
        }
        catch(Throwable e){
        	throw new RequestParserException(e);
        }
		
	}
	
}
