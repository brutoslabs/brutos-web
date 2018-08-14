package org.brandao.brutos.web;

import org.brandao.brutos.ResultAction;

public interface WebResultAction 
	extends ResultAction{

	WebResultAction setView(String view);

	WebResultAction setView(String view, boolean resolved);

	WebResultAction setContentType(Class<?> type);

	WebResultAction setContent(Object value);

	WebResultAction add(String name, Object o);
	
	WebResultAction setResponseStatus(int value);
	
	WebResultAction setReason(String value);
	
	WebResultAction addHeader(String name, String value);
	
}
