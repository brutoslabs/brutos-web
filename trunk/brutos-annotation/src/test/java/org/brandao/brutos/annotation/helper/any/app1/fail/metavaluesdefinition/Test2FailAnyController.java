package org.brandao.brutos.annotation.helper.any.app1.fail.metavaluesdefinition;


import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.helper.any.app1.Property;

@Controller("/controller")
public class Test2FailAnyController {

	public void testAction(
			@Any(
				metaBean=@Basic(bean="test"),
				metaValuesDefinition=TestEmptyMetaValuesDefinition.class)
			Property arg0){
	}
	
}
