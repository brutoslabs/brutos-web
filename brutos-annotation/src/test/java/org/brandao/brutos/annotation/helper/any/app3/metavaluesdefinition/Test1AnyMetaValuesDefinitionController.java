package org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition;

import java.util.Date;
import java.util.Map;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.any.app3.Property;
import org.brandao.brutos.annotation.helper.any.app3.PropertyType;

@Controller("/controller")
public class Test1AnyMetaValuesDefinitionController {

	@Transient
	private Map<Property,String> property;
	
	public void test1Action(
			@Basic(bean="property")
			@KeyCollection(
				any=
					@Any(
						metaBean=@Basic(bean="propertyType"),
						metaType=String.class,
						metaValuesDefinition=TestMetaValuesDefinition.class
					)
				)
			Map<Property,String> property){
		this.property = property;
	}

	public void test2Action(
			@Basic(bean="property")
			@KeyCollection(
				any=
					@Any(
						metaBean=@Basic(bean="propertyType"),
						metaType=PropertyType.class,
						metaEnumerated=EnumerationType.STRING,
						metaValuesDefinition=TestEnumMetaValuesDefinition.class
					)
				)
			Map<Property,String> property){
		this.property = property;
	}
	
	public void test3Action(
			@Basic(bean="property")
			@KeyCollection(
				any=
					@Any(
						metaBean=@Basic(bean="propertyType"),
						metaType=Integer.class,
						metaValuesDefinition=TestDecimalMetaValuesDefinition.class
					)
				)
			Map<Property,String> property){
		this.property = property;
	}

	public void test4Action(
			@Basic(bean="property")
			@KeyCollection(
				any=
					@Any(
						metaBean=@Basic(bean="propertyType"),
						metaType=Date.class,
						metaTemporal="yyyy-MM-dd",
						metaValuesDefinition=TestDateMetaValuesDefinition.class
					)
				)
			Map<Property,String> property){
		this.property = property;
	}
	
	public Map<Property,String> getProperty() {
		return property;
	}

	public void setProperty(Map<Property,String> property) {
		this.property = property;
	}
	
	
}
