package org.brandao.brutos.annotation.helper.result.fail;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Result;

@Controller("/controller")
public class Test1FailResultController {

	@Result("resultAction")
	public void test1Action(){
	}

}
