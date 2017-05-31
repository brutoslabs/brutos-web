package org.brandao.brutos.web;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosConstants;

public interface BrutosWebConstants extends BrutosConstants{

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

	@Deprecated
	final String DEFAULT_REQUEST_PARSER = "org.brandao.brutos.web.http.HttpRequestParserImp";
	
	final RequestMethodType DEFAULT_REQUEST_METHOD_TYPE = RequestMethodType.GET;
	
	final MediaType DEFAULT_REQUEST_TYPE = MediaType.APPLICATION_X_WWW_FORM_URLENCODED;
	
	final int DEFAULT_RESPONSE_ERROR  = HttpStatus.INTERNAL_SERVER_ERROR;

	final int DEFAULT_RESPONSE_STATUS = HttpStatus.OK;
	
}
