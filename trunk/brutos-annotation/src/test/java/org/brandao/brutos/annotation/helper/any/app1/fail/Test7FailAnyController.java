package org.brandao.brutos.annotation.helper.any.app1.fail;


import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.helper.any.app1.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app1.Property;
import org.brandao.brutos.annotation.helper.any.app1.SetProperty;

@Controller("/controller")
public class Test7FailAnyController {

	public void testAction(
			@Any(
					metaBean=@Basic,
					metaValues={
							@MetaValue(name="0", target=DecimalProperty.class),
							@MetaValue(name="1", target=SetProperty.class)
						})
			Property arg0){
	}
	
}
