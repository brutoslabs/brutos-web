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

package org.brandao.brutos.annotation.web;

/**
 * Descreve os tipos MIME. 
*/
public interface MediaTypes {
	
	static final String ALL 								= "*/*"; 

	static final String APPLICATION_ATOM_XML 				= "application/atom+xml";

	static final String APPLICATION_X_WWW_FORM_URLENCODED 	= "application/x-www-form-urlencoded";

	static final String APPLICATION_JSON 					= "application/json";

	static final String APPLICATION_OCTET_STREAM 			= "application/octet-stream";

	static final String APPLICATION_PDF 					= "application/pdf";

	static final String APPLICATION_RSS_XML 				= "application/rss+xml";

	static final String APPLICATION_XHTML_XML 				= "application/xhtml+xml";;
		
	static final String APPLICATION_XML 					= "application/xml";

	static final String IMAGE_GIF 							= "image/gif";

	static final String IMAGE_JPEG 							= "image/jpeg";

	static final String IMAGE_PNG 							= "image/png";

	static final String MULTIPART_FORM_DATA 				= "multipart/form-data";

	static final String TEXT_EVENT_STREAM 					= "text/event-stream";

	static final String TEXT_HTML 							= "text/html";

	static final String TEXT_MARKDOWN 						= "text/markdown";

	static final String TEXT_PLAIN 							= "text/plain";

	static final String TEXT_XML 							= "text/xml";	
	
}
