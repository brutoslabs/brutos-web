package org.brandao.brutos.annotation.helper.enumerated.app1;

import org.brandao.brutos.annotation.Enumerated;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.EnumTest;

public class BeanConstructorEnumTestEnumeratedAuto {

	@Transient
	private EnumTest property;

	public BeanConstructorEnumTestEnumeratedAuto(@Enumerated(EnumerationType.AUTO) EnumTest property){
		this.property = property;
	}
	
	public EnumTest getProperty() {
		return property;
	}

	public void setProperty(EnumTest property) {
		this.property = property;
	}
	
	
}
