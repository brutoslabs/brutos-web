package org.brandao.brutos.annotation.helper.any.app3.fail.metavaluesdefinition;


import java.util.Map;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.helper.any.app1.Property;

public class Test1FailAnyBeanMetaValuesDefinition {

	@KeyCollection(
		bean="key",
		any=
			@Any(
					metaBean=@Basic(bean="teste"),
					metaValuesDefinition=TestEmptyMetaValuesDefinition.class
			)
		)
	private Map<Property,String> property;
	
}
