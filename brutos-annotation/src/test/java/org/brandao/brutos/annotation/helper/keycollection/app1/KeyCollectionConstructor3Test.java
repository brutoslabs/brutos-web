package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;

public class KeyCollectionConstructor3Test {

	private Map<Integer,Integer> entity;
	
	public KeyCollectionConstructor3Test(@KeyCollection(bean="kly")@ElementCollection(bean="elx")Map<Integer,Integer> entity){
		this.entity = entity;
	}

	public Map<Integer,Integer> getEntity() {
		return entity;
	}

	public void setEntity(Map<Integer,Integer> entity) {
		this.entity = entity;
	}
	
}
