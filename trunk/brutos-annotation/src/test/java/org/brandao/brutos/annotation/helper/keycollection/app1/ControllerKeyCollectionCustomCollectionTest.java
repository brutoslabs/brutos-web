package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Map;

import org.brandao.brutos.annotation.Controller;

@Controller("/controller")
public class ControllerKeyCollectionCustomCollectionTest {

	public Map<CustomMap,String> property;
	
	public Map<Map<Integer,String>, String> property2;
	
}
