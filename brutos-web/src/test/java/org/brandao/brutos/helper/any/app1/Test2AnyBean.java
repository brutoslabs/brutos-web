package org.brandao.brutos.helper.any.app1;

public class Test2AnyBean {

	public Property property1;
	
	private Property property2;

	private Property property3;
	
	public Test2AnyBean(Property property3 ){
		this.property3 = property3;
	}
	
	public Property getProperty2() {
		return property2;
	}

	public void setProperty2(Property property2) {
		this.property2 = property2;
	}

	public Property getProperty3() {
		return property3;
	}

	public void setProperty3(Property property3) {
		this.property3 = property3;
	}
	
}
