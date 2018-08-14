package org.brandao.brutos.web;

import org.brandao.brutos.StackRequestElementImp;

public class WebStackRequestElementImp 
	extends StackRequestElementImp
	implements WebStackRequestElement{

	private int responseStatus;
	
	private String reason;
	
	public void setResponseStatus(int value) {
		this.responseStatus = value; 
	}

	public int getResponseStatus() {
		return this.responseStatus;
	}

	public void setReason(String value) {
		this.reason = value;
	}

	public String getReason() {
		return this.reason;
	}

}
