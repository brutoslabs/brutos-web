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

package org.brandao.brutos.annotation.helper.bean;

import java.util.*;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.helper.EnumTest;

/**
 *
 * @author Brandao
 */
@Bean
public class BeanFieldTest {
    
    private int propertyA;

    @Identify(bean="prop")
    private String propertyB;
    
    private Date propertyC;
    
    @Temporal("yyyy-MM-dd")
    private Date propertyD;
    
    private EnumTest propertyE;

    @Enumerated(value=EnumerationType.ORDINAL)
    private EnumTest propertyF;
    
    @Enumerated(value=EnumerationType.STRING)
    private EnumTest propertyG;
    
    @KeyCollection(
        bean="myKey",
        enumerated=EnumerationType.STRING,
        target=Integer.class,
        temporal="yyyy-MM-dd"
    )
    @ElementCollection(
        bean="myElement",
        enumerated=EnumerationType.STRING,
        target=String.class,
        temporal="yyyy-MM-dd",
        scope=ScopeType.REQUEST
    )
    private Map<String,Integer> propertyH;

    @Target(LinkedHashMap.class)
    private Map<String,Integer> propertyI;
    
    private Map<BeanConstructorTest,Integer> propertyJ;

    private Map<String,BeanConstructorTest> propertyK;

    @ElementCollection(bean="myElement", mappingType=MappingTypes.COMPLEX)
    private Map<String,BeanConstructorTest> propertyL;

    @ElementCollection(bean="myElement")
    private List<Integer> propertyM;

    @Target(LinkedList.class)
    private List<Integer> propertyN;
    
    private List<Integer> propertyO;

    private List<BeanConstructorTest> propertyP;

    @ElementCollection(bean="myElement")
    private List<BeanConstructorTest> propertyQ;

    @KeyCollection(bean="myKey", mappingType=MappingTypes.COMPLEX)
    @ElementCollection(bean="myElement", mappingType=MappingTypes.COMPLEX)
    private Map<BeanConstructorTest,BeanConstructorTest> propertyR;

    @ElementCollection(bean="myElement", mappingType=MappingTypes.COMPLEX)
    private List<BeanConstructorTest> propertyS;

    private Map<String,List<BeanConstructorTest>> propertyT;
    
    private Map<String,CustomArrayList> propertyU;

    @ElementCollection(bean="myElement2", mappingType=MappingTypes.COMPLEX)
    private Map<String,CustomArrayList> propertyV;

    private BeanConstructorTest propertyX;
    
}
