package org.brandao.brutos.annotation.helper.intercepts.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;
import org.brandao.brutos.annotation.Param;

@Controller("/controller")
@InterceptedBy(
		@Intercept(
			interceptor=Interceptor1ControllerInterceptor.class,
			params={
				@Param(name="param1_1", value="value1_1"),
				@Param(name="param1_2", value="value1_2")
			}
))
public class Test1Intercepts {

	public boolean testAction(){
		return true;
	}
	
}
