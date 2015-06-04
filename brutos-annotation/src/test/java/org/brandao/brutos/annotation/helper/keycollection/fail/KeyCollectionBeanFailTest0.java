package org.brandao.brutos.annotation.helper.keycollection.fail;

import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;

public class KeyCollectionBeanFailTest0 {

	@KeyCollection(bean="xxx")
	@ElementCollection(bean="xxx")
	private Map<Integer,Integer> property;
	
}
