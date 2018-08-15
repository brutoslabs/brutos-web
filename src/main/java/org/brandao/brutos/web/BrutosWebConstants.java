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

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosConstants;

/**
 * 
 * @author Brandao
 *
 */
public interface BrutosWebConstants extends BrutosConstants{

	final String REQUEST = ApplicationContext.class
			.getName() + ".request";
	
	final String RESPONSE = ApplicationContext.class
			.getName() + ".response";
	
	final String REDIRECT_PARAMS = ApplicationContext.class
			.getName() + ".web.redirect.params";
	
	final String WEB_SEPARATOR = "/";

	final String WEB_APPLICATION_CLASS = ApplicationContext.class
			.getName() + ".web.application";

	@Deprecated
	final String UPLOAD_LISTENER_FACTORY = ApplicationContext.class
			.getName() + ".UPLOAD_LISTENER_FACTORY";

	@Deprecated
	final String HTTP_REQUEST_PARSER_FACTORY = ApplicationContext.class
			.getName() + ".HTTP_REQUEST_PARSER_FACTORY";

	final String HTTP_REQUEST_PARSER = ApplicationContext.class
			.getName() + ".HTTP_REQUEST_PARSER";

	final String SESSION_UPLOAD_STATS = ApplicationContext.class
			.getName() + ".SESSION_UPLOAD_STATS";

	final String FLASH_INSTRUMENT = ApplicationContext.class.getName()
			+ ".FLASH_INSTRUMENT";

	final String JSF_HANDLER = ApplicationContext.class.getName()
			+ ".JSF_Handler";

	final String JSF_CONTEXT = ApplicationContext.class.getName()
			+ ".JSF_Context";

	final String JSF_UI_VIEW_ROOT = ApplicationContext.class.getName()
			+ ".JSF_viewRoot";

	final String JSF_ACTION_LISTENER = ApplicationContext.class
			.getName() + ".JSF_Action_Listener";

	@Deprecated
	final String REQUEST_PARSER_CLASS = "org.brandao.brutos.web.request_parser";

	final String REQUEST_METHOD_TYPE = "org.brandao.brutos.web.request.method_type";

	final String RESPONSE_STATUS = "org.brandao.brutos.web.response.status";

	final String RESPONSE_ERROR = "org.brandao.brutos.web.response.error";

	@Deprecated
	final String DEFAULT_REQUEST_PARSER = "org.brandao.brutos.web.http.HttpRequestParserImp";
	
	final RequestMethodType DEFAULT_REQUEST_METHOD_TYPE = RequestMethodType.GET;
	
	final MediaType DEFAULT_REQUEST_TYPE = MediaType.APPLICATION_X_WWW_FORM_URLENCODED;

	final MediaType DEFAULT_RESPONSE_TYPE = MediaType.TEXT_HTML;
	
	final int DEFAULT_RESPONSE_ERROR  = HttpStatus.INTERNAL_SERVER_ERROR;

	final int DEFAULT_RESPONSE_STATUS = HttpStatus.OK;
	
}
