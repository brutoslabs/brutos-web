package org.brandao.brutos.annotation.helper.interceptedby.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;
import org.brandao.brutos.annotation.Param;

@Controller("/controller")
@InterceptedBy({
		@Intercept(
			interceptor=TestNameInterceptorController.class,
			params={
				@Param(name="param1",value="value1"),
				@Param(name="param2",value="value2")
			}
		),
		@Intercept(
				interceptor=TestName2InterceptorController.class,
				params={
					@Param(name="param3",value="value3"),
					@Param(name="param4",value="value4")
				}
			)
})
public class Test5InterceptedByController {

	public boolean testAction(){
		return true;
	}
	
}
