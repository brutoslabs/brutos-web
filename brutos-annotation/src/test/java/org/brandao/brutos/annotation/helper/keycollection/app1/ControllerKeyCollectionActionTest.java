package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Date;
import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.ScopeType;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.EnumTest;
import org.brandao.brutos.type.StringType;

public class ControllerKeyCollectionActionTest {

	@Transient
    private Map<Integer,Integer> property;

	@Transient
    private Map<Integer,Integer> property2;

	@Transient
    private Map<Integer,Integer> property3;

	@Transient
    private Map<EnumTest,EnumTest> property4;

	@Transient
    private Map<Integer,Integer> property5;

	@Transient
    private Map<Date,Date> property6;

	@Transient
    private Map property7;

	@Transient
    private Map property8;

	@Transient
    private Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property9;

	@Transient
    private Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property10;
    
	@Transient
    private Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property11;

	@Transient
    private Map<EnumTest,EnumTest> property12;
    
    public void testAction(){
    }
    
	public Map<Integer,Integer> getProperty() {
		return property;
	}

	public void propertyAction(Map<Integer,Integer> property) {
		this.property = property;
	}

	public Map<Integer,Integer> getProperty2() {
		return property2;
	}

    
	public void property2Action(@ElementCollection Map<Integer,Integer> property2) {
		this.property2 = property2;
	}

	public Map<Integer,Integer> getProperty3() {
		return property3;
	}

    
	public void property3Action(@KeyCollection(bean="kly")@ElementCollection(bean="elx") Map<Integer,Integer> property3) {
		this.property3 = property3;
	}

	public Map<EnumTest,EnumTest> getProperty4() {
		return property4;
	}

    
	public void property4Action(@KeyCollection(enumerated=EnumerationType.STRING)@ElementCollection(enumerated=EnumerationType.STRING) Map<EnumTest,EnumTest> property4) {
		this.property4 = property4;
	}

	public Map<Integer,Integer> getProperty5() {
		return property5;
	}

    
	public void property5Action(@KeyCollection(scope=ScopeType.SESSION)@ElementCollection(scope=ScopeType.SESSION) Map<Integer,Integer> property5) {
		this.property5 = property5;
	}

	public Map<Date,Date> getProperty6() {
		return property6;
	}

    
	public void property6Action(@KeyCollection(temporal="mm-dd-yyyy")@ElementCollection(temporal="mm-dd-yyyy") Map<Date,Date> property6) {
		this.property6 = property6;
	}

	public Map getProperty7() {
		return property7;
	}

    
	public void property7Action(@KeyCollection(target=Integer.class)@ElementCollection(target=Integer.class) Map property7) {
		this.property7 = property7;
	}

	public Map getProperty8() {
		return property8;
	}

    
	public void property8Action(@KeyCollection(type=StringType.class)@ElementCollection(type=StringType.class) Map property8) {
		this.property8 = property8;
	}

	public Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> getProperty9() {
		return property9;
	}

    
	public void property9Action(@KeyCollection(mappingType=MappingTypes.SIMPLE)@ElementCollection(mappingType=MappingTypes.SIMPLE) Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property9) {
		this.property9 = property9;
	}

	public Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> getProperty10() {
		return property10;
	}

    
	public void property10Action(@KeyCollection(mappingType=MappingTypes.COMPLEX)@ElementCollection(mappingType=MappingTypes.COMPLEX) Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property10) {
		this.property10 = property10;
	}

	public Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> getProperty11() {
		return property11;
	}

	public void property11Action(Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property11) {
		this.property11 = property11;
	}

	public Map<EnumTest,EnumTest> getProperty12() {
		return property12;
	}

	public void property12Action(Map<EnumTest,EnumTest> property12) {
		this.property12 = property12;
	}
	
}
