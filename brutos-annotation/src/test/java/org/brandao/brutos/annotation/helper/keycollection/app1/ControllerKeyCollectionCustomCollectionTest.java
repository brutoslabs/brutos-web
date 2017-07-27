package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Map;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.KeyCollection;

@Controller("/controller")
public class ControllerKeyCollectionCustomCollectionTest {

	@KeyCollection(bean="key")
	public Map<CustomMap,String> property;
	
	@KeyCollection(bean="key")
	public Map<Map<Integer,String>, String> property2;
	
}
