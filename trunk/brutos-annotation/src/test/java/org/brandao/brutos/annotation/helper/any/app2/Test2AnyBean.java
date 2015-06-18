package org.brandao.brutos.annotation.helper.any.app2;

import java.util.List;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.Transient;

public class Test2AnyBean {

	@Basic(bean="propertyA")
	@ElementCollection(
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
	public List<Property> property1;
	
	private List<Property> property2;

	@Transient
	private List<Property> property3;
	
	public Test2AnyBean(
			@Basic(bean="propertyC")
			@ElementCollection(
				any=
					@Any(
						metaBean=@Basic(bean="propertyType3"),
						metaType=PropertyType.class,
						metaEnumerated=EnumerationType.STRING,
						metaValues={
							@MetaValue(name="DECIMAL", target=DecimalProperty.class),
							@MetaValue(name="SET", target=SetProperty.class)
						}
					)
				)
			List<Property> property3 ){
		this.property3 = property3;
	}
	
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
				metaValues={
					@MetaValue(name="DECIMAL", target=DecimalProperty.class),
					@MetaValue(name="SET", target=SetProperty.class)
				}
			)
		)
	public void setProperty2(List<Property> property2) {
		this.property2 = property2;
	}

	public List<Property> getProperty3() {
		return property3;
	}

	public void setProperty3(List<Property> property3) {
		this.property3 = property3;
	}

	
}
