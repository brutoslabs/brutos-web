package org.brandao.brutos.web;

import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.web.mapping.MediaTypeMap;

public interface WebInterceptorHandler 
	extends InterceptorHandler{

	RequestMethodType getRequestMethodType();

	MediaType getRequestType();

	MediaTypeMap getAcceptRequestType();
	
}
