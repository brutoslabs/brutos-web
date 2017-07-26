package org.brandao.brutos.annotation.helper.any.app3;

import java.util.Date;
import java.util.Map;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.Transient;

@Controller("/controller")
public class Test1AnyController {

	@Transient
	private Map<Property,String> property;
	
	public void test1Action(
			@Basic(bean="property")
			@KeyCollection(
				bean="key",
				any=
				@Any(
					metaBean=@Basic(bean="propertyType"),
					metaType=String.class,
					metaValues={
						@MetaValue(name="decimal", target=DecimalProperty.class),
						@MetaValue(name="set", target=SetProperty.class)
					}
				)
			)
			Map<Property,String> property){
		this.property = property;
	}

	public void test2Action(
			@Basic(bean="property")
			@KeyCollection(
				bean="key",
				any=
					@Any(
						metaBean=@Basic(bean="propertyType"),
						metaType=PropertyType.class,
						metaEnumerated=EnumerationType.STRING,
						metaValues={
							@MetaValue(name="DECIMAL", target=DecimalProperty.class),
							@MetaValue(name="SET", target=SetProperty.class)
						}
					)
				)
			Map<Property,String> property){
		this.property = property;
	}
	
	public void test3Action(
			@Basic(bean="property")
			@KeyCollection(
				bean="key",
				any=
				@Any(
					metaBean=@Basic(bean="propertyType"),
					metaType=Integer.class,
					metaValues={
						@MetaValue(name="0", target=DecimalProperty.class),
						@MetaValue(name="1", target=SetProperty.class)
					}
				)
				)
			Map<Property,String> property){
		this.property = property;
	}

	public void test4Action(
			@Basic(bean="property")
			@KeyCollection(
				bean="key",
				any=
				@Any(
					metaBean=@Basic(bean="propertyType"),
					metaType=Date.class,
					metaTemporal="yyyy-MM-dd",
					metaValues={
						@MetaValue(name="2015-01-01", target=DecimalProperty.class),
						@MetaValue(name="2015-01-02", target=SetProperty.class)
					}
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
