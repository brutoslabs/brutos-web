package org.brandao.brutos.web;

import java.util.Map;

import org.brandao.brutos.ConfigurableResultAction;

public interface ConfigurableWebResultAction 
	extends WebResultAction, ConfigurableResultAction{

	Map<String, String> getHeader();

	void setHeader(Map<String, String> header);

	int getResponseStatus();

	String getReason();
	
}
