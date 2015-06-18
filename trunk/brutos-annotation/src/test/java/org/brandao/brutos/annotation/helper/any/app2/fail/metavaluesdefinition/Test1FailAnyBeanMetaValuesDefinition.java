package org.brandao.brutos.annotation.helper.any.app2.fail.metavaluesdefinition;


import java.util.List;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.helper.any.app1.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app1.Property;
import org.brandao.brutos.annotation.helper.any.app1.SetProperty;

public class Test1FailAnyBeanMetaValuesDefinition {

	@ElementCollection(
		any=
			@Any(
					metaBean=@Basic(bean="teste"),
					metaValuesDefinition=TestEmptyMetaValuesDefinition.class
			)
		)
	private List<Property> property;
	
}
