package org.brandao.brutos.annotation.helper.keycollection.fail;

import java.util.Map;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.helper.elementcollection.app1.ElementCollectionBeanTest0;

@Controller
public class ControllerKeyCollectionUnknownTypeTest {

	public void propertyAction(
			@KeyCollection(mappingType=MappingTypes.SIMPLE) 
			Map<ElementCollectionBeanTest0,String> property9) {
	}
	
}
