package org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition;

import java.util.Date;
import java.util.Map;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.any.app3.Property;

public class Test4AnyBeanMetaValuesDefinition {

	@Basic(bean="propertyA")
	@KeyCollection(
		any=
			@Any(
				metaBean=@Basic(bean="propertyType"),
				metaType=Date.class,
				metaTemporal="yyyy-MM-dd",
				metaValuesDefinition=TestMetaValuesDefinition.class
			)
		)
	public Map<Property,String> property1;
	
	private Map<Property,String> property2;

	@Transient
	private Map<Property,String> property3;
	
	public Test4AnyBeanMetaValuesDefinition(
			@Basic(bean="propertyC")
			@KeyCollection(
				any=
					@Any(
						metaBean=@Basic(bean="propertyType3"),
						metaType=Date.class,
						metaTemporal="yyyy-MM-dd",
						metaValuesDefinition=TestMetaValuesDefinition.class
					)
				)
			Map<Property,String> property3 ){
		this.property3 = property3;
	}
	
	public Map<Property,String> getProperty2() {
		return property2;
	}

	@Basic(bean="propertyB")
	@KeyCollection(
		any=
			@Any(
				metaBean=@Basic(bean="propertyType2"),
				metaType=Date.class,
				metaTemporal="yyyy-MM-dd",
				metaValuesDefinition=TestMetaValuesDefinition.class
			)
		)
	public void setProperty2(Map<Property,String> property2) {
		this.property2 = property2;
	}

	public Map<Property,String> getProperty3() {
		return property3;
	}

	public void setProperty3(Map<Property,String> property3) {
		this.property3 = property3;
	}

	
}
