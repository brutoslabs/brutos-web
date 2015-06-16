package org.brandao.brutos.annotation.helper.throwsafe.fail;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ThrowSafe;
import org.brandao.brutos.annotation.ThrowSafeList;
import org.brandao.brutos.annotation.helper.throwsafe.app1.Exception1;

@Controller("/controller")
@ThrowSafeList({
	@ThrowSafe(target=Exception1.class),
	@ThrowSafe(target=Exception1.class)
})
public class Test1FailThrowSafeController {
	
}
