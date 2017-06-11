package org.brandao.brutos.web;

import javax.servlet.ServletRequest;

import org.brandao.brutos.MutableMvcRequest;

public interface MutableWebMvcRequest 
	extends MutableMvcRequest, WebMvcRequest{

	void setServletRequest(ServletRequest value);
	
}
