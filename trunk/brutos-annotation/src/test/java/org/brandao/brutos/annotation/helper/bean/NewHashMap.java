package org.brandao.brutos.annotation.helper.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.brandao.brutos.annotation.Transient;

@SuppressWarnings("serial")
public class NewHashMap 
	extends HashMap<Object, Object>{

	@Transient
	public boolean isEmpty(){
		return super.isEmpty();
	}
	
	@Override
	public boolean equals(Object arg0){
		return super.equals(arg0);
	}
	
	@Override
	public int hashCode(){
		return super.hashCode();
	}
	
	@Transient
	public void setValues(Collection v){
	}

	@Transient
	public void setUseAltHashing(boolean v){
	}
	
	@Transient
	public void setEntrySet(Set v){
	}
	
	@Transient
	public void setEmpty(boolean v){
	}

	@Transient
	public void setKeySet(Set v){
	}

	@Transient
	public void setTable(java.util.Map.Entry<Object, Object>[] e){
	}
	
	@Transient
	public void setModCount(int v){
	}
	
	@Transient
	public void setThreshold(int v){
		
	}
	@Transient
	public void setSize(int v){
		
	}
}
