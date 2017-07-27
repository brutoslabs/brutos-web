/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.Date;
import java.util.List;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.ScopeType;
import org.brandao.brutos.annotation.helper.EnumTest;

/**
 *
 * @author Brandao
 */
@SuppressWarnings("rawtypes")
public class ElementCollectionFieldTest {
    
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
    
}
