package org.brandao.brutos.annotation.helper.interceptedby.fail;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;
import org.brandao.brutos.annotation.Param;
import org.brandao.brutos.annotation.helper.interceptedby.app1.TestNameInterceptorController;

@Controller("/controller")
@InterceptedBy({
		@Intercept(
			interceptor=TestNameInterceptorController.class,
			params={
				@Param(name="param",value="value"),
				@Param(name="param2",value="value2")
			}
		),
		@Intercept(
				interceptor=TestNameInterceptorController.class,
				params={
					@Param(name="param3",value="value3"),
					@Param(name="param4",value="value4")
				}
			)
})
public class Test1InterceptedByFailController {

}
