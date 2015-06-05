package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.Date;
import java.util.List;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.ScopeType;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.EnumTest;
import org.brandao.brutos.type.StringType;

@Controller("/controller")
public class ControllerElementCollectionActionTest {

	@Transient
    private List<Integer> property;

	@Transient
    private List<Integer> property2;

	@Transient
    private List<Integer> property3;

	@Transient
    private List<EnumTest> property4;

	@Transient
    private List<Integer> property5;

	@Transient
    private List<Date> property6;

	@Transient
    private List property7;

	@Transient
    private List property8;

	@Transient
    private List<ElementCollectionBeanTest0> property9;

	@Transient
    private List<ElementCollectionBeanTest0> property10;
    
	@Transient
    private List<ElementCollectionBeanTest0> property11;

	@Transient
    private List<EnumTest> property12;
    
	public List<Integer> getProperty() {
		return property;
	}

	public void propertyAction(List<Integer> property) {
		this.property = property;
	}

	public List<Integer> getProperty2() {
		return property2;
	}

    
	public void property2Action(@ElementCollection List<Integer> property2) {
		this.property2 = property2;
	}

	public List<Integer> getProperty3() {
		return property3;
	}

    
	public void property3Action(@ElementCollection(bean="elx") List<Integer> property3) {
		this.property3 = property3;
	}

	public List<EnumTest> getProperty4() {
		return property4;
	}

    
	public void property4Action(@ElementCollection(enumerated=EnumerationType.STRING) List<EnumTest> property4) {
		this.property4 = property4;
	}

	public List<Integer> getProperty5() {
		return property5;
	}

    
	public void property5Action(@ElementCollection(scope=ScopeType.SESSION) List<Integer> property5) {
		this.property5 = property5;
	}

	public List<Date> getProperty6() {
		return property6;
	}

    
	public void property6Action(@ElementCollection(temporal="mm-dd-yyyy") List<Date> property6) {
		this.property6 = property6;
	}

	public List getProperty7() {
		return property7;
	}

    
	public void property7Action(@ElementCollection(target=Integer.class) List property7) {
		this.property7 = property7;
	}

	public List getProperty8() {
		return property8;
	}

    
	public void property8Action(@ElementCollection(type=TestStringType.class, target=String.class) List property8) {
		this.property8 = property8;
	}

	public List<ElementCollectionBeanTest0> getProperty9() {
		return property9;
	}

    
	public void property9Action(@ElementCollection(
			mappingType=MappingTypes.SIMPLE,
			type=ElementCollectionBeanTest0Type.class) 
			List<ElementCollectionBeanTest0> property9) {
		this.property9 = property9;
	}

	public List<ElementCollectionBeanTest0> getProperty10() {
		return property10;
	}

    
	public void property10Action(@ElementCollection(mappingType=MappingTypes.COMPLEX) List<ElementCollectionBeanTest0> property10) {
		this.property10 = property10;
	}

	public List<ElementCollectionBeanTest0> getProperty11() {
		return property11;
	}

	public void property11Action(List<ElementCollectionBeanTest0> property11) {
		this.property11 = property11;
	}

	public List<EnumTest> getProperty12() {
		return property12;
	}

	public void property12Action(List<EnumTest> property12) {
		this.property12 = property12;
	}
	
}
