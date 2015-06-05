package org.brandao.brutos.annotation.helper.elementcollection.fail;

import java.util.List;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.helper.elementcollection.app1.ElementCollectionBeanTest0;

public class ControllerElementCollectionUnknownTypeTest {

	public void propertyAction(
			@ElementCollection(mappingType=MappingTypes.SIMPLE) 
			List<ElementCollectionBeanTest0> property9) {
	}
	
}
