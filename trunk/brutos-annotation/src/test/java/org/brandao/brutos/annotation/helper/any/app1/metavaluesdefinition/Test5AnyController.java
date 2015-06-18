package org.brandao.brutos.annotation.helper.any.app1.metavaluesdefinition;

import java.util.Date;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.helper.any.app1.Property;

@Controller("/controller")
public class Test5AnyController {

	@Basic(bean="propertyA")
	@Any(
		metaBean=@Basic(bean="propertyType"),
		metaType=Date.class,
		metaTemporal="yyyy-MM-dd",
		metaValuesDefinition=TestMetaValuesDefinition.class
	)
	public Property property1;
	
	private Property property2;

	public Property getProperty2() {
		return property2;
	}

	@Basic(bean="propertyB")
	@Any(
		metaBean=@Basic(bean="propertyType2"),
		metaType=Date.class,
		metaTemporal="yyyy-MM-dd",
		metaValuesDefinition=TestMetaValuesDefinition.class
	)
	public void setProperty2(Property property2) {
		this.property2 = property2;
	}
	
	
}
