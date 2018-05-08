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

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.brandao.brutos.web.AbstractRequestParser;
import org.brandao.brutos.web.ParserContentType;
import org.brandao.brutos.web.RequestParserException;
import org.brandao.brutos.web.parser.JsonParserContentType;
import org.brandao.brutos.web.parser.MultipartFormDataParserContentType;

/**
 * 
 * @author Brandao
 */
@Deprecated
public class HttpRequestParserImp 
	extends AbstractRequestParser
	implements HttpRequestParser {

	private ContentTypeParser contentTypeParser;
	
	public HttpRequestParserImp(){
		this.contentTypeParser = new ContentTypeParser();
		//super.registryParser("application/json", 	new JsonParserContentType());
		//super.registryParser("multipart/form-data", new MultipartFormDataParserContentType());
	}
	
    @Deprecated
    public boolean isMultipart(BrutosRequest request, 
            UploadListener uploadListener) throws IOException {
        return uploadListener.getUploadEvent().isMultipart();
    }

    
    @Deprecated
    public void parserMultipart(BrutosRequest request, Properties config,
            UploadListener uploadListener) throws IOException{
    }
    
    
    public void parserContentType(BrutosRequest request, 
    		String contentType, Properties config,
            UploadEvent uploadEvent) throws RequestParserException {
        Map<String,String> contentTypeParams = this.contentTypeParser.parse(contentType);
        String type                          = 
        		contentTypeParams == null? 
        				null : 
    					contentTypeParams.get(ContentTypeParser.TYPE_FIELD);
        ParserContentType parser             = super.parsers.get(type);
        
        if(parser != null){
        	parser.parserContentType(request, (MutableUploadEvent)uploadEvent, config, contentTypeParams);
        }
        
    }

    public UploadEvent getUploadEvent(BrutosRequest request) {
        return new MutableUploadEventImp();
    }
    
}
