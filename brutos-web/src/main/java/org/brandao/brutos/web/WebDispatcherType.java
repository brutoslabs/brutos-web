package org.brandao.brutos.web;

import org.brandao.brutos.DispatcherType;

public class WebDispatcherType extends DispatcherType{

	public static final WebDispatcherType INCLUDE  = new WebDispatcherType("include");

	public static final WebDispatcherType REDIRECT = new WebDispatcherType("redirect");
	
	static {
		defaultDispatcher.put(INCLUDE.toString(),  INCLUDE);
		defaultDispatcher.put(REDIRECT.toString(), REDIRECT);
	}
	
	public WebDispatcherType(String name) {
		super(name);
	}

}
