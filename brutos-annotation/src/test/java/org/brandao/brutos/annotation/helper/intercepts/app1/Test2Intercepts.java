package org.brandao.brutos.annotation.helper.intercepts.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;
import org.brandao.brutos.annotation.Param;

@Controller("/controller")
@InterceptedBy(
		@Intercept(
			interceptor=Interceptor2ControllerInterceptor.class,
			params={
				@Param(name="param2.1", value="value2.1"),
				@Param(name="param2.2", value="value2.2")
			}
))
public class Test2Intercepts {

	public boolean testAction(){
		return true;
	}
	
}
