package org.brandao.brutos.annotation.helper.any.app1;

import java.util.Date;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.MetaValue;

@Controller("/controller")
public class Test5AnyController {

	@Basic(bean="propertyA")
	@Any(
		metaBean=@Basic(bean="propertyType"),
		metaType=Date.class,
		metaTemporal="yyyy-MM-dd",
		metaValues={
			@MetaValue(name="2015-01-01", target=DecimalProperty.class),
			@MetaValue(name="2015-01-02", target=SetProperty.class)
		}
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
		metaValues={
			@MetaValue(name="2015-01-01", target=DecimalProperty.class),
			@MetaValue(name="2015-01-02", target=SetProperty.class)
		}
	)
	public void setProperty2(Property property2) {
		this.property2 = property2;
	}
	
	
}
