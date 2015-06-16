package org.brandao.brutos.annotation.helper.type.app1;


import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.Type;

public class Test1TypeBean {

	@Type(TestStringType.class)
	public String property1;
	
	private String property2;

	@Transient
	public String property3;
	
	public Test1TypeBean(@Type(TestStringType.class) String arg0){
		this.property3 = arg0;
	}
	
	public String getProperty2() {
		return property2;
	}

	@Type(TestStringType.class)
	public void setProperty2(String property2) {
		this.property2 = property2;
	}
	
}
