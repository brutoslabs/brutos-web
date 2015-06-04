package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.List;
import java.util.Map;

public class KeyCollectionConstructor1Test {

	private Map<Integer, Integer> entity;
	
	public KeyCollectionConstructor1Test(Map<Integer,Integer> entity){
		this.entity = entity;
	}

	public Map<Integer,Integer> getEntity() {
		return entity;
	}

	public void setEntity(Map<Integer,Integer> entity) {
		this.entity = entity;
	}
	
}
