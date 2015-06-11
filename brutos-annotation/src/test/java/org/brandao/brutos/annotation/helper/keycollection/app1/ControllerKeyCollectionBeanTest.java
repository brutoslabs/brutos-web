package org.brandao.brutos.annotation.helper.keycollection.app1;

import org.brandao.brutos.annotation.Controller;

@Controller("/controller")
public class ControllerKeyCollectionBeanTest {

	private KeyCollectionFieldTest property1;
	
	private KeyCollectionPropertyTest property2;
	
	public void testAction(){
	}

	public KeyCollectionFieldTest getProperty1() {
		return property1;
	}

	public void setProperty1(KeyCollectionFieldTest property1) {
		this.property1 = property1;
	}

	public KeyCollectionPropertyTest getProperty2() {
		return property2;
	}

	public void setProperty2(KeyCollectionPropertyTest property2) {
		this.property2 = property2;
	}
	
}
