package org.brandao.brutos.annotation.helper.any.app1.fail;


import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.helper.any.app1.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app1.Property;
import org.brandao.brutos.annotation.helper.any.app1.SetProperty;

public class Test3FailAnyBean {

	public Test3FailAnyBean(
			@Any(
					metaBean=@Basic,
					metaValues={
							@MetaValue(name="0", target=DecimalProperty.class),
							@MetaValue(name="1", target=SetProperty.class)
						})
			Property property) {
	}


}
