package org.brandao.brutos.helper.any.app1;

public class Test1AnyController {

	private Property property;
	
	public void test1Action(Property property){
		this.property = property;
	}

	public void test2Action(Property property){
		this.property = property;
	}
	
	public void test3Action(Property property){
		this.property = property;
	}

	public void test4Action(Property property){
		this.property = property;
	}
	
	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}
	
	
}
