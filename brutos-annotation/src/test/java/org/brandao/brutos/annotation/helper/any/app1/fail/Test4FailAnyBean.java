package org.brandao.brutos.annotation.helper.any.app1.fail;


import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.helper.any.app1.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app1.Property;
import org.brandao.brutos.annotation.helper.any.app1.SetProperty;

public class Test4FailAnyBean {

	@Any(
			metaBean=@Basic(bean="teste"),
			metaValues={
					@MetaValue(name="0", target=DecimalProperty.class)
				})
	private Property property;
	
}
