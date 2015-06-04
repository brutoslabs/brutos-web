package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.KeyCollection;

public class KeyCollectionConstructor2Test {

	private Map<Integer,Integer> entity;
	
	public KeyCollectionConstructor2Test(@KeyCollection @ElementCollection Map<Integer,Integer> entity){
		this.entity = entity;
	}

	public Map<Integer,Integer> getEntity() {
		return entity;
	}

	public void setEntity(Map<Integer,Integer> entity) {
		this.entity = entity;
	}
	
}
