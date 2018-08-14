package org.brandao.brutos.web;

import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.ResultActionImp;

public class WebResultActionImp
	extends ResultActionImp
	implements ConfigurableWebResultAction {

	private int responseStatus;
	
	private String reason;
	
	private Map<String,String> header;
	
	public WebResultActionImp(){
		this.header = new HashMap<String, String>();
	}
	
	public WebResultAction setView(String view) {
		super.setView(view);
		return this;
	}

	public WebResultAction setView(String view, boolean resolved) {
		super.setView(view, resolved);
		return this;
	}

	public WebResultAction setContentType(Class<?> type) {
		super.setContentType(type);
		return this;
	}

	public WebResultAction setContent(Object value) {
		super.setContent(value);
		return this;
	}

	public WebResultAction add(String name, Object o) {
		super.add(name, o);
		return this;
	}

	public WebResultAction setResponseStatus(int value) {
		this.responseStatus = value;
		return this;
	}

	public WebResultAction setReason(String value) {
		this.reason = value;
		return this;
	}

	public WebResultAction addHeader(String name, String value) {
		this.header.put(name, value);
		return this;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public String getReason() {
		return reason;
	}


}
