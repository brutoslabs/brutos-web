package org.brandao.brutos.annotation.helper.type.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.Type;

@Controller("/controller")
public class Test1TypeController {

	@Type(TestStringType.class)
	public String property1;
	
	private String property2;

	@Transient
	public String property3;
	
	public Test1TypeBean property4;
	
	public String getProperty2() {
		return property2;
	}

	@Type(TestStringType.class)
	public void setProperty2(String property2) {
		this.property2 = property2;
	}
	
	public void testAction(	@Type(TestStringType.class) String arg0){
		this.property3 = arg0;
	}
}
