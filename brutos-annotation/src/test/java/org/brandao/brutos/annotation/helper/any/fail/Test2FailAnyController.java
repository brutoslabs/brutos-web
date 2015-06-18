package org.brandao.brutos.annotation.helper.any.fail;


import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.helper.any.app1.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app1.Property;
import org.brandao.brutos.annotation.helper.any.app1.SetProperty;

@Controller("/controller")
public class Test2FailAnyController {

	@Any(
		metaBean=@Basic,
		metaValues={
				@MetaValue(name="decimal", target=DecimalProperty.class),
				@MetaValue(name="set", target=SetProperty.class)
			}
	)
	private Property property;
	
	
}
