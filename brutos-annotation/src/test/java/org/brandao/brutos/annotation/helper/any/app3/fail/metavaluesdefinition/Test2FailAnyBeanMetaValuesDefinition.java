package org.brandao.brutos.annotation.helper.any.app3.fail.metavaluesdefinition;


import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.helper.any.app1.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app1.Property;
import org.brandao.brutos.annotation.helper.any.app1.SetProperty;

public class Test2FailAnyBeanMetaValuesDefinition {

	private Map<Property,String> property;

	public Map<Property,String> getProperty() {
		return property;
	}

	@KeyCollection(
		any=
			@Any(
					metaBean=@Basic(bean="teste"),
					metaValuesDefinition=TestEmptyMetaValuesDefinition.class
					)
		)
	public void setProperty(Map<Property,String> property) {
		this.property = property;
	}


}
