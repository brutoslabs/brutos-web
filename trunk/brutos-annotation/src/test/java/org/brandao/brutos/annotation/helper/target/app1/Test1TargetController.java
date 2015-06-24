package org.brandao.brutos.annotation.helper.target.app1;

import java.math.BigDecimal;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Target;
import org.brandao.brutos.annotation.Transient;

@Controller("/controller")
public class Test1TargetController {

	@Target(Integer.class)
	public Object property1;
	
	private Object property2;

	@Transient
	public Object property3;
	
	@Target(Test1TargetBean.class)
	public Object property4;
	
	public Object getProperty2() {
		return property2;
	}

	@Target(Long.class)
	public void setProperty2(Object property2) {
		this.property2 = property2;
	}
	
	public void testAction(@Target(BigDecimal.class) Object arg0){
		this.property3 = arg0;
	}
}
