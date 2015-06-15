package org.brandao.brutos.annotation.helper.interceptsstack.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;
import org.brandao.brutos.annotation.Param;

@Controller("/controller")
@InterceptedBy({
	@Intercept(
		name="stackA",
		params={
			@Param(name="interceptor1.param1_1_a", value="value1_1_ax"),
			@Param(name="interceptor1.param1_2_a", value="value1_2_ax")
		}
	)
})
public class Test1interceptsStack {

	public boolean testAction(){
		return true;
	}
	
}
