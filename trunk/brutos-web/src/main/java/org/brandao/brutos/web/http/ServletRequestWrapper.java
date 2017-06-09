package org.brandao.brutos.web.http;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.web.WebMvcRequest;
import org.brandao.brutos.web.WebMvcRequestImp;

@SuppressWarnings("rawtypes")
public class ServletRequestWrapper 
	extends javax.servlet.http.HttpServletRequestWrapper{

	private WebMvcRequest mvcRequest;
	
	public ServletRequestWrapper(WebMvcRequest mvcRequest) {
		super((HttpServletRequest) mvcRequest.getServletRequest());
		this.mvcRequest = mvcRequest;
	}

	@Override
	public String getHeader(String name) {
		return String.valueOf(mvcRequest.getHeader(name));
	}

	@Override
	public Enumeration getHeaderNames() {
		return Collections.enumeration(mvcRequest.getHeaderNames());
	}

	@Override
	public Object getAttribute(String name) {
		return mvcRequest.getProperty(name);
	}

	@Override
	public Enumeration getAttributeNames() {
		return Collections.enumeration(mvcRequest.getPropertyNames());
	}

	@Override
	public String getParameter(String name) {
		return String.valueOf(mvcRequest.getParameter(name));
	}

	@Override
	public Map getParameterMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Enumeration getParameterNames() {
		return Collections.enumeration(mvcRequest.getParameterNames());
	}

	@Override
	public String[] getParameterValues(String name) {
		Object[] values = mvcRequest.getParameters();
		
		if(values == null){
			return null;
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAttribute(String name, Object o) {
		((MutableMvcRequest)mvcRequest).setProperty(name, o);
	}

	public void setRequest(ServletRequest request){
		this.mvcRequest = new WebMvcRequestImp((HttpServletRequest) request);
		super.setRequest(request);
	}
}
