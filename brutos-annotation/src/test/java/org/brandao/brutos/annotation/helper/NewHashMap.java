package org.brandao.brutos.annotation.helper;

import java.util.HashMap;

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
	
}
