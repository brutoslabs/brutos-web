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


package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Date;
import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.ScopeType;
import org.brandao.brutos.annotation.helper.EnumTest;

/**
 *
 * @author Brandao
 */
public class KeyCollectionFieldTest {
    
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
    
}
