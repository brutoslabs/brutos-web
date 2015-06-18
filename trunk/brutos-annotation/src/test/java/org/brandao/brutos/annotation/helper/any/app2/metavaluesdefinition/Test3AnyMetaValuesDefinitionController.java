package org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition;

import java.util.List;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.helper.any.app1.Property;
import org.brandao.brutos.annotation.helper.any.app1.PropertyType;

@Controller("/controller")
public class Test3AnyMetaValuesDefinitionController {

	@Basic(bean="propertyA")
	@ElementCollection(
		any=
			@Any(
				metaBean=@Basic(bean="propertyType"),
				metaType=PropertyType.class,
				metaEnumerated=EnumerationType.STRING,
				metaValuesDefinition=TestMetaValuesDefinition.class
			)
		)
	public List<Property> property1;
	
	private List<Property> property2;

	public List<Property> getProperty2() {
		return property2;
	}

	@Basic(bean="propertyB")
	@ElementCollection(
		any=
			@Any(
				metaBean=@Basic(bean="propertyType2"),
				metaType=PropertyType.class,
				metaEnumerated=EnumerationType.STRING,
				metaValuesDefinition=TestMetaValuesDefinition.class
			)
		)
	public void setProperty2(List<Property> property2) {
		this.property2 = property2;
	}
	
	
}
