package org.brandao.brutos.annotation.helper.any.app2.fail.metavaluesdefinition;


import java.util.List;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.helper.any.app1.Property;

public class Test3FailAnyBeanMetaValuesDefinition {

	public Test3FailAnyBeanMetaValuesDefinition(
			@ElementCollection(
				any=
					@Any(
							metaBean=@Basic(bean="teste"),
							metaValuesDefinition=TestEmptyMetaValuesDefinition.class
					)
				)
			List<Property> property) {
	}


}
