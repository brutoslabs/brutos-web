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
import java.util.List;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.brandao.brutos.DataType;
import org.brandao.brutos.DefaultMvcRequest;

/**
 * 
 * @author Brandao
 */
public class WebMvcRequestImp 
	extends DefaultMvcRequest
	implements WebMvcRequest{

    private HttpServletRequest request;

    private RequestMethodType requestMethodType;
    
    @SuppressWarnings("unchecked")
	public WebMvcRequestImp(HttpServletRequest request){
    	super();
        this.request           = request;
        this.requestMethodType = 
        			RequestMethodType.valueOf(request.getMethod().toUpperCase());
        
        super.setType(
        			MediaType.valueOf(request.getContentType()));
        
        super.setRequestId(
    		this.parseRequestId(
    				request.getRequestURI(), 
    				request.getContextPath()));
        
        super.setAcceptResponse(
    		this.parseAcceptResponse(
    				request.getHeader("Accept")));
        
        this.loadNames(request.getParameterNames(), super.parameterNames);
        this.loadNames(request.getHeaderNames(), super.headerNames);
        this.loadNames(request.getAttributeNames(), super.parameterNames);
    }

    private void loadNames(Enumeration<String> paramNames, Set<String> set){
        while(paramNames.hasMoreElements()){
        	String name = paramNames.nextElement();
        	set.add(name);
        }
    }
    
    private String parseRequestId(String path, String contextPath){
        return path.substring( contextPath.length(), path.length() );
    }
    
    private List<DataType> parseAcceptResponse(String accept){
    	
    	List<DataType> result = new ArrayList<DataType>();
    	String[] split = accept.split("\\,");
    	
    	for(String mt: split){
    		MediaType mediaType = MediaType.valueOf(mt);
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
		return requestMethodType;
	}

	public InputStream getStream() throws IOException {
		return request.getInputStream();
	}
    
	public Object getHeader(String value) {
		Object r = super.getHeader(value);
		return r == null? request.getHeader(value) : r;
	}

	public Object getParameter(String name) {
		Object r = super.getParameter(name);
		return r == null? request.getParameter(name) : r;
	}

	public Object getProperty(String name) {
		Object r = super.getProperty(name);
		return r == null? request.getAttribute(name) : r;
	}
    
	public void setProperty(String name, Object value) {
		super.setProperty(name, value);
	}
    
}
