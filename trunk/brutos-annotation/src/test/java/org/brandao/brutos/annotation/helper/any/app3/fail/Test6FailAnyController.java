package org.brandao.brutos.annotation.helper.any.app3.fail;


import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.helper.any.app1.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app1.Property;
import org.brandao.brutos.annotation.helper.any.app1.SetProperty;

@Controller("/controller")
public class Test6FailAnyController {

	@KeyCollection(
		any=
		@Any(
			metaBean=@Basic,
			metaValues={
					@MetaValue(name="0", target=DecimalProperty.class),
					@MetaValue(name="1", target=SetProperty.class)
				}
		)
		)
	private Map<Property,String> property;
	
	
}
