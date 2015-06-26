package org.brandao.brutos.annotation.helper.any.app2;

import java.util.List;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.Transient;

@Controller("/controller")
public class Test10AnyController {

	public Test5AnyBean property1;

	@Basic(bean="propertyD")
	@ElementCollection(
		any=
			@Any(
				metaBean=@Basic(bean="propertyType4"),
				metaType=String.class,
				metaValues={
					@MetaValue(name="decimal", target=DecimalProperty.class),
					@MetaValue(name="set", target=SetProperty.class)
				},
				metaTypeDef=TestStringType.class
			)
	)
	public List<Property> property2;
	
	@Transient
	public List<Property> property3;
	
	public void testAction(
			@Basic(bean="propertyE")
			@ElementCollection(
				any=
			
					@Any(
						metaBean=@Basic(bean="propertyType5"),
						metaType=String.class,
						metaValues={
							@MetaValue(name="decimal", target=DecimalProperty.class),
							@MetaValue(name="set", target=SetProperty.class)
						},
						metaTypeDef=TestStringType.class
					)
				)
			List<Property> arg){
		this.property3 = arg;
	}
}
