package org.brandao.brutos.annotation.helper.throwsafe.fail;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ThrowSafe;
import org.brandao.brutos.annotation.ThrowSafeList;
import org.brandao.brutos.annotation.helper.throwsafe.app1.Exception2;

@Controller("/controller")
public class Test2FailThrowSafeController {
	
	@ThrowSafeList({
		@ThrowSafe(target=Exception2.class),
		@ThrowSafe(target=Exception2.class)
	})
	public void test1Action(){
	}
	
}
