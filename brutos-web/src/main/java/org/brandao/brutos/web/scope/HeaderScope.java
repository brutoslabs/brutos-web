package org.brandao.brutos.web.scope;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.RequestProvider;
import org.brandao.brutos.ResponseProvider;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.WebMvcRequest;
import org.brandao.brutos.web.WebMvcResponse;

public class HeaderScope implements Scope{

	public void put(String name, Object value) {
		WebMvcResponse response = (WebMvcResponse)ResponseProvider.getResponse();
		HttpServletResponse servletResponse = (HttpServletResponse) response.getServletResponse();
		servletResponse.setHeader(name, String.valueOf(value));
	}

	public Object get(String name) {
		WebMvcRequest request   = (WebMvcRequest)RequestProvider.getRequest();
		HttpServletRequest servletRequest   = (HttpServletRequest) request.getServletRequest();
		return servletRequest.getHeader(name);
	}

	public Object getCollection(String name) {
		WebMvcRequest request   = (WebMvcRequest)RequestProvider.getRequest();
		HttpServletRequest servletRequest   = (HttpServletRequest) request.getServletRequest();
		return servletRequest.getHeader(name);
	}

	@SuppressWarnings("unchecked")
	public List<String> getNamesStartsWith(String value) {
		WebMvcRequest request   = (WebMvcRequest)RequestProvider.getRequest();
		HttpServletRequest servletRequest   = (HttpServletRequest) request.getServletRequest();
		
		List<String> result = new ArrayList<String>();
		
		Enumeration<String> names = 
				servletRequest.getHeaderNames();
		
		while(names.hasMoreElements()){
			String name = names.nextElement();
			if(name.startsWith(value)){
				result.add(name);
			}
		}
		
		return result;
	}

	public void remove(String name) {
	}

}
