package org.brandao.brutos.annotation.helper.target.app1;

import java.math.BigDecimal;

import org.brandao.brutos.annotation.Target;
import org.brandao.brutos.annotation.Transient;

public class Test1TargetBean {

	@Target(Integer.class)
	public Object property1;
	
	private Object property2;

	@Transient
	public Object property3;
	
	public Test1TargetBean(@Target(BigDecimal.class) Object arg0){
		this.property3 = arg0;
	}
	
	public Object getProperty2() {
		return property2;
	}

	@Target(Long.class)
	public void setProperty2(Object property2) {
		this.property2 = property2;
	}
	
}
