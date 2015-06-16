package org.brandao.brutos.annotation.helper.temporal.fail;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Temporal;

@Controller("/controller")
public class Test2FaiTemporalController {

	public void testAction(@Temporal("yyyy-MM-dd") String property1){
	}
}
