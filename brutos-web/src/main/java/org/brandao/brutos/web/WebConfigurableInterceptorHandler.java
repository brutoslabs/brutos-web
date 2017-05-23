package org.brandao.brutos.web;

import org.brandao.brutos.interceptor.ConfigurableInterceptorHandler;
import org.brandao.brutos.web.mapping.MediaTypeMap;

public interface WebConfigurableInterceptorHandler 
	extends ConfigurableInterceptorHandler, WebInterceptorHandler{

	void setRequestMethodType(RequestMethodType value);

	void setRequestType(MediaType value);
	
	void setAcceptRequestType(MediaTypeMap value);
	
}
