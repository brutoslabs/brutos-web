package org.brandao.brutos.annotation.helper.enumerated.app1;

import org.brandao.brutos.annotation.Enumerated;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.EnumTest;

public class BeanConstructorEnumTestEnumerated {

	@Transient
	private EnumTest property;

	public BeanConstructorEnumTestEnumerated(@Enumerated EnumTest property){
		this.property = property;
	}
	
	public EnumTest getProperty() {
		return property;
	}

	public void setProperty(EnumTest property) {
		this.property = property;
	}
	
	
}
