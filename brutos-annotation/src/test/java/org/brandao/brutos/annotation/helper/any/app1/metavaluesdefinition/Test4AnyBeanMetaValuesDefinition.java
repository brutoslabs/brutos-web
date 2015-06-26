package org.brandao.brutos.annotation.helper.any.app1.metavaluesdefinition;

import java.util.Date;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.any.app1.Property;

public class Test4AnyBeanMetaValuesDefinition {

	@Basic(bean="propertyA")
	@Any(
		metaBean=@Basic(bean="propertyType"),
		metaType=Date.class,
		metaTemporal="yyyy-MM-dd",
		metaValuesDefinition=TestDateMetaValuesDefinition.class
	)
	public Property property1;
	
	private Property property2;

	@Transient
	private Property property3;
	
	public Test4AnyBeanMetaValuesDefinition(
			@Basic(bean="propertyC")
			@Any(
				metaBean=@Basic(bean="propertyType3"),
				metaType=Date.class,
				metaTemporal="yyyy-MM-dd",
				metaValuesDefinition=TestDateMetaValuesDefinition.class
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
		metaType=Date.class,
		metaTemporal="yyyy-MM-dd",
		metaValuesDefinition=TestDateMetaValuesDefinition.class
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
