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

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.brandao.brutos.*;

/**
 * 
 * @author Brandao
 */
public class WebMvcResponseImp
	extends HttpServletResponseWrapper
	implements MutableWebMvcResponse {

	private MutableMvcResponse response;

    public WebMvcResponseImp(HttpServletResponse response, MvcRequest request){
    	super(response);
    	this.response = new DefaultMvcResponse();
    	this.response.setRequest(request);
    }

	/* HttpServletResponse methods */
	
	public void addHeader(String name, String value){
		super.addHeader(name, value);
	}
	
	public ServletResponse getServletResponse() {
		return super.getResponse();
	}

	public OutputStream processStream() throws IOException {
		return super.getResponse().getOutputStream();
	}
	
	public void setServletresponse(ServletResponse value) {
		super.setResponse(value);
	}

	/* MutableWebMvcResponse methods */
	
	public void setResult(Object value) {
		this.response.setResult(value);
	}

	public void setRequest(MvcRequest value) {
		this.response.setRequest(value);
	}

	public MvcRequest getRequest() {
		return this.response.getRequest();
	}

	public void process(Object object) {
	}

	public void setHeader(String name, Object value) {
		super.addHeader(name, String.valueOf(value));
	}

	public void setType(DataType value) {
		this.response.setType(value);
	}

	public DataType getType() {
		return this.response.getType();
	}

	public Object getResult() {
		return this.response.getResult();
	}
	
}
