package org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition;

import java.util.Date;
import java.util.Map;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.helper.any.app3.Property;

@Controller("/controller")
public class Test5AnyMetaValuesDefinitionController {

	@Basic(bean="propertyA")
	@KeyCollection(
		bean="key",
		any=
			@Any(
				metaBean=@Basic(bean="propertyType"),
				metaType=Date.class,
				metaTemporal="yyyy-MM-dd",
				metaValuesDefinition=TestDateMetaValuesDefinition.class
			)
		)
	public Map<Property,String> property1;
	
	private Map<Property,String> property2;

	public Map<Property,String> getProperty2() {
		return property2;
	}

	@Basic(bean="propertyB")
	@KeyCollection(
		bean="key",
		any=
			@Any(
				metaBean=@Basic(bean="propertyType2"),
				metaType=Date.class,
				metaTemporal="yyyy-MM-dd",
				metaValuesDefinition=TestDateMetaValuesDefinition.class
			)
		)
	public void setProperty2(Map<Property,String> property2) {
		this.property2 = property2;
	}
	
	
}
