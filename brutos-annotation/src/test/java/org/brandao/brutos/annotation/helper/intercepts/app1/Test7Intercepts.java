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
				@Param(name="param7.1", value="value7.1x"),
				@Param(name="param7.2", value="value7.2x")
			}
))
public class Test7Intercepts {

	public boolean testAction(){
		return true;
	}
	
}
