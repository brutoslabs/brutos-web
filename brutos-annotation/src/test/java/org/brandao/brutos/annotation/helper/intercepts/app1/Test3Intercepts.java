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
				@Param(name="param3.1", value="value3.1"),
				@Param(name="param3.2", value="value3.2")
			}
))
public class Test3Intercepts {

	public boolean testAction(){
		return true;
	}
	
}
