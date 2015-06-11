package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Date;
import java.util.Map;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.ScopeType;
import org.brandao.brutos.annotation.helper.EnumTest;

@Controller("/controller")
public class ControllerKeyCollectionPropertyTest {

    private Map<Integer,String> property;

    private Map<Integer,String> property2;

    private Map<Integer,String> property3;

    private Map<EnumTest,String> property4;

    private Map<Integer,String> property5;

    private Map<Date,String> property6;

    private Map property7;

    private Map property8;

    private Map<KeyCollectionBeanTest0,String> property9;

    private Map<KeyCollectionBeanTest0,String> property10;
    
    private Map<KeyCollectionBeanTest0,String> property11;

    private Map<EnumTest,String> property12;
    
    public void testAction(){
    }
    
	public Map<Integer,String> getProperty() {
		return property;
	}

	public void setProperty(Map<Integer,String> property) {
		this.property = property;
	}

	public Map<Integer,String> getProperty2() {
		return property2;
	}

    @KeyCollection
	public void setProperty2(Map<Integer,String> property2) {
		this.property2 = property2;
	}

	public Map<Integer,String> getProperty3() {
		return property3;
	}

	@KeyCollection(bean="elx")
	public void setProperty3(Map<Integer,String> property3) {
		this.property3 = property3;
	}

	public Map<EnumTest,String> getProperty4() {
		return property4;
	}

	@KeyCollection(enumerated=EnumerationType.STRING)
	public void setProperty4(Map<EnumTest,String> property4) {
		this.property4 = property4;
	}

	public Map<Integer,String> getProperty5() {
		return property5;
	}

	@KeyCollection(scope=ScopeType.SESSION)
	public void setProperty5(Map<Integer,String> property5) {
		this.property5 = property5;
	}

	public Map<Date,String> getProperty6() {
		return property6;
	}

	@KeyCollection(temporal="mm-dd-yyyy")
	public void setProperty6(Map<Date,String> property6) {
		this.property6 = property6;
	}

	public Map getProperty7() {
		return property7;
	}

	@KeyCollection(target=Integer.class)
	@ElementCollection(target=String.class)
	public void setProperty7(Map property7) {
		this.property7 = property7;
	}

	public Map getProperty8() {
		return property8;
	}

	@KeyCollection(type=TestStringType.class, target=String.class)
	@ElementCollection(target=String.class)
	public void setProperty8(Map property8) {
		this.property8 = property8;
	}

	public Map<KeyCollectionBeanTest0,String> getProperty9() {
		return property9;
	}

	@KeyCollection(mappingType=MappingTypes.SIMPLE, type=KeyCollectionBeanTest0Type.class)
	public void setProperty9(Map<KeyCollectionBeanTest0,String> property9) {
		this.property9 = property9;
	}

	public Map<KeyCollectionBeanTest0,String> getProperty10() {
		return property10;
	}

	@KeyCollection(mappingType=MappingTypes.COMPLEX)
	public void setProperty10(Map<KeyCollectionBeanTest0,String> property10) {
		this.property10 = property10;
	}

	public Map<KeyCollectionBeanTest0,String> getProperty11() {
		return property11;
	}

	public void setProperty11(Map<KeyCollectionBeanTest0,String> property11) {
		this.property11 = property11;
	}

	public Map<EnumTest,String> getProperty12() {
		return property12;
	}

	public void setProperty12(Map<EnumTest,String> property12) {
		this.property12 = property12;
	}
	
}
