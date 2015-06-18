package org.brandao.brutos.annotation.helper.any.app1.fail.metavaluesdefinition;


import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.helper.any.app1.Property;

public class Test3FailAnyBeanMetaValuesDefinition {

	public Test3FailAnyBeanMetaValuesDefinition(
			@Any(
					metaBean=@Basic(bean="teste"),
					metaValuesDefinition=TestEmptyMetaValuesDefinition.class
			)
			Property property) {
	}


}
