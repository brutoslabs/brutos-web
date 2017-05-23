package org.brandao.brutos.web.bean.helper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.Enumerated;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.ScopeType;
import org.brandao.brutos.annotation.Target;
import org.brandao.brutos.annotation.Temporal;
import org.brandao.brutos.annotation.helper.EnumTest;
import org.brandao.brutos.annotation.helper.bean.CustomArrayList;
import org.brandao.brutos.annotation.helper.bean.NewArrayList;
import org.brandao.brutos.annotation.helper.bean.NewHashMap;

public class JsonBeanEncoderBean {

    private int propertyA;

    @Basic(bean="prop")
    private String propertyB;
    
    private Date propertyC;
    
    @Temporal("yyyy-MM-dd'T'hh:mm:ss.sss'Z'")
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

    @Target(NewHashMap.class)
    @KeyCollection(target=String.class)
    @ElementCollection(target=Integer.class)
    private Map<String,Integer> propertyI;
    
    private Map<JsonBeanEncoderInnerBean,Integer> propertyJ;

    private Map<String,JsonBeanEncoderInnerBean> propertyK;

    @ElementCollection(bean="myElement")
    private Map<String,JsonBeanEncoderInnerBean> propertyL;

    @ElementCollection(bean="myElement")
    private List<Integer> propertyM;

    @Target(NewArrayList.class)
    @ElementCollection(target=Integer.class)
    private List<Integer> propertyN;
    
    private List<Integer> propertyO;

    private List<JsonBeanEncoderInnerBean> propertyP;

    @ElementCollection(bean="myElement")
    private List<JsonBeanEncoderInnerBean> propertyQ;

    @KeyCollection(bean="myKey")
    @ElementCollection(bean="myElement")
    private Map<JsonBeanEncoderInnerBean,JsonBeanEncoderInnerBean> propertyR;

    @ElementCollection(bean="myElement")
    private List<JsonBeanEncoderInnerBean> propertyS;

    private Map<String,List<JsonBeanEncoderInnerBean>> propertyT;
    
    private Map<String,CustomArrayList> propertyU;

    @ElementCollection(bean="myElement2")
    private Map<String,CustomArrayList> propertyV;

	public int getPropertyA() {
		return propertyA;
	}

	public void setPropertyA(int propertyA) {
		this.propertyA = propertyA;
	}

	public String getPropertyB() {
		return propertyB;
	}

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

	public void setPropertyF(EnumTest propertyF) {
		this.propertyF = propertyF;
	}

	public EnumTest getPropertyG() {
		return propertyG;
	}

	public void setPropertyG(EnumTest propertyG) {
		this.propertyG = propertyG;
	}

	public Map<String, Integer> getPropertyH() {
		return propertyH;
	}

	public void setPropertyH(Map<String, Integer> propertyH) {
		this.propertyH = propertyH;
	}

	public Map<String, Integer> getPropertyI() {
		return propertyI;
	}

	public void setPropertyI(Map<String, Integer> propertyI) {
		this.propertyI = propertyI;
	}

	public Map<JsonBeanEncoderInnerBean, Integer> getPropertyJ() {
		return propertyJ;
	}

	public void setPropertyJ(Map<JsonBeanEncoderInnerBean, Integer> propertyJ) {
		this.propertyJ = propertyJ;
	}

	public Map<String, JsonBeanEncoderInnerBean> getPropertyK() {
		return propertyK;
	}

	public void setPropertyK(Map<String, JsonBeanEncoderInnerBean> propertyK) {
		this.propertyK = propertyK;
	}

	public Map<String, JsonBeanEncoderInnerBean> getPropertyL() {
		return propertyL;
	}

	public void setPropertyL(Map<String, JsonBeanEncoderInnerBean> propertyL) {
		this.propertyL = propertyL;
	}

	public List<Integer> getPropertyM() {
		return propertyM;
	}

	public void setPropertyM(List<Integer> propertyM) {
		this.propertyM = propertyM;
	}

	public List<Integer> getPropertyN() {
		return propertyN;
	}

	public void setPropertyN(List<Integer> propertyN) {
		this.propertyN = propertyN;
	}

	public List<Integer> getPropertyO() {
		return propertyO;
	}

	public void setPropertyO(List<Integer> propertyO) {
		this.propertyO = propertyO;
	}

	public List<JsonBeanEncoderInnerBean> getPropertyP() {
		return propertyP;
	}

	public void setPropertyP(List<JsonBeanEncoderInnerBean> propertyP) {
		this.propertyP = propertyP;
	}

	public List<JsonBeanEncoderInnerBean> getPropertyQ() {
		return propertyQ;
	}

	public void setPropertyQ(List<JsonBeanEncoderInnerBean> propertyQ) {
		this.propertyQ = propertyQ;
	}

	public Map<JsonBeanEncoderInnerBean, JsonBeanEncoderInnerBean> getPropertyR() {
		return propertyR;
	}

	public void setPropertyR(
			Map<JsonBeanEncoderInnerBean, JsonBeanEncoderInnerBean> propertyR) {
		this.propertyR = propertyR;
	}

	public List<JsonBeanEncoderInnerBean> getPropertyS() {
		return propertyS;
	}

	public void setPropertyS(List<JsonBeanEncoderInnerBean> propertyS) {
		this.propertyS = propertyS;
	}

	public Map<String, List<JsonBeanEncoderInnerBean>> getPropertyT() {
		return propertyT;
	}

	public void setPropertyT(
			Map<String, List<JsonBeanEncoderInnerBean>> propertyT) {
		this.propertyT = propertyT;
	}

	public Map<String, CustomArrayList> getPropertyU() {
		return propertyU;
	}

	public void setPropertyU(Map<String, CustomArrayList> propertyU) {
		this.propertyU = propertyU;
	}

	public Map<String, CustomArrayList> getPropertyV() {
		return propertyV;
	}

	public void setPropertyV(Map<String, CustomArrayList> propertyV) {
		this.propertyV = propertyV;
	}	
    
}
