package org.brandao.brutos.annotation.helper.interceptedby.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;
import org.brandao.brutos.annotation.Param;

@Controller("/controller")
@InterceptedBy(
		@Intercept(
			interceptor=TestNameInterceptorController.class,
			params={
				@Param(name="param",value="value"),
				@Param(name="param2",value="value2")
			}
		)
)
public class Test4InterceptedByController {

}
