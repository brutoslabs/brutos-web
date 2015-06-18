package org.brandao.brutos.annotation.helper.any.app1.fail;


import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.helper.any.app1.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app1.Property;
import org.brandao.brutos.annotation.helper.any.app1.SetProperty;

public class Test2FailAnyBean {

	private Property property;

	public Property getProperty() {
		return property;
	}

	@Any(
			metaBean=@Basic,
			metaValues={
					@MetaValue(name="0", target=DecimalProperty.class),
					@MetaValue(name="1", target=SetProperty.class)
				})
	public void setProperty(Property property) {
		this.property = property;
	}


}
