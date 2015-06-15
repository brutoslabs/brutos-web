package org.brandao.brutos.annotation.helper.intercepts.app1;

import org.brandao.brutos.annotation.Controller;

@Controller("/controller")
public class TestNotInterceptorIntercepts {

	public boolean testAction(){
		return true;
	}
	
}
