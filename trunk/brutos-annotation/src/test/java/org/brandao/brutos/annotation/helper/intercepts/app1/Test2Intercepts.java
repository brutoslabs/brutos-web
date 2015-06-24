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
				@Param(name="param2_1", value="value2_1"),
				@Param(name="param2_2", value="value2_2")
			}
))
public class Test2Intercepts {

	public boolean testAction(){
		return true;
	}
	
}
