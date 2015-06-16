package org.brandao.brutos.annotation.helper.result.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Result;

@Controller("/controller")
public class Test1ResultController {

	public boolean test1Action(){
		return true; 
	}

	@Result("resultAction")
	public boolean test2Action(){
		return true; 
	}
	
}
