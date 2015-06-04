package org.brandao.brutos.annotation.helper.elementcollection.app1;

import org.brandao.brutos.annotation.Controller;

@Controller("/controller")
public class ControllerElementCollectionBeanTest {

	private ElementCollectionFieldTest property1;
	
	private ElementCollectionPropertyTest property2;
	
	public void testAction(){
	}

	public ElementCollectionFieldTest getProperty1() {
		return property1;
	}

	public void setProperty1(ElementCollectionFieldTest property1) {
		this.property1 = property1;
	}

	public ElementCollectionPropertyTest getProperty2() {
		return property2;
	}

	public void setProperty2(ElementCollectionPropertyTest property2) {
		this.property2 = property2;
	}
	
}
