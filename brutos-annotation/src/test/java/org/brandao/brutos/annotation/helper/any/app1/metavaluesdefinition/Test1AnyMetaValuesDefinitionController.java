package org.brandao.brutos.annotation.helper.any.app1.metavaluesdefinition;

import java.util.Date;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.any.app1.Property;
import org.brandao.brutos.annotation.helper.any.app1.PropertyType;

@Controller("/controller")
public class Test1AnyMetaValuesDefinitionController {

	@Transient
	private Property property;
	
	public void test1Action(
			@Basic(bean="property")
			@Any(
				metaBean=@Basic(bean="propertyType"),
				metaType=String.class,
				metaValuesDefinition=TestMetaValuesDefinition.class
			)
			Property property){
		this.property = property;
	}

	public void test2Action(
			@Basic(bean="property")
			@Any(
				metaBean=@Basic(bean="propertyType"),
				metaType=PropertyType.class,
				metaEnumerated=EnumerationType.STRING,
				metaValuesDefinition=TestMetaValuesDefinition.class
			)
			Property property){
		this.property = property;
	}
	
	public void test3Action(
			@Basic(bean="property")
			@Any(
				metaBean=@Basic(bean="propertyType"),
				metaType=Integer.class,
				metaValuesDefinition=TestMetaValuesDefinition.class
			)
			Property property){
		this.property = property;
	}

	public void test4Action(
			@Basic(bean="property")
			@Any(
				metaBean=@Basic(bean="propertyType"),
				metaType=Date.class,
				metaTemporal="yyyy-MM-dd",
				metaValuesDefinition=TestMetaValuesDefinition.class
			)
			Property property){
		this.property = property;
	}
	
	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}
	
	
}
