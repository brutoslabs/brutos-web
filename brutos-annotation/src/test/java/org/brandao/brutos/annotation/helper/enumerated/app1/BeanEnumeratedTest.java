package org.brandao.brutos.annotation.helper.enumerated.app1;

import org.brandao.brutos.annotation.Enumerated;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.helper.EnumTest;

public class BeanEnumeratedTest {

	public EnumTest property;
	
	@Enumerated
	public EnumTest property2;
	
	@Enumerated(EnumerationType.AUTO)
	public EnumTest property3;
	
	@Enumerated(EnumerationType.ORDINAL)
	public EnumTest property4;
	
	@Enumerated(EnumerationType.STRING)
	public EnumTest property5;
	
	private EnumTest property6;
	
	private EnumTest property7;
	
	private EnumTest property8;
	
	private EnumTest property9;
	
	private EnumTest property10;

	public EnumTest getProperty6() {
		return property6;
	}

	public void setProperty6(EnumTest property6) {
		this.property6 = property6;
	}

	public EnumTest getProperty7() {
		return property7;
	}

	@Enumerated
	public void setProperty7(EnumTest property7) {
		this.property7 = property7;
	}

	public EnumTest getProperty8() {
		return property8;
	}

	@Enumerated(EnumerationType.AUTO)
	public void setProperty8(EnumTest property8) {
		this.property8 = property8;
	}

	public EnumTest getProperty9() {
		return property9;
	}

	@Enumerated(EnumerationType.ORDINAL)
	public void setProperty9(EnumTest property9) {
		this.property9 = property9;
	}

	public EnumTest getProperty10() {
		return property10;
	}

	@Enumerated(EnumerationType.STRING)
	public void setProperty10(EnumTest property10) {
		this.property10 = property10;
	}
	
}
