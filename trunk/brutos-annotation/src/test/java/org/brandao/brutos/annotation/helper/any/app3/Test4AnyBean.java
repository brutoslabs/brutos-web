package org.brandao.brutos.annotation.helper.any.app3;

import java.util.Date;
import java.util.Map;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.Transient;

public class Test4AnyBean {

	@Basic(bean="propertyA")
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
	public Map<Property,String> property1;
	
	private Map<Property,String> property2;

	@Transient
	private Map<Property,String> property3;
	
	public Test4AnyBean(
			@Basic(bean="propertyC")
			@KeyCollection(
				bean="key",
				any=
					@Any(
						metaBean=@Basic(bean="propertyType3"),
						metaType=Date.class,
						metaTemporal="yyyy-MM-dd",
						metaValues={
							@MetaValue(name="2015-01-01", target=DecimalProperty.class),
							@MetaValue(name="2015-01-02", target=SetProperty.class)
						}
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
		bean="key",
		any=
			@Any(
				metaBean=@Basic(bean="propertyType2"),
				metaType=Date.class,
				metaTemporal="yyyy-MM-dd",
				metaValues={
					@MetaValue(name="2015-01-01", target=DecimalProperty.class),
					@MetaValue(name="2015-01-02", target=SetProperty.class)
				}
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
