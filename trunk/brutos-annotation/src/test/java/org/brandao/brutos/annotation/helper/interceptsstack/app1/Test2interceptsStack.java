package org.brandao.brutos.annotation.helper.interceptsstack.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;
import org.brandao.brutos.annotation.Param;

@Controller("/controller")
@InterceptedBy({
	@Intercept(
		name="stackB",
		params={
				@Param(name="interceptor3.param3_1_b", value="value3_1_bx"),
				@Param(name="interceptor3.param3_2_b", value="value3_2_bx")
		}
	)
})
public class Test2interceptsStack {

	public boolean testAction(){
		return true;
	}
	
}
