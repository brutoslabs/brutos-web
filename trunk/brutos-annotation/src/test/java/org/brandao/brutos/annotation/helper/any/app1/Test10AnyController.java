package org.brandao.brutos.annotation.helper.any.app1;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.Transient;

@Controller("/controller")
public class Test10AnyController {

	public Test5AnyBean property1;

	@Basic(bean="propertyD")
	@Any(
		metaBean=@Basic(bean="propertyType4"),
		metaType=String.class,
		metaValues={
			@MetaValue(name="decimal", target=DecimalProperty.class),
			@MetaValue(name="set", target=SetProperty.class)
		},
		metaTypeDef=TestStringType.class
	)
	public Property property2;
	
	@Transient
	public Property property3;
	
	public void testAction(
			@Basic(bean="propertyE")
			@Any(
				metaBean=@Basic(bean="propertyType5"),
				metaType=String.class,
				metaValues={
					@MetaValue(name="decimal", target=DecimalProperty.class),
					@MetaValue(name="set", target=SetProperty.class)
				},
				metaTypeDef=TestStringType.class
			)
			Property arg){
		this.property3 = arg;
	}
}
