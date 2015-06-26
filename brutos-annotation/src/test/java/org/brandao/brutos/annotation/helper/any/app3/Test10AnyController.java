package org.brandao.brutos.annotation.helper.any.app3;

import java.util.Map;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.Transient;

@Controller("/controller")
public class Test10AnyController {

	public Test5AnyBean property1;

	@Basic(bean="propertyD")
	@KeyCollection(
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
	public Map<Property,String> property2;
	
	@Transient
	public Map<Property,String> property3;
	
	public void testAction(
			@Basic(bean="propertyE")
			@KeyCollection(
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
			Map<Property,String> arg){
		this.property3 = arg;
	}
}
