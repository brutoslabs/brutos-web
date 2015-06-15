package org.brandao.brutos.annotation.helper.intercepts.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;

@Controller("/controller")
@InterceptedBy(
		@Intercept(name="interceptorX"))
public class Test4_1Intercepts {

	public boolean testAction(){
		return true;
	}
	
}
