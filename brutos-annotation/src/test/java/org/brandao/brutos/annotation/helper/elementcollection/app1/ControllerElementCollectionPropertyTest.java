package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.Date;
import java.util.List;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.ScopeType;
import org.brandao.brutos.annotation.helper.EnumTest;
import org.brandao.brutos.type.StringType;

@Controller("/controller")
public class ControllerElementCollectionPropertyTest {

    private List<Integer> property;

    private List<Integer> property2;

    private List<Integer> property3;

    private List<EnumTest> property4;

    private List<Integer> property5;

    private List<Date> property6;

    private List property7;

    private List property8;

    private List<ElementCollectionBeanTest0> property9;

    private List<ElementCollectionBeanTest0> property10;
    
    private List<ElementCollectionBeanTest0> property11;

    private List<EnumTest> property12;
    
    public void testAction(){
    }
    
	public List<Integer> getProperty() {
		return property;
	}

	public void setProperty(List<Integer> property) {
		this.property = property;
	}

	public List<Integer> getProperty2() {
		return property2;
	}

    @ElementCollection
	public void setProperty2(List<Integer> property2) {
		this.property2 = property2;
	}

	public List<Integer> getProperty3() {
		return property3;
	}

    @ElementCollection(bean="elx")
	public void setProperty3(List<Integer> property3) {
		this.property3 = property3;
	}

	public List<EnumTest> getProperty4() {
		return property4;
	}

    @ElementCollection(enumerated=EnumerationType.STRING)
	public void setProperty4(List<EnumTest> property4) {
		this.property4 = property4;
	}

	public List<Integer> getProperty5() {
		return property5;
	}

    @ElementCollection(scope=ScopeType.SESSION)
	public void setProperty5(List<Integer> property5) {
		this.property5 = property5;
	}

	public List<Date> getProperty6() {
		return property6;
	}

    @ElementCollection(temporal="mm-dd-yyyy")
	public void setProperty6(List<Date> property6) {
		this.property6 = property6;
	}

	public List getProperty7() {
		return property7;
	}

    @ElementCollection(target=Integer.class)
	public void setProperty7(List property7) {
		this.property7 = property7;
	}

	public List getProperty8() {
		return property8;
	}

    @ElementCollection(type=StringType.class)
	public void setProperty8(List property8) {
		this.property8 = property8;
	}

	public List<ElementCollectionBeanTest0> getProperty9() {
		return property9;
	}

    @ElementCollection(mappingType=MappingTypes.SIMPLE, type=ElementCollectionBeanTest0Type.class)
	public void setProperty9(List<ElementCollectionBeanTest0> property9) {
		this.property9 = property9;
	}

	public List<ElementCollectionBeanTest0> getProperty10() {
		return property10;
	}

    @ElementCollection(mappingType=MappingTypes.COMPLEX)
	public void setProperty10(List<ElementCollectionBeanTest0> property10) {
		this.property10 = property10;
	}

	public List<ElementCollectionBeanTest0> getProperty11() {
		return property11;
	}

	public void setProperty11(List<ElementCollectionBeanTest0> property11) {
		this.property11 = property11;
	}

	public List<EnumTest> getProperty12() {
		return property12;
	}

	public void setProperty12(List<EnumTest> property12) {
		this.property12 = property12;
	}
	
}
