package org.brandao.brutos.annotation.helper.any.app1;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.Transient;

public class Test2AnyBean {

	@Basic(bean="propertyA")
	@Any(
		metaBean=@Basic(bean="propertyType"),
		metaType=PropertyType.class,
		metaEnumerated=EnumerationType.STRING,
		metaValues={
			@MetaValue(name="DECIMAL", target=DecimalProperty.class),
			@MetaValue(name="SET", target=SetProperty.class)
		}
	)
	public Property property1;
	
	private Property property2;

	@Transient
	private Property property3;
	
	public Test2AnyBean(
			@Basic(bean="propertyC")
			@Any(
				metaBean=@Basic(bean="propertyType3"),
				metaType=PropertyType.class,
				metaEnumerated=EnumerationType.STRING,
				metaValues={
					@MetaValue(name="DECIMAL", target=DecimalProperty.class),
					@MetaValue(name="SET", target=SetProperty.class)
				}
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
		metaType=PropertyType.class,
		metaEnumerated=EnumerationType.STRING,
		metaValues={
			@MetaValue(name="DECIMAL", target=DecimalProperty.class),
			@MetaValue(name="SET", target=SetProperty.class)
		}
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
