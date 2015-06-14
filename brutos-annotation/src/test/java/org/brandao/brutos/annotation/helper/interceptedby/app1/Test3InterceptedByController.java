package org.brandao.brutos.annotation.helper.interceptedby.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;
import org.brandao.brutos.annotation.Param;

@Controller("/controller")
@InterceptedBy(
		@Intercept(
			name="testName",
			params=@Param(name="param1",value="value1")
		)
)
public class Test3InterceptedByController {

	public boolean testAction(){
		return true;
	}
	
}
