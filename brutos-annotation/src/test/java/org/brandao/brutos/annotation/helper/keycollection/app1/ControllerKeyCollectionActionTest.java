package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Date;
import java.util.Map;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.ScopeType;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.EnumTest;

@Controller("/controller")
@SuppressWarnings("rawtypes")
public class ControllerKeyCollectionActionTest {

	@Transient
    private Map<Integer,String> property;

	@Transient
    private Map<Integer,String> property2;

	@Transient
    private Map<Integer,String> property3;

	@Transient
    private Map<EnumTest,String> property4;

	@Transient
    private Map<Integer,String> property5;

	@Transient
    private Map<Date,String> property6;

	@Transient
    private Map property7;

	@Transient
    private Map property8;

	@Transient
    private Map<KeyCollectionBeanTest0,String> property9;

	@Transient
    private Map<KeyCollectionBeanTest0,String> property10;
    
	@Transient
    private Map<KeyCollectionBeanTest0,String> property11;

	@Transient
    private Map<EnumTest,String> property12;
    
	public Map<Integer,String> getProperty() {
		return property;
	}

	public void propertyAction(Map<Integer,String> property) {
		this.property = property;
	}

	public Map<Integer,String> getProperty2() {
		return property2;
	}

    
	public void property2Action(@KeyCollection Map<Integer,String> property2) {
		this.property2 = property2;
	}

	public Map<Integer,String> getProperty3() {
		return property3;
	}

    
	public void property3Action(@KeyCollection(bean="elx") Map<Integer,String> property3) {
		this.property3 = property3;
	}

	public Map<EnumTest,String> getProperty4() {
		return property4;
	}

    
	public void property4Action(@KeyCollection(enumerated=EnumerationType.STRING) Map<EnumTest,String> property4) {
		this.property4 = property4;
	}

	public Map<Integer,String> getProperty5() {
		return property5;
	}

    
	public void property5Action(
			@KeyCollection(scope=ScopeType.SESSION)
			@ElementCollection(scope=ScopeType.SESSION)
			Map<Integer,String> property5) {
		this.property5 = property5;
	}

	public Map<Date,String> getProperty6() {
		return property6;
	}

    
	public void property6Action(@KeyCollection(temporal="mm-dd-yyyy") Map<Date,String> property6) {
		this.property6 = property6;
	}

	public Map getProperty7() {
		return property7;
	}

    
	public void property7Action(
			@KeyCollection(target=Integer.class)
			@ElementCollection(target=String.class)
			Map property7) {
		this.property7 = property7;
	}

	public Map getProperty8() {
		return property8;
	}

    
	public void property8Action(
			@KeyCollection(type=TestStringType.class, target=String.class)
			@ElementCollection(target=String.class)
			Map property8) {
		this.property8 = property8;
	}

	public Map<KeyCollectionBeanTest0,String> getProperty9() {
		return property9;
	}

    
	public void property9Action(@KeyCollection(
			mappingType=MappingTypes.SIMPLE,
			type=KeyCollectionBeanTest0Type.class) 
	Map<KeyCollectionBeanTest0,String> property9) {
		this.property9 = property9;
	}

	public Map<KeyCollectionBeanTest0,String> getProperty10() {
		return property10;
	}

    
	public void property10Action(
			@KeyCollection(bean="key", mappingType=MappingTypes.COMPLEX)
			Map<KeyCollectionBeanTest0,String> property10) {
		this.property10 = property10;
	}

	public Map<KeyCollectionBeanTest0,String> getProperty11() {
		return property11;
	}

	public void property11Action(@KeyCollection(bean="type") Map<KeyCollectionBeanTest0,String> property11) {
		this.property11 = property11;
	}

	public Map<EnumTest,String> getProperty12() {
		return property12;
	}

	public void property12Action(Map<EnumTest,String> property12) {
		this.property12 = property12;
	}
	
}
