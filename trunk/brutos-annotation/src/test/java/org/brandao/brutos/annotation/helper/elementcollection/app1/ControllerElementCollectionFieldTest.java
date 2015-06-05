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
public class ControllerElementCollectionFieldTest {

    public List<Integer> property;

    @ElementCollection
    public List<Integer> property2;

    @ElementCollection(bean="elx")
    public List<Integer> property3;

    @ElementCollection(enumerated=EnumerationType.STRING)
    public List<EnumTest> property4;

    @ElementCollection(scope=ScopeType.SESSION)
    public List<Integer> property5;

    @ElementCollection(temporal="mm-dd-yyyy")
    public List<Date> property6;

    @ElementCollection(target=Integer.class)
    public List property7;

    @ElementCollection(type=TestStringType.class, target=String.class)
    public List property8;

    @ElementCollection(mappingType=MappingTypes.SIMPLE, type=ElementCollectionBeanTest0Type.class)
    public List<ElementCollectionBeanTest0> property9;

    @ElementCollection(mappingType=MappingTypes.COMPLEX)
    public List<ElementCollectionBeanTest0> property10;
    
    public List<ElementCollectionBeanTest0> property11;

    public List<EnumTest> property12;
	
    public void testAction(){
    }
    
}
