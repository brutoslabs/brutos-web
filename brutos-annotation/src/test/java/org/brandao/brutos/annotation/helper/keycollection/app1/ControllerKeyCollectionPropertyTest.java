package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Date;
import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.ScopeType;
import org.brandao.brutos.annotation.helper.EnumTest;
import org.brandao.brutos.type.StringType;

public class ControllerKeyCollectionPropertyTest {

    private Map<Integer,Integer> property;

    private Map<Integer,Integer> property2;

    private Map<Integer,Integer> property3;

    private Map<EnumTest,EnumTest> property4;

    private Map<Integer,Integer> property5;

    private Map<Date,Date> property6;

    private Map property7;

    private Map property8;

    private Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property9;

    private Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property10;
    
    private Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property11;

    private Map<EnumTest,EnumTest> property12;
    
    public void testAction(){
    }
    
	public Map<Integer,Integer> getProperty() {
		return property;
	}

	public void setProperty(Map<Integer,Integer> property) {
		this.property = property;
	}

	public Map<Integer,Integer> getProperty2() {
		return property2;
	}

    @KeyCollection
    @ElementCollection
	public void setProperty2(Map<Integer,Integer> property2) {
		this.property2 = property2;
	}

	public Map<Integer,Integer> getProperty3() {
		return property3;
	}

    @KeyCollection(bean="kly")
    @ElementCollection(bean="elx")
	public void setProperty3(Map<Integer,Integer> property3) {
		this.property3 = property3;
	}

	public Map<EnumTest,EnumTest> getProperty4() {
		return property4;
	}

    @KeyCollection(enumerated=EnumerationType.STRING)
    @ElementCollection(enumerated=EnumerationType.STRING)
	public void setProperty4(Map<EnumTest,EnumTest> property4) {
		this.property4 = property4;
	}

	public Map<Integer,Integer> getProperty5() {
		return property5;
	}

    @KeyCollection(scope=ScopeType.SESSION)
    @ElementCollection(scope=ScopeType.SESSION)
	public void setProperty5(Map<Integer,Integer> property5) {
		this.property5 = property5;
	}

	public Map<Date,Date> getProperty6() {
		return property6;
	}

    @KeyCollection(temporal="mm-dd-yyyy")
    @ElementCollection(temporal="mm-dd-yyyy")
	public void setProperty6(Map<Date,Date> property6) {
		this.property6 = property6;
	}

	public Map getProperty7() {
		return property7;
	}

    @KeyCollection(target=Integer.class)
    @ElementCollection(target=Integer.class)
	public void setProperty7(Map property7) {
		this.property7 = property7;
	}

	public Map getProperty8() {
		return property8;
	}

    @KeyCollection(type=StringType.class)
    @ElementCollection(type=StringType.class)
	public void setProperty8(Map property8) {
		this.property8 = property8;
	}

	public Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> getProperty9() {
		return property9;
	}

    @KeyCollection(mappingType=MappingTypes.SIMPLE)
    @ElementCollection(mappingType=MappingTypes.SIMPLE)
	public void setProperty9(Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property9) {
		this.property9 = property9;
	}

	public Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> getProperty10() {
		return property10;
	}

    @KeyCollection(mappingType=MappingTypes.COMPLEX)
    @ElementCollection(mappingType=MappingTypes.COMPLEX)
	public void setProperty10(Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property10) {
		this.property10 = property10;
	}

	public Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> getProperty11() {
		return property11;
	}

	public void setProperty11(Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property11) {
		this.property11 = property11;
	}

	public Map<EnumTest,EnumTest> getProperty12() {
		return property12;
	}

	public void setProperty12(Map<EnumTest,EnumTest> property12) {
		this.property12 = property12;
	}
	
}
