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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.brandao.brutos.DataType;
import org.brandao.brutos.DefaultMvcRequest;
import org.brandao.brutos.web.http.ServletRequestWrapper;
import org.brandao.brutos.web.util.WebUtil;

/**
 * 
 * @author Brandao
 */
public class WebMvcRequestImp 
	extends DefaultMvcRequest
	implements MutableWebMvcRequest{

    private ServletRequestWrapper request;

    private HttpServletRequest realRequest;
    
    private RequestMethodType requestMethodType;
    
	public WebMvcRequestImp(HttpServletRequest request){
    	super();
	    this.realRequest = (HttpServletRequest) request;
	    this.request     = new ServletRequestWrapper(this, this.realRequest);
    	
    	//this.setServletRequest(request);
    	super.setAcceptResponse(null);
    }

	public void setServletRequest(ServletRequest value){
	    this.realRequest = (HttpServletRequest) value;
	    this.request     = new ServletRequestWrapper(this, this.realRequest);
		//this.request.setRequest(value);
	}
    
	public List<DataType> getAcceptResponse() {
		if(this.acceptResponse == null){
			this.acceptResponse = 
					this.parseAcceptResponse();
		}
		return this.acceptResponse;
	}
	
	public String getRequestId(){
		String id = super.getRequestId();
		
		if(id == null){
			id = this.parseRequestId(
					this.realRequest.getRequestURI(), 
					this.realRequest.getContextPath());
			super.setRequestId(id);
		}
		
		return id;
	}
	
	public DataType getType(){
		DataType type = super.getType();
		if(type == null){
			type = MediaType.valueOf(this.realRequest.getContentType());
			super.setType(type);
		}
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> getPropertyNames(){
		Set<String> set = new HashSet<String>(super.getPropertyNames());
		Enumeration<String> names = this.realRequest.getAttributeNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			set.add(name);
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	public Set<String> getHeaderNames(){
		Set<String> set = new HashSet<String>(super.getHeaderNames());
		Enumeration<String> names = this.realRequest.getHeaderNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			set.add(name);
		}
		return set;
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> getParameterNames(){
		Set<String> set = new HashSet<String>(super.getParameterNames());
		Enumeration<String> names = this.realRequest.getParameterNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			set.add(name);
		}
		return set;
	}
    
    private String parseRequestId(String path, String contextPath){
        return path.substring( contextPath.length(), path.length() );
    }
    
	private List<DataType> parseAcceptResponse(){
    	
    	List<DataType> result = new ArrayList<DataType>();
    	
    	Enumeration<String> values = WebUtil.toEnumeration(this.realRequest.getHeader("Accept"));
    	
    	while(values.hasMoreElements()){
    		String value = values.nextElement();
    		MediaType mediaType = MediaType.valueOf(value);
    		result.add(mediaType);
    	}
    	
    	Collections.sort(result, new Comparator<DataType>(){

			public int compare(DataType o1, DataType o2) {
				String q1 = ((MediaType)o1).getParams().get("q");
				String q2 = ((MediaType)o2).getParams().get("q");
				
				double v1 = q1 == null? 1.0 : Double.parseDouble(q1);
				double v2 = q2 == null? 1.0 : Double.parseDouble(q2);
				return (int)(v1 - v2);
			}
    		
    	});
    	
    	return result;
    }
    
    public ServletRequest getServletRequest(){
    	return this.request;
    }
    
	public RequestMethodType getRequestMethodType() {
		if(this.requestMethodType == null){
			this.requestMethodType = 
					RequestMethodType.valueOf(this.realRequest.getMethod().toUpperCase());
		}
		return this.requestMethodType;
	}

	public InputStream getStream() throws IOException {
		return realRequest.getInputStream();
	}
    
	public Object getHeader(String value) {
		Object r = super.getHeader(value);
		return r == null? realRequest.getHeader(value) : r;
	}

	public Object getParameter(String name) {
		Object r = super.getParameter(name);
		return r == null? realRequest.getParameter(name) : r;
	}

	public Object getProperty(String name) {
		Object r = super.getProperty(name);
		return r == null? realRequest.getAttribute(name) : r;
	}
    
	public void setProperty(String name, Object value) {
		super.setProperty(name, value);
	}
    
}
