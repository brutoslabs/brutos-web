package org.brandao.brutos.annotation.helper.any.app3.fail.metavaluesdefinition;


import java.util.Map;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.helper.any.app1.Property;

@Controller("/controller")
public class Test2FailAnyMetaValuesDefinitionController {

	public void testAction(
			@KeyCollection(
				bean="key",
				any=
					@Any(
						metaBean=@Basic(bean="test"),
						metaValuesDefinition=TestEmptyMetaValuesDefinition.class)
				)
			Map<Property,String> arg0){
	}
	
}
