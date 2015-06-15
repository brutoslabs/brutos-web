package org.brandao.brutos.annotation.helper.intercepts.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;

@Controller("/controller")
@InterceptedBy(
		@Intercept(interceptor=Interceptor4.class))
public class Test4Intercepts {

	public boolean testAction(){
		return true;
	}
	
}
