package org.brandao.brutos.annotation.helper.enumerated.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Enumerated;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.EnumTest;

@Controller("/controller")
public class ControllerEnumeratedTest {

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

	@Transient
	public EnumTest property11;
	
	@Transient
	public EnumTest property12;
	
	@Transient
	public EnumTest property13;
	
	@Transient
	public EnumTest property14;
	
	@Transient
	public EnumTest property15;
	
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
	
	public void property11Action(EnumTest property11) {
		this.property11 = property11;
	}

	public void property12Action(@Enumerated EnumTest property12) {
		this.property12 = property12;
	}
	
	public void property13Action(@Enumerated(EnumerationType.AUTO) EnumTest property13) {
		this.property13 = property13;
	}

	public void property14Action(@Enumerated(EnumerationType.ORDINAL) EnumTest property14) {
		this.property14 = property14;
	}

	public void property15Action(@Enumerated(EnumerationType.STRING) EnumTest property15) {
		this.property15 = property15;
	}
	
}
