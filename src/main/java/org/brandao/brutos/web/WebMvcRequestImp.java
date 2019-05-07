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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.DataType;
import org.brandao.brutos.DefaultMvcRequest;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.RequestParser;
import org.brandao.brutos.RequestParserEvent;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.web.util.WebUtil;

/**
 * 
 * @author Brandao
 */
public class WebMvcRequestImp 
	extends HttpServletRequestWrapper 
	implements MutableWebMvcRequest{

    private RequestMethodType requestMethodType;
    
    private MutableMvcRequest request;
    
    private boolean acceptResponseLoaded;
    
    private boolean typeLoaded;
    
    private boolean requestMethodTypeLoaded;
    
	public WebMvcRequestImp(HttpServletRequest request){
    	super(request);
	    this.request                 = new DefaultMvcRequest();
	    this.acceptResponseLoaded    = false;
	    this.typeLoaded              = false;
	    this.requestMethodTypeLoaded = false;
	    this.request.setAcceptResponse(null);
    }

	private HttpServletRequest _getRequest(){
		return (HttpServletRequest) super.getRequest();
	}
    
    private String parseRequestId(String path, String contextPath){
        return path.substring( contextPath.length(), path.length() );
    }
    
	private List<DataType> parseAcceptResponse(){
    	List<DataType> result = new ArrayList<DataType>();
    	
    	String accept = this._getRequest().getHeader(ACCEPT_REQUEST_HEADER);
    	
    	if(accept == null){
    		return result;
    	}
    	
    	Enumeration<String> values = WebUtil.toEnumeration(accept);
    	
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
    
	/* MutableWebMvcRequest */

	public void setRequestMethodType(RequestMethodType requestMethodType) {
		this.requestMethodType = requestMethodType;
		this.requestMethodTypeLoaded = true;
	}
	
	public RequestMethodType getRequestMethodType() {
		if(!this.requestMethodTypeLoaded){
			this.requestMethodType = 
					RequestMethodType.valueOf(this._getRequest().getMethod());
			this.requestMethodTypeLoaded = true;
		}
		
		return this.requestMethodType;
	}

	public String getRequestId() {
		String id = this.request.getRequestId();
		
		if(id == null){
			HttpServletRequest r = this._getRequest();
			String include = (String) r.getAttribute("javax.servlet.include.request_uri");
			
			if(include != null) {
				id = this.parseRequestId(
						include, 
						(String)r.getAttribute("javax.servlet.include.context_path"));
			}
			else {
				id = this.parseRequestId(
						this._getRequest().getRequestURI(), 
						this._getRequest().getContextPath());
			}
			this.request.setRequestId(id);
		}
		
		return id;
	}

	public void setRequestId(String requestId) {
		this.request.setRequestId(requestId);
	}

	public Throwable getThrowable() {
		return this.request.getThrowable();
	}

	public Object getProperty(String name) {
		return this._getRequest().getAttribute(name);
		//Object r = this.request.getProperty(name);
		//return r == null? _getRequest().getAttribute(name) : r;
	}

	public InputStream getStream() throws IOException {
		return _getRequest().getInputStream();
	}

	@SuppressWarnings("unchecked")
	public Set<String> getPropertiesNames(){
		//Set<String> set = new HashSet<String>(this.request.getPropertiesNames());
		Set<String> set = new HashSet<String>();
		Enumeration<String> names = this._getRequest().getAttributeNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			set.add(name);
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	public Set<String> getHeadersNames(){
		//Set<String> set = new HashSet<String>(this.request.getHeadersNames());
		Set<String> set = new HashSet<String>();
		Enumeration<String> names = this._getRequest().getHeaderNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			set.add(name);
		}
		return set;
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> getParametersNames(){
		Set<String> set = new HashSet<String>(this.request.getParametersNames());
		Enumeration<String> names = this._getRequest().getParameterNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			set.add(name);
		}
		return set;
	}
	
	public DataType getType() {
		if(!this.typeLoaded){
			DataType type = MediaType.valueOf(this._getRequest().getContentType());
			this.request.setType(type);
			this.typeLoaded = true;
		}
		
		return this.request.getType();
	}

	public ResourceAction getResourceAction() {
		return this.request.getResourceAction();
	}

	public ApplicationContext getApplicationContext() {
		return this.request.getApplicationContext();
	}

	public Object getResource() {
		return this.request.getResource();
	}

	public Object[] getParameters() {
		return this.request.getParameters();
	}

	public RequestInstrument getRequestInstrument() {
		return this.request.getRequestInstrument();
	}

	public StackRequestElement getStackRequestElement() {
		return this.request.getStackRequestElement();
	}

	public void setThrowable(Throwable value) {
		this.request.setThrowable(value);
	}

	public void setHeader(String name, Object value) {
		throw new UnsupportedOperationException();
	}

	public void setParameter(String name, String value) {
		this.request.setParameter(name, value);
	}

	public void setParameters(String name, String[] values) {
		this.request.setParameters(name, values);
	}

	public void setParameter(String name, Object value) {
		this.request.setParameter(name, value);
	}

	public void setParameters(String name, Object[] value) {
		this.request.setParameters(name, value);
	}

	public void setParameters(Object[] value) {
		this.request.setParameters(value);
	}

	public void setProperty(String name, Object value) {
		//this.request.setProperty(name, value);
		this._getRequest().setAttribute(name, value);
	}

	public void setType(DataType value) {
		this.request.setType(value);
		this.typeLoaded = true;
	}

	public void setResourceAction(ResourceAction value) {
		this.request.setResourceAction(value);
	}

	public void setApplicationContext(ApplicationContext value) {
		this.request.setApplicationContext(value);
	}

	public void setResource(Object value) {
		this.request.setResource(value);
	}

	public void setRequestInstrument(RequestInstrument value) {
		this.request.setRequestInstrument(value);
	}

	public void setStackRequestElement(StackRequestElement value) {
		this.request.setStackRequestElement(value);
	}

	public void setRequestParserInfo(RequestParserEvent value) {
		this.request.setRequestParserInfo(value);
	}

	public void setRequestParser(RequestParser value) {
		this.request.setRequestParser(value);
	}
	
	public RequestParserEvent getRequestParserInfo() {
		return this.request.getRequestParserInfo();
	}

	public RequestParser getRequestParser() {
		return this.request.getRequestParser();
	}
	
	public void setAcceptResponse(List<DataType> value){
		this.request.setAcceptResponse(value);
		this.acceptResponseLoaded = true;
	}
    
	public List<DataType> getAcceptResponse() {
		if(!this.acceptResponseLoaded){
			this.request.setAcceptResponse(this.parseAcceptResponse());
			this.acceptResponseLoaded = true;
		}
		
		return this.request.getAcceptResponse();
	}

	public ServletRequest getServletRequest() {
		return super.getRequest();
	}

	public void setServletRequest(ServletRequest value) {
		super.setRequest(value);
	}

	/* HttpServletRequestWrapper */
	
	@SuppressWarnings("rawtypes")
	public Enumeration getHeaderNames(){
		return this._getRequest().getHeaderNames();
	}
	
	@SuppressWarnings("rawtypes")
	public Enumeration getParameterNames(){
		return Collections.enumeration(this.getParametersNames());
	}
	
	public String getHeader(String value) {
		return this._getRequest().getHeader(value);
	}

	public String getParameter(String name) {
		Object r = this.request.getParameter(name);
		r = r == null? _getRequest().getParameter(name) : r;
		return r == null? null : String.valueOf(r);
	}

	public Object getParameterObject(String name) {
		Object r = this.request.getParameterObject(name);
		r = r == null? _getRequest().getParameter(name) : r;
		return r;
	}
	
	public List<Object> getParameters(String name) {
		
		List<Object> r = this.request.getParameters(name);
		
		if(r == null){
			
			String[] values = _getRequest().getParameterValues(name);
			
			if(values == null){
				return null;
			}
			
			Object[] objArray = new Object[values.length];
			System.arraycopy(values, 0, objArray, 0, objArray.length);
			r = Arrays.asList(objArray);
		}
		
		return r;
	}
	
	@Override
	public Object getAttribute(String name) {
		return this.getProperty(name);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getAttributeNames() {
		return Collections.enumeration(this.getPropertiesNames());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getParameterMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getParameterValues(String name) {
		Object[] values = this.request.getParameters();
		
		if(values == null){
			return _getRequest().getParameterValues(name);
		}
		else{
			String[] result = new String[values.length];
			for(int i=0;i<values.length;i++){
				result[i] = values[i] == null? null : String.valueOf(values[i]);
			}
			return result;
		}
		
	}

	@Override
	public void removeAttribute(String name) {
		this._getRequest().removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object o) {
		this._getRequest().setAttribute(name, o);
	}

	public void setEncoding(String value) throws UnsupportedEncodingException {
		this._getRequest().setCharacterEncoding(value);
	}

	public String getEncoding() {
		return this.request.getEncoding();
	}
	
}
