package org.brandao.brutos.web.test;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.web.ConfigurableWebApplicationContext;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;

public class BasicWebApplicationTester 
	implements WebApplicationTester{

	public void prepareContext(Map<String, String> parameters) {
	}

	public void prepareRequest(Map<String, String> parameters) {
	}

	public void prepareSession(Map<String, String> parameters) {
	}

	public void checkException(Throwable e){
	}

	public void checkResult(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			ConfigurableWebApplicationContext applicationContext) {
	}

	public void prepareContext(MockServletContext servletContext) {
		// TODO Auto-generated method stub
		
	}

	public void prepareRequest(MockHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	public void prepareSession(MockHttpSession session) {
		// TODO Auto-generated method stub
		
	}

}
