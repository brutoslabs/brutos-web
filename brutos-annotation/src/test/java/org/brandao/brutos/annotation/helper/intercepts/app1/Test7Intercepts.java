package org.brandao.brutos.annotation.helper.intercepts.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;
import org.brandao.brutos.annotation.Param;

@Controller("/controller")
@InterceptedBy(
		@Intercept(
			interceptor=Interceptor3.class,
			params={
				@Param(name="param7_1", value="value7_1x"),
				@Param(name="param7_2", value="value7_2x")
			}
))
public class Test7Intercepts {

	public boolean testAction(){
		return true;
	}
	
}
