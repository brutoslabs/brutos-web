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

package org.brandao.brutos.annotation.helper;

import java.util.*;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.helper.bean.BeanConstructorTest;
import org.brandao.brutos.annotation.helper.bean.CustomArrayList;

/**
 *
 * @author Brandao
 */
public class ControllerTest19Controller {

    private int propertyA;

    private String propertyB;
    
    private Date propertyC;
    
    private Date propertyD;
    
    private EnumTest propertyE;

    private EnumTest propertyF;
    
    private EnumTest propertyG;
    
    private Map<String,Integer> propertyH;

    private Map<String,Integer> propertyI;
    
    private Map<BeanConstructorTest,Integer> propertyJ;

    private Map<String,BeanConstructorTest> propertyK;

    private Map<String,BeanConstructorTest> propertyL;

    private List<Integer> propertyM;

    private List<Integer> propertyN;
    
    private List<Integer> propertyO;

    private List<BeanConstructorTest> propertyP;

    private List<BeanConstructorTest> propertyQ;

    private Map<BeanConstructorTest,BeanConstructorTest> propertyR;

    private List<BeanConstructorTest> propertyS;

    private Map<String,List<BeanConstructorTest>> propertyT;
    
    private Map<String,CustomArrayList> propertyU;

    private Map<String,CustomArrayList> propertyV;
    
    public Object myFirstAction(){
        return null;
    }

    public int getPropertyA() {
        return propertyA;
    }

    public void setPropertyA(int propertyA) {
        this.propertyA = propertyA;
    }

    public String getPropertyB() {
        return propertyB;
    }

    @Identify(bean="prop")
    public void setPropertyB(String propertyB) {
        this.propertyB = propertyB;
    }

    public Date getPropertyC() {
        return propertyC;
    }

    public void setPropertyC(Date propertyC) {
        this.propertyC = propertyC;
    }

    public Date getPropertyD() {
        return propertyD;
    }

    @Temporal("yyyy-MM-dd")
    public void setPropertyD(Date propertyD) {
        this.propertyD = propertyD;
    }

    public EnumTest getPropertyE() {
        return propertyE;
    }

    public void setPropertyE(EnumTest propertyE) {
        this.propertyE = propertyE;
    }

    public EnumTest getPropertyF() {
        return propertyF;
    }

    @Enumerated(value=EnumerationType.ORDINAL)
    public void setPropertyF(EnumTest propertyF) {
        this.propertyF = propertyF;
    }

    public EnumTest getPropertyG() {
        return propertyG;
    }

    @Enumerated(value=EnumerationType.STRING)
    public void setPropertyG(EnumTest propertyG) {
        this.propertyG = propertyG;
    }

    public Map<String,Integer> getPropertyH() {
        return propertyH;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
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
    public void setPropertyH(Map<String,Integer> propertyH) {
        this.propertyH = propertyH;
    }

    public Map<String,Integer> getPropertyI() {
        return propertyI;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    @Target(LinkedHashMap.class)
    public void setPropertyI(Map<String,Integer> propertyI) {
        this.propertyI = propertyI;
    }

    public Map<BeanConstructorTest,Integer> getPropertyJ() {
        return propertyJ;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    public void setPropertyJ(Map<BeanConstructorTest,Integer> propertyJ) {
        this.propertyJ = propertyJ;
    }

    public Map<String,BeanConstructorTest> getPropertyK() {
        return propertyK;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    public void setPropertyK(Map<String,BeanConstructorTest> propertyK) {
        this.propertyK = propertyK;
    }

    public Map<String,BeanConstructorTest> getPropertyL() {
        return propertyL;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    @ElementCollection(bean="myElement", mappingType=MappingTypes.COMPLEX)
    public void setPropertyL(Map<String,BeanConstructorTest> propertyL) {
        this.propertyL = propertyL;
    }

    public List<Integer> getPropertyM() {
        return propertyM;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    @ElementCollection(bean="myElement")
    public void setPropertyM(List<Integer> propertyM) {
        this.propertyM = propertyM;
    }

    public List<Integer> getPropertyN() {
        return propertyN;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    @Target(LinkedList.class)
    public void setPropertyN(List<Integer> propertyN) {
        this.propertyN = propertyN;
    }

    public List<Integer> getPropertyO() {
        return propertyO;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    public void setPropertyO(List<Integer> propertyO) {
        this.propertyO = propertyO;
    }

    public List<BeanConstructorTest> getPropertyP() {
        return propertyP;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    public void setPropertyP(List<BeanConstructorTest> propertyP) {
        this.propertyP = propertyP;
    }

    public List<BeanConstructorTest> getPropertyQ() {
        return propertyQ;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    @ElementCollection(bean="myElement")
    public void setPropertyQ(List<BeanConstructorTest> propertyQ) {
        this.propertyQ = propertyQ;
    }

    public Map<BeanConstructorTest,BeanConstructorTest> getPropertyR() {
        return propertyR;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    @KeyCollection(bean="myKey", mappingType=MappingTypes.COMPLEX)
    @ElementCollection(bean="myElement", mappingType=MappingTypes.COMPLEX)
    public void setPropertyR(Map<BeanConstructorTest,BeanConstructorTest> propertyR) {
        this.propertyR = propertyR;
    }

    public List<BeanConstructorTest> getPropertyS() {
        return propertyS;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    @ElementCollection(bean="myElement", mappingType=MappingTypes.COMPLEX)
    public void setPropertyS(List<BeanConstructorTest> propertyS) {
        this.propertyS = propertyS;
    }

    public Map<String,List<BeanConstructorTest>> getPropertyT() {
        return propertyT;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    public void setPropertyT(Map<String,List<BeanConstructorTest>> propertyT) {
        this.propertyT = propertyT;
    }

    public Map<String,CustomArrayList> getPropertyU() {
        return propertyU;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    public void setPropertyU(Map<String,CustomArrayList> propertyU) {
        this.propertyU = propertyU;
    }

    public Map<String,CustomArrayList> getPropertyV() {
        return propertyV;
    }

    @Identify(mappingType=MappingTypes.COMPLEX)
    @ElementCollection(bean="myElement2",mappingType=MappingTypes.COMPLEX)
    public void setPropertyV(Map<String,CustomArrayList> propertyV) {
        this.propertyV = propertyV;
    }

}
