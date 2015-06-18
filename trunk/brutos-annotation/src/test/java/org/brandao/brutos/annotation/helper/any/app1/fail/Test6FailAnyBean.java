package org.brandao.brutos.annotation.helper.any.app1.fail;


import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.helper.any.app1.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app1.Property;

public class Test6FailAnyBean {

	public Test6FailAnyBean(
			@Any(
					metaBean=@Basic(bean="teste"),
					metaValues={
							@MetaValue(name="0", target=DecimalProperty.class)
						})
			Property property) {
	}


}
