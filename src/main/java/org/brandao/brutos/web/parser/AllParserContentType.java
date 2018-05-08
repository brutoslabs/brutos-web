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
import java.io.InputStreamReader;
import java.net.URLDecoder;
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

/**
 * 
 * @author Brandao
 *
 */
public class AllParserContentType 
	extends AbstractParserContentType{
	
	private static final String DEFAULT_CHARSET         = "ISO8859-1";
	
	private static final String BUFFER_LENGTH_VAR		= "org.brandao.brutos.request.buffer_length";

	private static final String DEFAULT_BUFFER_LENGTH	= "1024";
	
	
	public void parserContentType(MutableMvcRequest request,
			MutableRequestParserEvent requestParserInfo, 
			CodeGenerator codeGenerator, Properties config)
			throws RequestParserException {
	
		InputStream stream       = null;
		InputStreamReader reader = null;
        try{
        	MediaType requestDataType = (MediaType)request.getType();
        	Map<String,String> vars   = requestDataType == null? null : requestDataType.getParams();
        	String charsetName        = vars != null? vars.get("charset") : DEFAULT_CHARSET;
        	charsetName               = charsetName == null? request.getEncoding() : charsetName;
        	charsetName               = charsetName == null? DEFAULT_CHARSET : charsetName;
        	
    		stream = request.getStream();
    		reader = new InputStreamReader(stream, charsetName);
    		
            Integer maxLength =
                    Integer.parseInt(config.getProperty(BUFFER_LENGTH_VAR, DEFAULT_BUFFER_LENGTH));
        	
        	StringBuilder builder = new StringBuilder();
        	char[] buf            = new char[maxLength];
        	int l;
        	while((l = reader.read(buf, 0, buf.length)) != -1){
        		builder.append(buf, 0, l);
        	}
        	
        	String data = builder.toString();
        	String[] parts = data.split("\\&");
        	
        	for(String part: parts){
        		String[] field = part.split("\\=");
        		
        		if(field.length < 2){
        			continue;
        		}
        		
        		String name  = URLDecoder.decode(field[0], "UTF-8");
        		String value = URLDecoder.decode(field[1], "UTF-8");
        		
        		if(!value.isEmpty()){
        			request.setParameter(name, value);
        		}
        	}
        	
        	BeanDecoder beanDecoder = new WWWFormUrlEncodedBeanDecoder();
        	beanDecoder.setCodeGenerator(codeGenerator);
            super.parser(request, requestParserInfo, beanDecoder, config, null);
        }
        catch(Throwable e){
        	throw new org.brandao.brutos.RequestParserException(e);
        }
        finally{
        	if(reader != null){
        		try{
        			reader.close();
        		}
        		catch(Throwable e){
        		}
        	}
        	
        	if(stream != null){
        		try{
        			stream.close();
        		}
        		catch(Throwable e){
        		}
        	}
        }
		
	}
	
}
