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
@SuppressWarnings("rawtypes")
public class ControllerKeyCollectionFieldTest {

    public Map<Integer,String> property;

    @KeyCollection
    public Map<Integer,String> property2;

    @KeyCollection(bean="elx")
    public Map<Integer,String> property3;

    @KeyCollection(enumerated=EnumerationType.STRING)
    public Map<EnumTest,String> property4;

    @KeyCollection(scope=ScopeType.SESSION)
    public Map<Integer,String> property5;

    @KeyCollection(temporal="mm-dd-yyyy")
    public Map<Date,String> property6;

	@KeyCollection(target=Integer.class)
    @ElementCollection(target=String.class)
    public Map property7;

    @KeyCollection(type=TestStringType.class, target=String.class)
    @ElementCollection(target=String.class)
    public Map property8;

    @KeyCollection(mappingType=MappingTypes.SIMPLE, type=KeyCollectionBeanTest0Type.class)
    public Map<KeyCollectionBeanTest0,String> property9;

    @KeyCollection(bean="key", mappingType=MappingTypes.COMPLEX)
    public Map<KeyCollectionBeanTest0,String> property10;
    
    @KeyCollection(bean="key")
    public Map<KeyCollectionBeanTest0,String> property11;

    public Map<EnumTest,String> property12;
	
    public void testAction(){
    }
    
}
