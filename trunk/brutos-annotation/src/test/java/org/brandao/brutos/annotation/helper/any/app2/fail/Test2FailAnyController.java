package org.brandao.brutos.annotation.helper.any.app2.fail;


import java.util.List;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.helper.any.app1.Property;

@Controller("/controller")
public class Test2FailAnyController {

	public void testAction(
			@ElementCollection(
				any=
					@Any(metaBean=@Basic(bean="test"))
				)
			List<Property> arg0){
	}
	
}
