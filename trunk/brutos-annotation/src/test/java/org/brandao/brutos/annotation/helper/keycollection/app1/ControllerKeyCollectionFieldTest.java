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
import org.brandao.brutos.type.StringType;

@Controller("/")
public class ControllerKeyCollectionFieldTest {

    private Map<Integer,Integer> property;

    @ElementCollection
    private Map<Integer,Integer> property2;

    @KeyCollection(bean="kly")
    @ElementCollection(bean="elx")
    private Map<Integer,Integer> property3;

    @KeyCollection(enumerated=EnumerationType.STRING)
    @ElementCollection(enumerated=EnumerationType.STRING)
    private Map<Integer,EnumTest> property4;

    @KeyCollection(scope=ScopeType.SESSION)
    @ElementCollection(scope=ScopeType.SESSION)
    private Map<Integer,Integer> property5;

    @KeyCollection(temporal="mm-dd-yyyy")
    @ElementCollection(temporal="mm-dd-yyyy")
    private Map<Date,Date> property6;

    @KeyCollection(target=Integer.class)
    @ElementCollection(target=Integer.class)
    private Map property7;

    @KeyCollection(type=StringType.class)
    @ElementCollection(type=StringType.class)
    private Map property8;

    @KeyCollection(mappingType=MappingTypes.SIMPLE)
    @ElementCollection(mappingType=MappingTypes.SIMPLE)
    private Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property9;

    @KeyCollection(mappingType=MappingTypes.COMPLEX)
    @ElementCollection(mappingType=MappingTypes.COMPLEX)
    private Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property10;
    
    private Map<KeyCollectionBeanTest0,KeyCollectionBeanTest0> property11;

    private Map<EnumTest,EnumTest> property12;
	
    public void testAction(){
    }
    
}
