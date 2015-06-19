package org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition;

import java.util.Date;
import java.util.List;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.any.app2.Property;
import org.brandao.brutos.annotation.helper.any.app2.PropertyType;

@Controller("/controller")
public class Test1AnyMetaValuesDefinitionController {

	@Transient
	private List<Property> property;
	
	public void test1Action(
			@Basic(bean="property")
			@ElementCollection(
				any=
					@Any(
						metaBean=@Basic(bean="propertyType"),
						metaType=String.class,
						metaValuesDefinition=TestMetaValuesDefinition.class
					)
				)
			List<Property> property){
		this.property = property;
	}

	public void test2Action(
			@Basic(bean="property")
			@ElementCollection(
				any=
					@Any(
						metaBean=@Basic(bean="propertyType"),
						metaType=PropertyType.class,
						metaEnumerated=EnumerationType.STRING,
						metaValuesDefinition=TestMetaValuesDefinition.class
					)
				)
			List<Property> property){
		this.property = property;
	}
	
	public void test3Action(
			@Basic(bean="property")
			@ElementCollection(
				any=
					@Any(
						metaBean=@Basic(bean="propertyType"),
						metaType=Integer.class,
						metaValuesDefinition=TestMetaValuesDefinition.class
					)
				)
			List<Property> property){
		this.property = property;
	}

	public void test4Action(
			@Basic(bean="property")
			@ElementCollection(
				any=
					@Any(
						metaBean=@Basic(bean="propertyType"),
						metaType=Date.class,
						metaTemporal="yyyy-MM-dd",
						metaValuesDefinition=TestMetaValuesDefinition.class
					)
				)
			List<Property> property){
		this.property = property;
	}
	
	public List<Property> getProperty() {
		return property;
	}

	public void setProperty(List<Property> property) {
		this.property = property;
	}
	
	
}
