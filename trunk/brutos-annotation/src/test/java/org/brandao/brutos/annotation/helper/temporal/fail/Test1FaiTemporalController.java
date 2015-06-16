package org.brandao.brutos.annotation.helper.temporal.fail;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Temporal;

@Controller("/controller")
public class Test1FaiTemporalController {

	@Temporal("yyyy-MM-dd")
	public String property1;
	
}
