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

package org.brandao.brutos.web;

import org.brandao.brutos.web.parser.AllParserContentType;
import org.brandao.brutos.web.parser.JsonParserContentType;
import org.brandao.brutos.web.parser.MultipartFormDataParserContentType;
import org.brandao.brutos.web.parser.WWWFormUrlEncodedParserContentType;

/**
 * 
 * @author Brandao
 *
 */
public class WebRequestParserImp 
	extends org.brandao.brutos.AbstractRequestParser{

	public WebRequestParserImp(){
		this.registerParser(
			MediaType.ALL, new AllParserContentType());
		this.registerParser(
			MediaType.APPLICATION_JSON,	new JsonParserContentType());
		this.registerParser(
			MediaType.MULTIPART_FORM_DATA, new MultipartFormDataParserContentType());
		this.registerParser(
			MediaType.APPLICATION_X_WWW_FORM_URLENCODED, new WWWFormUrlEncodedParserContentType());
	}
	
}
