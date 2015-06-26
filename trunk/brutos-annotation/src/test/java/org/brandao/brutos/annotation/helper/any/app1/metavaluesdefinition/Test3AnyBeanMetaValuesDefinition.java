package org.brandao.brutos.annotation.helper.any.app1.metavaluesdefinition;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.any.app1.Property;

public class Test3AnyBeanMetaValuesDefinition {

	@Basic(bean="propertyA")
	@Any(
		metaBean=@Basic(bean="propertyType"),
		metaType=Integer.class,
		metaValuesDefinition=TestDecimalMetaValuesDefinition.class
	)
	public Property property1;
	
	private Property property2;

	@Transient
	private Property property3;
	
	public Test3AnyBeanMetaValuesDefinition(
			@Basic(bean="propertyC")
			@Any(
				metaBean=@Basic(bean="propertyType3"),
				metaType=Integer.class,
				metaValuesDefinition=TestDecimalMetaValuesDefinition.class
			)
			Property property3 ){
		this.property3 = property3;
	}
	
	public Property getProperty2() {
		return property2;
	}

	@Basic(bean="propertyB")
	@Any(
		metaBean=@Basic(bean="propertyType2"),
		metaType=Integer.class,
		metaValuesDefinition=TestDecimalMetaValuesDefinition.class
	)
	public void setProperty2(Property property2) {
		this.property2 = property2;
	}

	public Property getProperty3() {
		return property3;
	}

	public void setProperty3(Property property3) {
		this.property3 = property3;
	}

	
}
