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

import java.util.Map;
import java.util.Properties;

import org.brandao.brutos.web.ParserContentType;
import org.brandao.brutos.web.RequestParserException;
import org.brandao.brutos.web.http.BrutosRequest;
import org.brandao.brutos.web.http.MultipartContentParser;
import org.brandao.brutos.web.http.MultipartContentParser.Input;
import org.brandao.brutos.web.http.MutableUploadEvent;

/**
 * 
 * @author Brandao
 *
 */
public class MultipartFormDataParserContentType 
	implements ParserContentType{

	private static final String MAX_LENGTH_PROPERTY	= "org.brandao.brutos.request.max_length";

	private static final String DEFAULT_MAX_LENGTH	= "0";

	private static final String PATH_PROPERTY		= "org.brandao.brutos.request.path";

	private static final String DEFAULT_PATH		= null;
	
	public void parserContentType(BrutosRequest request, 
			MutableUploadEvent uploadEvent, Properties config, 
			Map<String, String> params)	throws RequestParserException {
		
        try{
            Long maxLength =
                Long.parseLong(config.getProperty(MAX_LENGTH_PROPERTY, DEFAULT_MAX_LENGTH));

            String path = 
        		config.getProperty(PATH_PROPERTY, DEFAULT_PATH);
                
        	MultipartContentParser mpcp = 
        			new MultipartContentParser(request, params, uploadEvent);
        	mpcp.setMaxLength(maxLength);
        	mpcp.setPath(path);
        	mpcp.start();
        	
            while(mpcp.hasMoreElements()){
                Input input = mpcp.nextElement();
                request.setObject(input.getName(), input.getValue() );
            }
            
        }
        catch(Throwable e){
        	throw new RequestParserException(e);
        }
		
	}
	
}
