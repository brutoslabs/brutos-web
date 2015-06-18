package org.brandao.brutos.annotation.helper.any.app2.fail.metavaluesdefinition;


import java.util.List;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.helper.any.app1.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app1.Property;
import org.brandao.brutos.annotation.helper.any.app1.SetProperty;

public class Test2FailAnyBeanMetaValuesDefinition {

	private List<Property> property;

	public List<Property> getProperty() {
		return property;
	}

	@ElementCollection(
		any=
			@Any(
					metaBean=@Basic(bean="teste"),
					metaValuesDefinition=TestEmptyMetaValuesDefinition.class
					)
		)
	public void setProperty(List<Property> property) {
		this.property = property;
	}


}
