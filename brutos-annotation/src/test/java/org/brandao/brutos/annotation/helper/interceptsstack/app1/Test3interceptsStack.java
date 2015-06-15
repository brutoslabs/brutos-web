package org.brandao.brutos.annotation.helper.interceptsstack.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;

@Controller("/controller")
@InterceptedBy({
	@Intercept(
		name="stackC")
})
public class Test3interceptsStack {

	public boolean testAction(){
		return true;
	}
	
}
