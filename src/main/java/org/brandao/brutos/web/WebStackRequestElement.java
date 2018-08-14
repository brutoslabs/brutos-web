package org.brandao.brutos.web;

import org.brandao.brutos.StackRequestElement;

public interface WebStackRequestElement 
	extends StackRequestElement{

	void setResponseStatus(int value);
	
	int getResponseStatus();
	
	void setReason(String value);

	String getReason();
	
}
