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

import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.ResultActionImp;

/**
 * 
 * @author Brandao
 *
 */
public class WebResultActionImp
	extends ResultActionImp
	implements ConfigurableWebResultAction {

	private int responseStatus;
	
	private String reason;
	
	private DispatcherType dispatcher;

	private Map<String,String> header;
	
	public WebResultActionImp(){
		this.header = new HashMap<String, String>();
	}

	public WebResultAction setDispatcher(DispatcherType value) {
		this.dispatcher = value;
		return this;
	}

	public DispatcherType getDispatcher() {
		return dispatcher;
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
