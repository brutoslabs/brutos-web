package org.brandao.brutos.annotation.helper.interceptsstack.fail;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;
import org.brandao.brutos.annotation.Param;

@Controller("/controller")
@InterceptedBy({
	@Intercept(
		name="stackA",
		params={
			@Param(name="interceptorX.param1_1", value="value1_1_ax")
		}
	)
})
public class Test2FailinterceptsStack {

	public boolean testAction(){
		return true;
	}
	
}
