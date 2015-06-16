package org.brandao.brutos.annotation.helper.throwsafe.fail;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ThrowSafeList;

@Controller("/controller")
public class Test4FailThrowSafeController {
	
	@ThrowSafeList({})
	public void test1Action(){
	}
	
}
