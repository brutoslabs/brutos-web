package org.brandao.brutos.annotation.helper.elementcollection.fail;

import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;

public class ElementCollectionBeanFailTest0 {

	@KeyCollection(bean="xxx")
	@ElementCollection(bean="xxx")
	private Map<Integer,Integer> property;
	
}
